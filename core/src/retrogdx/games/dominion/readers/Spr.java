package retrogdx.games.dominion.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Spr {
    public byte[] pixels;
    public int width;
    public int height;

    public Spr(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        this.width = buffer.readInt();
        this.height = buffer.readInt();
        int unk1 = buffer.readInt(); // TODO
        int unk2 = buffer.readInt(); // TODO origin x?
        int unk3 = buffer.readInt(); // TODO origin y?
        buffer.readInt(); // id

        if (unk1 == 0) {
            // TODO CRASHES!
        } else {
            this.pixels = new byte[this.width * this.height];

            for (int y = 0; y < this.height; y++) {
                short numChunks = buffer.readUByte();
                byte unk5 = buffer.readByte(); // TODO

                int x = 0;

                for (int j = 0; j < numChunks; j++) {
                    int bitmask = buffer.readUShort();
                    int length = bitmask & 0b11111111111;
                    int alpha = bitmask >> 11 & 0b11111;

                    if (alpha == 0b00000) {
                        for (int p = 0; p < length; p++) {
                            this.pixels[(this.height - y - 1) * this.width + x] = (byte) 0x00;
                            x++;
                        }
                    } else if (alpha == 0b00001) {
                        for (int p = 0; p < length; p++) {
                            this.pixels[(this.height - y - 1) * this.width + x] = (byte) 0x01;
                            x++;
                        }
                    } else if (alpha == 0b00010) {
                        for (int p = 0; p < length; p++) {
                            this.pixels[(this.height - y - 1) * this.width + x] = (byte) 0x02;
                            x++;
                        }
                    } else if (alpha == 0b00011) {
                        for (int p = 0; p < length; p++) {
                            if ((this.height - y - 1) * this.width + x < this.pixels.length) {
                                this.pixels[(this.height - y - 1) * this.width + x] = buffer.readByte();

                            } else {
                                buffer.readByte();
                            }

                            x++;
                        }

                        if (length % 2 != 0) {
                            // TODO what is this byte?
                            buffer.readByte();
                        }
                    }
                }
            }
        }
    }
}
