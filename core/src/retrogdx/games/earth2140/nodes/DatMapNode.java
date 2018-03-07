package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.earth2140.readers.DatMap;
import retrogdx.ui.AssetFileNode;
import retrogdx.utils.SmartByteBuffer;

public class DatMapNode extends AssetFileNode {
    public DatMapNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name, buffer);
    }

    protected void showPreview() {
        DatMap dat = new DatMap(this.buffer);

        // TODO render the terrain as preview.
        // TODO do we want to show anything else? Map Objects, Layers, Entities, Regions, ...?
    }
}
