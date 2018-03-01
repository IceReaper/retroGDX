package retrogdx.generic.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.RiffWave;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.AudioPreview;
import retrogdx.utils.SmartByteBuffer;

public class RiffWaveNode extends AssetFileNode {
    private SmartByteBuffer buffer;

    public RiffWaveNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name);

        this.buffer = buffer;
    }

    protected void showPreview() {
        RiffWave wave = new RiffWave(this.buffer);

        this.previewArea.add(new AudioPreview(wave.clip));
    }
}
