package retrogdx.games.futurecop.readers;

import retrogdx.utils.SmartByteBuffer;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.nio.ByteOrder;

public class Snds {
    public Clip clip;

    public Snds(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        buffer.readInt(); // TODO Unk

        byte[] audioData = buffer.readBytes(buffer.capacity() - buffer.position());

        // TODO values are guessed! Find real values, or verifyExecutableExists that they are correct!
        AudioFormat format = new AudioFormat(22050, 8, 1, false, false);
        AudioInputStream input = new AudioInputStream(new ByteArrayInputStream(audioData), format, audioData.length);

        try {
            this.clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, input.getFormat()));
            this.clip.open(input);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
