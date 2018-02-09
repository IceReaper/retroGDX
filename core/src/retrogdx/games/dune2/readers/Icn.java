package retrogdx.games.dune2.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Icn extends Iff {
    public int width;
    public int height;
    public byte[][] tiles;
    private short bpp;
    private byte[][] rpal;
    private byte[] rtbl;

    public Icn(SmartByteBuffer buffer) {
        super(buffer);
    }

    protected void read(SmartByteBuffer buffer) {
        String type = buffer.readString(4); // ICON

        while (buffer.position() < buffer.capacity()) {
            String token = buffer.readString(4);
            int length = buffer.readInt();
            SmartByteBuffer subBuffer = buffer.slice(length);

            switch (token) {
                case "SINF":
                    this.readSinf(subBuffer);
                    break;
                case "SSET":
                    this.readSset(subBuffer);
                    break;
                case "RPAL":
                    this.readRpal(subBuffer);
                    break;
                case "RTBL":
                    this.readRtbl(subBuffer);
                    break;
            }
        }

        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                this.tiles[i][j] = this.rpal[this.rtbl[i] & 0xff][this.tiles[i][j] & 0xff];
            }
        }
    }

    private void readSinf(SmartByteBuffer buffer) {
        this.width = buffer.readUByte();
        this.height = buffer.readUByte();
        int shift = buffer.readUByte();
        this.bpp = buffer.readUByte();

        this.width <<= shift;
        this.height <<= shift;
    }

    private void readSset(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        int unk1 = buffer.readUShort();
        int tileDataSize = buffer.readUShort();
        int unk3 = buffer.readUShort();
        int unk4 = buffer.readUShort();

        this.tiles = new byte[tileDataSize / this.bpp * 8 / this.width / this.height][this.width * this.height];

        int byteBuffer = 0x00;
        int bitsLeft = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int color = 0x00;

                for (int k = 0; k < this.bpp; k++) {
                    if (bitsLeft == 0) {
                        bitsLeft = 8;
                        byteBuffer = buffer.readByte();
                    }

                    color = (color << 1) | ((byteBuffer >> --bitsLeft) & 0b1);
                }

                this.tiles[i][j] = (byte) color;
            }
        }
    }

    private void readRpal(SmartByteBuffer buffer) {
        int numColors = 1 << this.bpp;
        this.rpal = new byte[buffer.capacity() / numColors][numColors];

        for (int i = 0; i < this.rpal.length; i++){
            for (int j = 0; j < numColors; j++) {
                this.rpal[i][j] = buffer.readByte();
            }
        }
    }

    private void readRtbl(SmartByteBuffer buffer) {
        this.rtbl = buffer.readBytes(buffer.capacity());
    }
}
