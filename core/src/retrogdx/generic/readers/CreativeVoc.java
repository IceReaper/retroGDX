package retrogdx.generic.readers;

import retrogdx.utils.SmartByteBuffer;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.nio.ByteOrder;

public class CreativeVoc {
    public Clip clip;

    public CreativeVoc(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        buffer.readString(19); // signature
        buffer.readByte(); // magic
        buffer.readUShort(); // headerSize
        buffer.readUShort(); // version
        buffer.readUShort(); // checksum

        while (buffer.position() < buffer.capacity()) {
            byte blockType = buffer.readByte();

            if (blockType == 0) {
                break;
            }

            int blockSize = buffer.readUByte() + (buffer.readUShort() << 8);

            switch (blockType) {
                case 1:
                    int frequency = buffer.readUByte();
                    int codec = buffer.readUByte();
                    byte[] audioData = buffer.readBytes(blockSize - 2);

                    AudioFormat format;

                    switch (codec) {
                        case 0:
                            format = new AudioFormat(1000000 / (256 - frequency), 8, 1, false, false);
                            break;
                        default:
                            System.out.println(codec);
                            continue;
                    }

                    AudioInputStream input = new AudioInputStream(new ByteArrayInputStream(audioData), format, audioData.length);

                    try {
                        this.clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, input.getFormat()));
                        this.clip.open(input);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    break;
                case 3:
                    // TODO Some Dune 2 files use a silence block
                    buffer.readBytes(blockSize);
                    break;
            }
        }
    }
}
