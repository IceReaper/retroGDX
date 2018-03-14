package retrogdx.games.warcraft.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Tile {
    public class TileInfo {
        public int index;
        public boolean flipX;
        public boolean flipY;
    }

    public TileInfo[][] tiles;

    public Tile(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        this.tiles = new TileInfo[buffer.capacity() / 8][4];

        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < 4; j++) {
                TileInfo tileInfo = new TileInfo();
                this.tiles[i][j] = tileInfo;
                int data = buffer.readUShort();

                tileInfo.index = ((data & 0xfffc) << 1) / (8 * 8);
                tileInfo.flipX = ((data >> 1) & 0x1) == 1;
                tileInfo.flipY = (data & 0x1) == 1;
            }
        }
    }
}
