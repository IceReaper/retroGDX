package retrogdx.games.warcraft.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class War {
    private SmartByteBuffer buffer;
    private int i;

    public War(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        this.buffer.readInt(); // version
        int numFiles = this.buffer.readInt();

        for (this.i = 0; this.i < numFiles; this.i++) {
            int offset = this.buffer.readInt();

            this.buffer.block(offset, blockBuffer -> {
                int rawData = blockBuffer.readInt();
                boolean isCompressed = (rawData >> 24) != 0;
                int fileSize = rawData & 0xFFFFFF;

                SmartByteBuffer fileBuffer = SmartByteBuffer.wrap(isCompressed ? this.decompress(blockBuffer, fileSize) : blockBuffer.readBytes(fileSize));
                fileBuffer.order(ByteOrder.LITTLE_ENDIAN);

                String magic = fileBuffer.readString(4);
                fileBuffer.position(0);

                if (magic.equals("RIFF")) {
                    files.put(this.i + ".WAV", fileBuffer);
                    return;
                } else if (magic.equals("FORM")) {
                    files.put(this.i + ".XMI", fileBuffer);
                    return;
                } else if (magic.equals("Crea")) {
                    files.put(this.i + ".VOC", fileBuffer);
                    return;
                } else {
                    fileBuffer.position(0);

                    while (true) {
                        byte temp = fileBuffer.readByte();

                        if (temp == 0x00 || fileBuffer.position() == fileBuffer.capacity()) {
                            if (fileBuffer.position() == fileBuffer.capacity()) {
                                fileBuffer.position(0);
                                files.put(this.i + ".TXT", fileBuffer);
                                return;
                            } else {
                                break;
                            }
                        }
                    }
                }

                fileBuffer.position(0);

                if (fileSize == 256 * 3 || fileSize == 128 * 3) {
                    files.put(this.i + ".PAL", fileBuffer);

                    return;
                } else if (fileSize == 64 * 64 * 2) {
                    files.put(this.i + ".MAP", fileBuffer);
                    return;
                }

                fileBuffer.position(8);

                if (fileBuffer.readShort() == 0x20 && fileBuffer.readShort() == 0x40 && fileBuffer.readShort() == 0x60) {
                    fileBuffer.position(0);
                    files.put(this.i + ".TILE", fileBuffer);
                    return;
                }

                fileBuffer.position(0);

                if (fileBuffer.capacity() > 8 * 8 && Arrays.equals(fileBuffer.readBytes(8 * 8), new byte[8 * 8])) {
                    fileBuffer.position(0);
                    files.put(this.i + ".TILEPART", fileBuffer);
                    return;
                }

                fileBuffer.position(0);

                if (fileBuffer.readShort() * fileBuffer.readShort() + 4 == fileBuffer.capacity()) {
                    fileBuffer.position(0);
                    files.put(this.i + ".IMG", fileBuffer);
                    return;
                }

                fileBuffer.position(4);

                if (fileBuffer.readShort() * fileBuffer.readShort() + 8 == fileBuffer.capacity()) {
                    fileBuffer.position(0);
                    files.put(this.i + ".CUR", fileBuffer);
                    return;
                }

                fileBuffer.position(0);
                int numFrames = fileBuffer.readUShort();

                if (numFrames > 0 && fileBuffer.capacity() > 4 + numFrames * 8) {
                    fileBuffer.position(4);
                    for (int j = 0; j < numFrames; j++) {
                        fileBuffer.position(fileBuffer.position() + 2);
                        if (fileBuffer.readUByte() * fileBuffer.readUByte() + fileBuffer.readInt() == fileBuffer.capacity()) {
                            fileBuffer.position(0);
                            files.put(this.i + ".SPR", fileBuffer);
                            return;
                        }
                    }
                }

                fileBuffer.position(0);
                files.put(this.i + ".GUI", fileBuffer);
            });
        }

        return files;
    }

    private byte[] decompress(SmartByteBuffer buffer, int decompressedSize) {
        byte[] decompressed = new byte[decompressedSize];

        int writeOffset = 0;

        while (writeOffset < decompressedSize) {
            short mask = buffer.readUByte();

            for (int i = 0; i < 8 && writeOffset < decompressedSize; i++) {
                boolean uncompressed = ((mask >> i) & 0x1) == 1;

                if (uncompressed) {
                    decompressed[writeOffset++] = buffer.readByte();
                } else {
                    int compresion = buffer.readUShort();
                    int offset = compresion & 0xfff;
                    int numPixels = (compresion >> 12) + 3;

                    for (int j = 0; j < numPixels; j++) {
                        int readOffset = (writeOffset - (writeOffset % 4096) + offset + j);

                        if (readOffset >= writeOffset) {
                            readOffset -= 4096;
                        }

                        if (readOffset >= 0) {
                            decompressed[writeOffset] = decompressed[readOffset];
                        }

                        writeOffset++;

                        if (writeOffset == decompressedSize) {
                            break;
                        }
                    }
                }
            }
        }

        return decompressed;
    }
}
