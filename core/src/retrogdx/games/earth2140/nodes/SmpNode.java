package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.earth2140.readers.Smp;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.AudioPreview;
import retrogdx.utils.SmartByteBuffer;

public class SmpNode extends AssetFileNode {
    public SmpNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Smp smp = new Smp(this.buffer);

        previewArea.add(new AudioPreview(smp.clip));
    }
}
