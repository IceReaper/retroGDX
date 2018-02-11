package retrogdx.generic.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.ui.AssetFileNode;
import retrogdx.utils.SmartByteBuffer;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;

public class RiffWaveNode extends AssetFileNode {
    private SmartByteBuffer buffer;

    public RiffWaveNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name);

        this.buffer = buffer;
    }

    protected void showPreview() {
        byte[] bytes = this.buffer.readBytes(this.buffer.capacity());

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(bytes));
            AudioFormat audioFormat = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            Clip sourceLine = (Clip) AudioSystem.getLine(info);
            sourceLine.open(audioStream);
            sourceLine.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
