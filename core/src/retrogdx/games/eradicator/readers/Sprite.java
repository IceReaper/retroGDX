package retrogdx.games.eradicator.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class Sprite {
    public class SpriteFrame {
        public int width;
        public int height;
        public byte[] pixels;
    }

    public SpriteFrame[] frames;

    public Sprite(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        List<SpriteFrame> frames = new ArrayList<>();


        buffer.readInt(); // TODO unk
        buffer.readInt(); // TODO unk
        int headerSize = buffer.readInt();
        // TODO what is between this?
        buffer.position(headerSize);

        while (buffer.position() < buffer.capacity()) {
            SpriteFrame frame = new SpriteFrame();
            frame.width = buffer.readInt();
            frame.height = buffer.readInt();
            buffer.readInt(); // TODO unk

            frame.pixels = new byte[frame.width * frame.height];

            for (int x = 0; x < frame.width; x++) {
                for (int y = 0; y < frame.height; y++) {
                    frame.pixels[x + y * frame.width] = buffer.readByte();
                }
            }

            if (buffer.position() % 4 != 0) {
                buffer.position(buffer.position() + 4 - buffer.position() % 4);
            }

            frames.add(frame);
        }

        this.frames = frames.toArray(new SpriteFrame[0]);
    }
}
