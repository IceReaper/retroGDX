package retrogdx.games.earth2140.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Mix {
    public class MixImage {
        public int width;
        public int height;
        public int paletteIndex;
        public byte[] pixelsIndexed;
        public int[] pixelsRgb;
    }

    public MixImage[] images;
    public int[][] palettes;

    public Mix(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        buffer.readString(10); // "MIX FILE  "
        buffer.readInt(); // dataLength
        int dataCount = buffer.readInt();
        int dataOffset = buffer.readInt();
        int paletteCount = buffer.readInt();
        int paletteStartIndex = buffer.readInt();
        buffer.readInt(); // paletteOffset

        buffer.readString(5); // "ENTRY"
        int[] dataOffsets = new int[dataCount];

        for (int i = 0; i < dataCount; i++) {
            dataOffsets[i] = buffer.readInt() + dataOffset;
        }

        buffer.readString(5); // " PAL "
        this.palettes = new int[paletteCount][256];

        for (int i = 0; i < paletteCount; i++) {
            for (int j = 0; j < 256; j++) {
                this.palettes[i][j] = (buffer.readUByte() << 24) | (buffer.readUByte() << 16) | (buffer.readUByte() << 8) | 0xff;
            }
        }

        buffer.readString(5); // "DATA "
        this.images = new MixImage[dataCount];

        for (int i = 0; i < dataCount; i++) {
            buffer.position(dataOffsets[i]);
            this.images[i] = this.readImage(buffer, paletteStartIndex);
        }
    }

    private MixImage readImage(SmartByteBuffer buffer, int paletteStartIndex) {
        MixImage image = new MixImage();
        image.width = buffer.readUShort();
        image.height = buffer.readUShort();
        int format = buffer.readUByte();
        image.paletteIndex = buffer.readUByte() - paletteStartIndex;

        if (format == 1) {
            // Uncompressed, indexed
            image.pixelsIndexed = buffer.readBytes(image.width * image.height);
        } else if (format == 2) {
            // Uncompressed, 16bpp
            image.paletteIndex = -1;
            image.pixelsRgb = new int[image.width * image.height];

            for (int y = 0; y < image.height; y++) {
                for (int x = 0; x < image.width; x++) {
                    short pixel = buffer.readShort();
                    int r = ((pixel & 0b1111100000000000) >> 8) & 0xff;
                    int g = ((pixel & 0b0000011111100000) >> 3) & 0xff;
                    int b = ((pixel & 0b0000000000011111) << 3) & 0xff;
                    image.pixelsRgb[y * image.width + x] = (r << 24) | (g << 16) | (b << 8) | 0xff;
                }
            }
        } else if (format == 9) {
            // Compressed, indexed
            image.pixelsIndexed = new byte[image.width * image.height];

            buffer.readInt(); // width duplicate
            buffer.readInt(); // height duplicate
            buffer.readInt(); // dataBlockLength
            int scanLinesCount = buffer.readInt();
            int segmentBlockLength = buffer.readInt();

            buffer.readInt(); // headerInfoBlockSize
            buffer.readInt(); // == height * 2 + 38
            buffer.readInt(); // == height * 4 + 40
            buffer.readInt(); // headerBlockLength

            int[] scanLines = new int[scanLinesCount];
            int[] dataOffsets = new int[scanLinesCount];

            for (int j = 0; j < scanLinesCount; j++) {
                scanLines[j] = buffer.readUShort();
            }

            for (int j = 0; j < scanLinesCount; j++) {
                dataOffsets[j] = buffer.readUShort();
            }

            int[] segments = new int[segmentBlockLength];

            for (int j = 0; j < segmentBlockLength; j++) {
                segments[j] = buffer.readUByte();
            }

            int dataBlockOffset = buffer.position();
            int writePosition = 0;

            for (int index = 0; index < scanLinesCount; index++) {
                int line = scanLines[index] / 2;

                if (index + 1 < scanLines.length) {
                    int nextLine = scanLines[index + 1] / 2;
                    buffer.position(dataBlockOffset + dataOffsets[index]);
                    int lineSize = 0;

                    for (int segmentIndex = line; segmentIndex < nextLine; segmentIndex++) {
                        int skip = segments[segmentIndex * 2];
                        writePosition += skip;

                        int pixels = segments[segmentIndex * 2 + 1];

                        for (int j = 0; j < pixels; j++) {
                            image.pixelsIndexed[writePosition] = buffer.readByte();
                            writePosition++;
                        }

                        lineSize += skip + pixels;
                    }

                    writePosition += image.width - lineSize;
                }
            }
        }

        return image;
    }
}
