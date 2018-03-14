package retrogdx.generic.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class AutodeskFlic {
    public int width;
    public int height;
    public int[][] frames;
    public int speed;
    private int frameIndex = 0;
    private int[] palette = new int[256];

    public AutodeskFlic(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        buffer.readInt(); // size
        buffer.readUShort(); // type
        int frames = buffer.readUShort();
        this.width = buffer.readUShort();
        this.height = buffer.readUShort();
        buffer.readUShort(); // depth
        buffer.readUShort(); // flags
        this.speed = buffer.readInt();
        buffer.readShort(); // reserved
        buffer.readInt(); // created
        buffer.readInt(); // creator
        buffer.readInt(); // updated
        buffer.readInt(); // updater
        buffer.readUShort(); // aspect_dx
        buffer.readUShort(); // aspect_dy
        buffer.readUShort(); // ext_flags
        buffer.readUShort(); // keyframes
        buffer.readUShort(); // totalframes
        buffer.readInt(); // req_memory
        buffer.readUShort(); // max_regions
        buffer.readUShort(); // transp_num
        buffer.readBytes(24); // reserved
        buffer.readInt(); // oframe1
        buffer.readInt(); // oframe2
        buffer.readBytes(40); // reserved

        // TODO either remove the first or the last frame!
        this.frames = new int[frames][this.width * this.height];

        while (buffer.position() < buffer.capacity()) {
            int chunkSize = buffer.readInt();
            int chunkType = buffer.readUShort();

            switch (chunkType) {
                case 0xf1fa:
                    this.readFrame(buffer);
                    this.frameIndex++;
                    break;

                default:
                    buffer.readBytes(chunkSize - 6);
                    break;
            }
        }
    }

    private void readFrame(SmartByteBuffer buffer) {
        int chunks = buffer.readUShort();
        buffer.readUShort(); // delay
        buffer.readUShort(); // reserved
        buffer.readUShort(); // width
        buffer.readUShort(); // height

        for (int i = 0; i < chunks; i++) {
            int chunkSize = buffer.readInt();
            int chunkType = buffer.readUShort();

            int[] frame;

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
                            this.palette[skipColors + l] = (buffer.readByte() << 24) | (buffer.readByte() << 16) | (buffer.readByte() << 8) | 0xff;
                        }
                    }

                    break;

                case 7:
                    // FLI_DELTA
                    frame = this.deltaFrame(buffer.slice(chunkSize - 6));

                    // Skip the ring frame!
                    if (this.frameIndex < this.frames.length) {
                        this.frames[this.frameIndex] = frame;
                    }

                    break;

                case 12:
                    // FLI_LC
                    frame = this.lineCompressedFrame(buffer.slice(chunkSize - 6));

                    // Skip the ring frame!
                    if (this.frameIndex < this.frames.length) {
                        this.frames[this.frameIndex] = frame;
                    }

                    break;

                case 15:
                    // FLI_BRUN
                    frame = this.byteRunFrame(buffer.slice(chunkSize - 6));

                    // Skip the ring frame!
                    if (this.frameIndex < this.frames.length) {
                        this.frames[this.frameIndex] = frame;
                    }

                    break;

                case 16:
                    // FLI_COPY
                    frame = this.copyFrame(buffer.slice(this.width * this.height)); // chunkSize looks wrong here

                    // Skip the ring frame!
                    if (this.frameIndex < this.frames.length) {
                        this.frames[this.frameIndex] = frame;
                    }

                    break;

                case 18:
                    // FLI_MINI
                    // Just a thumbnail, we can safely skip this one
                    buffer.readBytes(chunkSize - 6);
                    break;

                default:
                    buffer.readBytes(chunkSize - 6);
                    break;
            }
        }
    }

    private int[] lineCompressedFrame(SmartByteBuffer buffer) {
        int[] frame = new int[this.width * this.height];
        System.arraycopy(this.frames[frameIndex - 1], 0, frame, 0, frame.length);

        int firstLine = buffer.readUShort();
        int numLines = buffer.readUShort();

        for (int y = firstLine; y < firstLine + numLines; y++) {
            int numChunks = buffer.readUByte();
            int x = 0;

            for (int i = 0; i < numChunks; i++) {
                x += buffer.readUByte();
                byte count = buffer.readByte();

                if (count < 0) {
                    int index = buffer.readUByte();

                    for (int j = 0; j < -count; j++) {
                        frame[y * this.width + x++] = this.palette[index];
                    }
                } else {
                    for (int j = 0; j < count; j++) {
                        frame[y * this.width + x++] = this.palette[buffer.readUByte()];
                    }
                }
            }
        }

        return frame;
    }

    private int[] copyFrame(SmartByteBuffer buffer) {
        int[] frame = new int[this.width * this.height];

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                frame[y * this.width + x] = this.palette[buffer.readUByte()];
            }
        }

        return frame;
    }

    private int[] byteRunFrame(SmartByteBuffer buffer) {
        int[] frame = new int[this.width * this.height];

        for (int y = 0; y < this.height; y++) {
            int numChunks = buffer.readUByte();
            int x = 0;

            for (int i = 0; i < numChunks; i++) {
                byte count = buffer.readByte();

                if (count > 0) {
                    int index = buffer.readUByte();

                    for (int j = 0; j < count; j++) {
                        frame[y * this.width + x++] = this.palette[index];
                    }
                } else {
                    for (int j = 0; j < -count; j++) {
                        frame[y * this.width + x++] = this.palette[buffer.readUByte()];
                    }
                }
            }
        }

        return frame;
    }

    private int[] deltaFrame(SmartByteBuffer buffer) {
        int[] frame = new int[this.width * this.height];
        System.arraycopy(this.frames[frameIndex - 1], 0, frame, 0, frame.length);

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
                            frame[y * this.width + x++] = this.palette[buffer.readUByte()];
                            frame[y * this.width + x++] = this.palette[buffer.readUByte()];
                        }
                    } else {
                        int index1 = buffer.readUByte();
                        int index2 = buffer.readUByte();

                        for (int j = 0; j < -count; j++) {
                            frame[y * this.width + x++] = this.palette[index1];
                            frame[y * this.width + x++] = this.palette[index2];
                        }
                    }
                }

                y++;
            } else {
                y += -numChunks;
            }
        }

        return frame;
    }
}
