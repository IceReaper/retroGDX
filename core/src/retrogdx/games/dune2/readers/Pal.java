package retrogdx.games.dune2.readers;

import retrogdx.utils.SmartByteBuffer;

public class Pal {
    public int[] colors = new int[256];

    public Pal(SmartByteBuffer buffer) {
        buffer.position(0);

        for (int i = 0; i < 256; i++) {
            colors[i] = (buffer.readByte() << 26) | (buffer.readByte() << 18) | (buffer.readByte() << 10) | 0xff;
        }
    }
}
