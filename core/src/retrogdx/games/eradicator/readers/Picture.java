package retrogdx.games.eradicator.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Picture {
    public int width;
    public int height;
    public byte[] pixels;

    public Picture(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        this.width = buffer.readInt();
        this.height = buffer.readInt();

        this.pixels = buffer.readBytes(this.width * this.height);
    }
}
