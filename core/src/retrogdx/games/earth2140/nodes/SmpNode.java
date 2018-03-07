package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.earth2140.readers.Smp;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.AudioPreview;
import retrogdx.utils.SmartByteBuffer;

public class SmpNode extends AssetFileNode {
    public SmpNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name, buffer);
    }

    protected void showPreview() {
        Smp smp = new Smp(this.buffer);

        this.previewArea.add(new AudioPreview(smp.clip));
    }
}
