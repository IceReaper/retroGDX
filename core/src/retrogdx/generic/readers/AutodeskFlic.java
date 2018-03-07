package retrogdx.generic.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class AutodeskFlic {
    public int width;
    public int height;
    public int[][] frames;
    public int speed;

    public AutodeskFlic(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        buffer.readInt(); // size
        buffer.readUShort(); // magic
        int frames = buffer.readUShort();
        this.width = buffer.readUShort();
        this.height = buffer.readUShort();
        buffer.readUShort(); // septh
        buffer.readUShort(); // flags
        this.speed = buffer.readUShort();
        buffer.readInt(); // next
        buffer.readInt(); // frit
        buffer.readBytes(102); // expand

        this.frames = new int[frames][this.width * this.height];
        int[] palette = new int[256];

        for (int i = 0; i < frames; i++) {
            buffer.readInt(); // frameSize
            buffer.readUShort(); // frameMagic
            int frameChunks = buffer.readUShort();
            buffer.readBytes(8); // frameExpand

            for (int j = 0; j < frameChunks; j++) {
                int chunkSize = buffer.readInt();
                int chunkType = buffer.readUShort();

                switch (chunkType) {
                    case 4:
                        // FLI_256_COLOR
                        int numPackets = buffer.readUShort();

                        for (int k = 0; k < numPackets; k++) {
                            int skipColors = buffer.readUByte();
                            int numColors = buffer.readUByte();

                            if (numColors == 0) {
                                numColors = 256;
                            }

                            for (int l = 0; l < numColors; l++) {
                                palette[skipColors + l] = (buffer.readByte() << 24) | (buffer.readByte() << 16) | (buffer.readByte() << 8) | 0xff;
                            }
                        }

                        break;

                    case 7:
                        // FLI_DELTA
                        this.frames[i] = this.modifyFrame(buffer.slice(chunkSize - 6), palette, this.frames[i - 1]);
                        break;

                    case 15:
                        // FLI_BRUN
                        this.frames[i] = this.decompressFrame(buffer.slice(chunkSize - 6), palette);
                        break;

                    case 18:
                        // FLI_MINI
                        // We can safely skip this chunk, as its a smaller thumbnail of the first frame.
                        buffer.readBytes(chunkSize - 6);
                        break;
                }
            }
        }
    }

    private int[] decompressFrame(SmartByteBuffer buffer, int[] palette) {
        int[] frame = new int[this.width * this.height];

        for (int y = 0; y < this.height; y++) {
            int numChunks = buffer.readUByte();
            int x = 0;

            for (int i = 0; i < numChunks; i++) {
                byte count = buffer.readByte();

                if (count > 0) {
                    int index = buffer.readUByte();

                    for (int j = 0; j < count; j++) {
                        frame[y * this.width + x++] = palette[index];
                    }
                } else {
                    for (int j = 0; j < -count; j++) {
                        frame[y * this.width + x++] = palette[buffer.readUByte()];
                    }
                }
            }
        }

        return frame;
    }

    private int[] modifyFrame(SmartByteBuffer buffer, int[] palette, int[] prevFrame) {
        int[] frame = new int[this.width * this.height];

        int numLines = buffer.readUShort();
        int y = 0;

        while (numLines > 0) {
            int numChunks = buffer.readShort();

            if (numChunks > 0) {
                numLines--;
                int x = 0;

                for (int i = 0; i < numChunks; i++) {
                    int skip = buffer.readUByte();
                    x += skip;
                    byte count = buffer.readByte();

                    if (count > 0) {
                        for (int j = 0; j < count; j++) {
                            frame[y * this.width + x++] = palette[buffer.readUByte()];
                            frame[y * this.width + x++] = palette[buffer.readUByte()];
                        }
                    } else {
                        int index1 = buffer.readUByte();
                        int index2 = buffer.readUByte();

                        for (int j = 0; j < -count; j++) {
                            frame[y * this.width + x++] = palette[index1];
                            frame[y * this.width + x++] = palette[index2];
                        }
                    }
                }

                y++;
            } else {
                System.arraycopy(prevFrame, y * this.width, frame, y * this.width, this.width * -numChunks);
                y += -numChunks;
            }
        }

        return frame;
    }
}
