package retrogdx.games.eradicator.readers;

import retrogdx.utils.SmartByteBuffer;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.nio.ByteOrder;

public class Raw {
    public Clip clip;

    public Raw(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        byte[] audioData = buffer.readBytes(buffer.capacity());

        // TODO values are guessed! Find real values, or verifyExecutableExists that they are correct!
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
        AudioInputStream input = new AudioInputStream(new ByteArrayInputStream(audioData), format, audioData.length);

        try {
            this.clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, input.getFormat()));
            this.clip.open(input);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
