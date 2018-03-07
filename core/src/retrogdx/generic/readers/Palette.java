package retrogdx.generic.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Palette {
    public int[] colors = new int[256];

    public Palette(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        for (int i = 0; i < 256; i++) {
            this.colors[i] = (buffer.readByte() << 24) | (buffer.readByte() << 16) | (buffer.readByte() << 8) | 0xff;
        }
    }
}
