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
        int unk1 = buffer.readInt(); // TODO 0, 128 flipped?
        int unk2 = buffer.readInt(); // TODO origin x?
        int unk3 = buffer.readInt(); // TODO origin y?
        int unk4 = buffer.readInt(); // TODO id? Starts with 246, may skip some numbers

        this.pixels = new byte[this.width * this.height];


        // TODO this is not right yet. a lot of bytes are not read correctly and some images are messed up.
        for (int y = 0; y < this.height; y++) {
            short numChunks = buffer.readUByte();
            byte unk5 = buffer.readByte();

            int x = 0;

            for (int j = 0; j < numChunks; j++) {
                int bitmask = buffer.readUShort();

                int writeLength = bitmask & 0b1111111111;
                int flags = bitmask >> 10 & 0b111111;

                if ((flags & 0b111111) == 0b000000) {
                    x += writeLength;
                } else if ((flags & 0b000110) == 0b000110) {
                    // Color
                    writeLength += writeLength & 1; // TODO verify this!

                    for (int p = 0; p < writeLength; p++) {
                        if ((this.height - y - 1) * this.width + x < this.pixels.length) {
                            this.pixels[(this.height - y - 1) * this.width + x] = buffer.readByte();
                        } else {
                            buffer.readByte();
                        }
                        x++;
                    }
                } else if ((flags & 0b000010) == 0b000010) {
                    // Shadow
                    for (int p = 0; p < writeLength; p++) {
                        // TODO is there any reserved color we can use for here?
                        x++;
                    }

                } else if ((flags & 0b000001) == 0b000001) {
                    // TODO UNK 1
                    buffer.position(buffer.position() + 16);

                    if (x < this.width) {
                        x++;
                    }
                } else if ((flags & 0b000010) == 0b000010) {
                    // TODO isnt this shadow copy? should never be called!
                    // TODO UNK 2
                    for (int p = 0; p < writeLength; p++) {
                        x++;
                    }
                } else if ((flags & 0b000100) == 0b000100) {
                    // TODO UNK 3
                    for (int p = 0; p < writeLength; p++) {
                        x++;
                    }
                } else if ((flags & 0b001000) == 0b001000) {
                    // TODO UNK 4
                    for (int p = 0; p < writeLength; p++) {
                        x++;
                    }
                } else if ((flags & 0b010000) == 0b010000) {
                    // TODO UNK 5
                    for (int p = 0; p < writeLength; p++) {
                        x++;
                    }
                } else if ((flags & 0b100000) == 0b100000) {
                    // TODO UNK 6
                    for (int p = 0; p < writeLength; p++) {
                        x++;
                    }
                } else {
                    System.out.println("UNK FLAGS!");
                }
            }
        }
    }
}
