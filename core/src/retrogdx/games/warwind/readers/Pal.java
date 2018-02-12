package retrogdx.games.warwind.readers;

import retrogdx.utils.SmartByteBuffer;

public class Pal {
    public int[] colors = new int[256 * 256];

    public Pal(SmartByteBuffer buffer) {
        buffer.position(0);

        for (int i = 0; i < 256 * 256; i++) {
            // TODO find correct palette format!
            int value = buffer.readUByte();
            this.colors[i] = (value << 24) | (value << 16) | (value << 8) | 0xff;
        }
    }
}
