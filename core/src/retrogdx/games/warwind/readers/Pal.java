package retrogdx.games.warwind.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Pal {
    public int[] colors = new int[256];

    public Pal(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        buffer.readString(4); // D3GR
        int flags = buffer.readInt(); // 0x2001
        int framesStart = buffer.readInt();
        int paletteStart = buffer.readInt();

        buffer.position(4 + paletteStart); //skip 4 bytes D3GR header also

        for (int i = 0; i < 256; i++) {
            int r = buffer.readUByte() << 2;
            int g = buffer.readUByte() << 2;
            int b = buffer.readUByte() << 2;
            this.colors[i] = (r << 24) | (g << 16) | (b << 8) | 0xff;
        }
    }
}
