package retrogdx.games.warcraft.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Tilepart {
    public byte[][] tiles;

    public Tilepart(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        this.tiles = new byte[buffer.capacity() / (8 * 8)][8 * 8];

        for (int i = 0; i < this.tiles.length; i++) {
            this.tiles[i] = buffer.readBytes(8 * 8);
        }
    }
}
