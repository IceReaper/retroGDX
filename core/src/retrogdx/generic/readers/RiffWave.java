package retrogdx.generic.readers;

import retrogdx.utils.SmartByteBuffer;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.nio.ByteOrder;

public class RiffWave {
    public Clip clip;

    public RiffWave(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        byte[] bytes = buffer.readBytes(buffer.capacity());

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(bytes));
            AudioFormat audioFormat = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            this.clip = (Clip) AudioSystem.getLine(info);
            this.clip.open(audioStream);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
