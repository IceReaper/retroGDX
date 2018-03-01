package retrogdx.games.bam.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Ani {
    public class AniImage {
        public int originX;
        public int originY;
        public int width;
        public int height;
        public byte[] pixels;
    }

    public AniImage[] images;

    public Ani(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        int frameControl = 0;

        while (true) {
            buffer.position(buffer.position() + 8);
            int width = buffer.readShort();
            int height = buffer.readShort();
            buffer.position(buffer.position() + 2);
            int frameOffset = buffer.readInt();

            if (frameControl == frameOffset) {
                frameControl += width * height;
            } else {
                buffer.position(buffer.position() - 18);
                break;
            }
        }

        int numFrames = buffer.position() / 18;
        buffer.position(0);

        this.images = new AniImage[numFrames];

        for (int i = 0; i < numFrames; i++) {
            this.images[i] = this.parseImage(buffer, numFrames * 18);
        }
    }

    private AniImage parseImage(SmartByteBuffer buffer, int pixelsStart) {
        AniImage frame = new AniImage();

        frame.originX = buffer.readInt();
        frame.originY = buffer.readInt();

        frame.width = buffer.readShort();
        frame.height = buffer.readShort();

        int unk1 = buffer.readShort(); // TODO

        int frameOffset = buffer.readInt();

        int pos = buffer.position();
        buffer.position(pixelsStart + frameOffset);
        frame.pixels = buffer.readBytes(frame.width * frame.height);
        buffer.position(pos);

        return frame;
    }
}
