package retrogdx.games.warwind.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.warwind.readers.Lvl;
import retrogdx.ui.AssetFileNode;
import retrogdx.utils.SmartByteBuffer;

public class LvlNode extends AssetFileNode {
    public LvlNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name, buffer);
    }

    protected void showPreview() {
        Lvl lvl = new Lvl(this.buffer);

        // TODO render the terrain as preview.
        // TODO do we want to show anything else? Map Objects, Layers, Entities, Regions, ...?
    }
}
