package retrogdx.generic.readers;

import retrogdx.utils.SmartByteBuffer;

public class Palette {
    public int[] colors = new int[256];

    public Palette(SmartByteBuffer buffer) {
        buffer.position(0);

        for (int i = 0; i < 256; i++) {
            colors[i] = (buffer.readByte() << 24) | (buffer.readByte() << 16) | (buffer.readByte() << 8) | 0xff;
        }
    }
}
