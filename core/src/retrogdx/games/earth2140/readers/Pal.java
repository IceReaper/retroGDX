package retrogdx.games.earth2140.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Pal {
    public int[] colors = new int[256];

    public Pal(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        for (int i = 0; i < 256; i++) {
            this.colors[i] = (buffer.readByte() << 24) | (buffer.readByte() << 16) | (buffer.readByte() << 8) | 0xff;
        }
    }
}
