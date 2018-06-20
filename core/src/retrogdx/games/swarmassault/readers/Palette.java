package retrogdx.games.swarmassault.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Palette {
    public int[] colors = new int[256];

    public Palette(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.position(0);

        for (int i = 0; i < 256; i++) {
            this.colors[i] = buffer.readInt() | (i == 0 ? 0x00 : 0xff);
        }
    }
}
