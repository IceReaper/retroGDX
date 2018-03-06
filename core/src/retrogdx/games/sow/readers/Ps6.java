package retrogdx.games.sow.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class Ps6 {
    public class Ps6Frame {
        public int[] pixels;
        public int width;
        public int height;
        public int originX;
        public int originY;
    }

    public Ps6Frame[] frames;

    public Ps6(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        List<Ps6Frame> frames = new ArrayList<>();

        while (buffer.position() < buffer.capacity()) {
            int size = buffer.readInt();

            if (size == 0) {
                break;
            }

            int outerPosition = buffer.position();

            Ps6Frame frame = new Ps6Frame();
            frames.add(frame);

            int startPosition = buffer.position();

            frame.width = buffer.readShort();
            frame.height = buffer.readShort();

            frame.originX = buffer.readShort();
            frame.originY = buffer.readShort();

            short unk1 = buffer.readShort();
            short unk2 = buffer.readShort();

            short unk3 = buffer.readShort();
            short unk4 = buffer.readShort();

            if (frame.width > 0 && frame.height > 0) {
                frame.pixels = new int[frame.width * frame.height];

                for (int y = 0; y < frame.height; y++) {
                    int lineOffset = buffer.readInt();
                    int position = buffer.position();
                    buffer.position(startPosition + lineOffset * 2);

                    int numCommand = buffer.readShort();
                    int x = 0;
                    boolean skipMode = buffer.readShort() == 0x00;

                    for (int i = 0; i < numCommand; i++) {
                        if (skipMode) {
                            x += buffer.readShort();
                        } else {
                            short readPixels = buffer.readShort();

                            for (int j = 0; j < readPixels; j++) {
                                int color16 = buffer.readShort();
                                int color32 = (((color16 >> 8) & 0xf8) << 24) | (((color16 >> 3) & 0xfc) << 16) | ((color16 & 0x1f) << 11) | 0xff;
                                frame.pixels[x + y * frame.width] = color32;
                                x++;
                            }
                        }

                        skipMode = !skipMode;
                    }

                    buffer.position(position);
                }
            }

            buffer.position(outerPosition + size);
        }

        this.frames = new Ps6Frame[frames.size()];

        for (int i = 0; i < frames.size(); i++) {
            this.frames[i] = frames.get(i);
        }
    }
}
