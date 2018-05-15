package retrogdx.games.futurecop.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Bmp {
    public int[] pixels = new int[256 * 256];

    public Bmp(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        // TODO what is in this bytes?
        buffer.position(0x45c);

        for (int y = 0; y < 256; y++) {
            for (int x = 0; x < 256; x++) {
                int color16 = buffer.readShort();
                this.pixels[y * 256 + x] = (((color16 >> 10) & 0x1f) << 27) | (((color16 >> 5) & 0x1f) << 19) | ((color16 & 0x1f) << 11) | 0xff;
            }
        }

        // TODO is there anything after the pixels?
    }
}
