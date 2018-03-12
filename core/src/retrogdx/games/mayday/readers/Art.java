package retrogdx.games.mayday.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Art {
    public class ArtFrame {
        public byte[] pixels;
        public int width;
        public int height;
    }

    public ArtFrame[] frames;

    public Art(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        // TODO try to find information about frame sizes, so we can get rid of this hardcoded  list.

        byte[] unk1 = buffer.readBytes(4 * 10);
        byte[] unk2 = buffer.readBytes(4 * 46);
        byte[] unk3 = buffer.readBytes(14);

        this.frames = new ArtFrame[7334];

        for (int i = 0; i < this.frames.length; i++) {
            ArtFrame frame = new ArtFrame();

            if (i < 2884) {
                frame.width = 64;
                frame.height = 32;
            } else if (i < 2884 + 960) {
                frame.width = 32;
                frame.height = 32;
            } else if (i < 2884 + 960 + 2848) {
                frame.width = 64;
                frame.height = 64;
            } else if (i < 2884 + 960 + 2848 + 113) {
                frame.width = 128;
                frame.height = 128;
            } else if (i < 2884 + 960 + 2848 + 113 + 281) {
                frame.width = 64;
                frame.height = 64;
            } else if (i < 2884 + 960 + 2848 + 113 + 281 + 64) {
                frame.width = 32;
                frame.height = 32;
            } else if (i < 2884 + 960 + 2848 + 113 + 281 + 64 + 1) {
                frame.width = 164;
                frame.height = 25;
            } else if (i < 2884 + 960 + 2848 + 113 + 281 + 64 + 1 + 58) {
                frame.width = 90;
                frame.height = 64;
            } else if (i < 2884 + 960 + 2848 + 113 + 281 + 64 + 1 + 58 + 1) {
                frame.width = 164;
                frame.height = 498;
            } else if (i < 2884 + 960 + 2848 + 113 + 281 + 64 + 1 + 58 + 1 + 50) {
                frame.width = 154;
                frame.height = 105;
            } else if (i < 2884 + 960 + 2848 + 113 + 281 + 64 + 1 + 58 + 1 + 50 + 16) {
                frame.width = 20;
                frame.height = 20;
            } else if (i < 2884 + 960 + 2848 + 113 + 281 + 64 + 1 + 58 + 1 + 50 + 16 + 1) {
                frame.width = 164;
                frame.height = 189;
            } else if (i < 2884 + 960 + 2848 + 113 + 281 + 64 + 1 + 58 + 1 + 50 + 16 + 1 + 48) {
                frame.width = 64;
                frame.height = 64;
            } else if (i < 2884 + 960 + 2848 + 113 + 281 + 64 + 1 + 58 + 1 + 50 + 16 + 1 + 48 + 1) {
                frame.width = 320;
                frame.height = 286;
            } else {
                frame.width = 32;
                frame.height = 32;
            }

            frame.pixels = buffer.readBytes(frame.width * frame.height);
            this.frames[i] = frame;
        }
    }
}
