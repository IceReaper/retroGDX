package retrogdx.games.warcraft.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Pal {
    public int[] colors;

    public Pal(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        this.colors = new int[buffer.capacity() / 3];

        for (int i = 0; i < this.colors.length; i++) {
            this.colors[i] = (buffer.readByte() << 26) | (buffer.readByte() << 18) | (buffer.readByte() << 10) | 0xff;
        }
    }
}
