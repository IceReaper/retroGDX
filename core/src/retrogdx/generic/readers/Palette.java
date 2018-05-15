package retrogdx.generic.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Palette {
    public int[] colors;

    public static Palette fromRGB16(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        Palette palette = new Palette();
        palette.colors = new int[buffer.capacity() / 2];

        for (int i = 0; i < palette.colors.length; i++) {
            int color16 = buffer.readShort();
            palette.colors[i] = (((color16 >> 10) & 0x1f) << 27) | (((color16 >> 5) & 0x1f) << 19) | ((color16 & 0x1f) << 11) | 0xff;
        }

        return palette;
    }

    public static Palette fromRGB24(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.position(0);

        Palette palette = new Palette();
        palette.colors = new int[buffer.capacity() / 3];

        for (int i = 0; i < palette.colors.length; i++) {
            palette.colors[i] = (buffer.readUByte() << 24) | (buffer.readUByte() << 16) | (buffer.readUByte() << 8) | 0xff;
        }

        return palette;
    }

    public static Palette fromRGB_32(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.position(0);

        Palette palette = new Palette();
        palette.colors = new int[buffer.capacity() / 4];

        for (int i = 0; i < palette.colors.length; i++) {
            palette.colors[i] = buffer.readInt() | 0xff;
        }

        return palette;
    }
}
