package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.earth2140.readers.DatMap;
import retrogdx.ui.AssetFileNode;
import retrogdx.utils.SmartByteBuffer;

public class DatMapNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;

    public DatMapNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
    }

    protected void showPreview() {
        DatMap dat = new DatMap(this.smartByteBuffer);

        // TODO render the terrain as preview.
        // TODO do we want to show anything else?
    }
}
