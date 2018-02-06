package retrogdx.games.dune2.readers;

import retrogdx.utils.SmartByteBuffer;

public class Pal {
    private SmartByteBuffer buffer;

    public Pal(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public int[] getColors() {
        int[] colors = new int[256];

        this.buffer.position(0);

        for (int i = 0; i < 256; i++) {
            colors[i] = (this.buffer.readByte() << 26) | (this.buffer.readByte() << 18) | (this.buffer.readByte() << 10) | 0xff;
        }

        return colors;
    }
}
