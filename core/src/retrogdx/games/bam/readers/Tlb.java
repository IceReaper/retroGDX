package retrogdx.games.bam.readers;

import retrogdx.utils.SmartByteBuffer;

public class Tlb {
    public class TlbTile {
        public byte[] pixels;
    }

    public int width = 40;
    public int height = 38;
    public TlbTile[] tiles;

    public Tlb(SmartByteBuffer buffer) {
        buffer.readInt(); // id
        this.tiles = new TlbTile[buffer.readInt()];
        int numObjects = buffer.readInt();
        buffer.readBytes(64); // 0x00

        for (int i = 0; i < numObjects; i++) {
            buffer.readBytes(20 + 8 * 4); // TODO object
        }

        buffer.readBytes((128 - numObjects) * 52); // 0x00

        for (int i = 0; i < this.tiles.length; i++) {
            this.tiles[i] = this.readTile(buffer);
        }
    }

    private TlbTile readTile(SmartByteBuffer buffer) {
        TlbTile tile = new TlbTile();

        buffer.readUByte(); // id
        buffer.readInt(); // swapTile
        buffer.readInt(); // aniRes
        buffer.readInt(); // aniDelay

        tile.pixels = new byte[this.width * this.height];

        for (int i = 0; i < this.width * this.height; i += 2) {
            tile.pixels[i] = buffer.readByte();
            tile.pixels[i + 1] = tile.pixels[i];
        }

        return tile;
    }
}
