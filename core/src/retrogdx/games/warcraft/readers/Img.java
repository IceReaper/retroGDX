package retrogdx.games.warcraft.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Img {
    public byte[] pixels;
    public short width;
    public short height;

    public Img(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        this.width = buffer.readShort();
        this.height = buffer.readShort();
        this.pixels = buffer.readBytes(this.width * this.height);
    }
}
