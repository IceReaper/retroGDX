package retrogdx.games.futurecop.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.futurecop.readers.Snds;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.AudioPreview;
import retrogdx.utils.SmartByteBuffer;

public class SndsNode extends AssetFileNode {
    public SndsNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Snds snds = new Snds(this.buffer);

        previewArea.add(new AudioPreview(snds.clip));
    }
}
