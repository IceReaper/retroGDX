package retrogdx.games.warcraft.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Cur {
    public byte[] pixels;
    public short width;
    public short height;
    public short originX;
    public short originY;

    public Cur(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        this.originX = buffer.readShort();
        this.originY = buffer.readShort();
        this.width = buffer.readShort();
        this.height = buffer.readShort();
        this.pixels = buffer.readBytes(this.width * this.height);
    }
}
