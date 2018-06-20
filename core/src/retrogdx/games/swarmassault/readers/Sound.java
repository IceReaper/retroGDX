package retrogdx.games.swarmassault.readers;

import retrogdx.utils.SmartByteBuffer;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.nio.ByteOrder;

public class Sound {
    public Clip clip;

    public Sound(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int returnPos = buffer.position();

        int fileOffset = buffer.readInt();
        int fileSize = buffer.readInt();
        buffer.readShort(); // 01
        int channels = buffer.readShort();
        int sampleRate = buffer.readInt();
        buffer.readInt(); // sampleRate * sampleSizeInBytes
        buffer.readShort(); // sampleSizeInBytes
        int sampleSizeInBits = buffer.readShort();
        buffer.readInt(); // 00

        buffer.position(fileOffset);
        byte[] audioData = buffer.readBytes(fileSize);

        // ANTCRUSH, MOTHFLUTTER: not signed

        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, sampleSizeInBits == 16, false);
        AudioInputStream input = new AudioInputStream(new ByteArrayInputStream(audioData), format, audioData.length);

        try {
            this.clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, input.getFormat()));
            this.clip.open(input);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        buffer.position(returnPos);
    }
}
