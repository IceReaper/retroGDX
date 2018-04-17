package retrogdx.games.warwind.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.warwind.readers.Lvl;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.utils.SmartByteBuffer;

public class LvlNode extends AssetFileNode {
    public LvlNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Lvl lvl = new Lvl(this.buffer);

        // TODO render the terrain as preview.
        // TODO do we want to show anything else? Map Objects, Layers, Entities, Regions, ...?
    }
}
