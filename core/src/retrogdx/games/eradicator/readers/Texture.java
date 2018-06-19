package retrogdx.games.eradicator.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Texture {
    public int width;
    public int height;
    public boolean translucent;
    public byte[] pixels;

    public Texture(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        this.width = buffer.readInt();
        this.height = buffer.readInt();
        this.translucent = buffer.readInt() == 1;
        this.pixels = new byte[this.width * this.height];

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.pixels[x + y * this.width] = buffer.readByte();
            }
        }
    }
}
