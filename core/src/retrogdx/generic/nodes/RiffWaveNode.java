package retrogdx.generic.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.RiffWave;
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
        RiffWave wave = new RiffWave(this.buffer);
        wave.clip.start();
    }
}
