package retrogdx.games.bam.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Ani {
    public class AniFrame {
        public byte[] pixels;
        public int width;
        public int height;
        public int originX;
        public int originY;
    }

    public AniFrame[] frames;

    public Ani(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        int frameControl = 0;

        while (true) {
            buffer.readInt(); // originX
            buffer.readInt(); // originY
            int width = buffer.readShort();
            int height = buffer.readShort();
            int unk1 = buffer.readShort(); // TODO
            int frameOffset = buffer.readInt();

            if (frameControl == frameOffset) {
                frameControl += width * height;
            } else {
                break;
            }
        }

        int numFrames = buffer.position() / 18 - 1;
        buffer.position(0);

        this.frames = new AniFrame[numFrames];

        for (int i = 0; i < numFrames; i++) {
            this.frames[i] = this.parseFrame(buffer, numFrames * 18);
        }
    }

    private AniFrame parseFrame(SmartByteBuffer buffer, int pixelsStart) {
        AniFrame frame = new AniFrame();

        frame.originX = buffer.readInt();
        frame.originY = buffer.readInt();
        frame.width = buffer.readShort() * 2;
        frame.height = buffer.readShort();
        int unk1 = buffer.readShort(); // TODO
        int frameOffset = buffer.readInt();

        frame.pixels = new byte[frame.width * frame.height];

        buffer.block(pixelsStart + frameOffset, (SmartByteBuffer blockBuffer) -> {
            for (int i = 0; i < frame.width * frame.height; i += 2) {
                frame.pixels[i] = blockBuffer.readByte();
                frame.pixels[i + 1] = frame.pixels[i];
            }
        });

        return frame;
    }
}
