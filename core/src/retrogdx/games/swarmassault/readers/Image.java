package retrogdx.games.swarmassault.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Image {
    public int width;
    public int height;
    public byte[] pixels;

    public Image(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int returnPos = buffer.position();

        int fileOffset = buffer.readInt();
        int format = buffer.readInt();
        int fileSize = buffer.readInt();
        this.width = buffer.readInt();
        this.height = buffer.readInt();

        SmartByteBuffer imageData = buffer.slice(fileOffset, fileSize);

        if (format == 0) {
            this.pixels = imageData.readBytes(this.width * this.height);
        } else if (format == 1) {
            this.pixels = new byte[this.width * this.height];

            for (int y = 0; y < this.height; y++) {
                imageData.position(y * 4);
                int rowOffset = imageData.readInt();
                imageData.position(this.height * 4 + rowOffset);
                int x = 0;

                while (true) {
                    short length = imageData.readShort();

                    if (length == 0) {
                        break;
                    } else if (length < 0) {
                        for (int i = 0; i < -length; i++) {
                            this.pixels[y * this.width + x++] = 0x00;
                        }
                    } else {
                        for (int i = 0; i < length; i++) {
                            this.pixels[y * this.width + x++] = imageData.readByte();
                        }
                    }
                }
            }
        } else {
            System.out.print("UNKNOWN FORMAT " + format);
        }

        buffer.position(returnPos);
    }
}
