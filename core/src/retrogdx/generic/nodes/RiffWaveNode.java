package retrogdx.generic.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.RiffWave;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.AudioPreview;
import retrogdx.utils.SmartByteBuffer;

public class RiffWaveNode extends AssetFileNode {
    public RiffWaveNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        RiffWave wave = new RiffWave(this.buffer);

        previewArea.add(new AudioPreview(wave.clip));
    }
}
