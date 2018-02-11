package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.earth2140.readers.DatMap;
import retrogdx.ui.AssetFileNode;
import retrogdx.utils.SliceInfo;

public class DatMapNode extends AssetFileNode {
    private SliceInfo sliceInfo;

    public DatMapNode(Table previewArea, String name, SliceInfo sliceInfo) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
    }

    protected void showPreview() {
        DatMap dat = new DatMap(this.sliceInfo.slice());

        // TODO render the terrain as preview.
        // TODO do we want to show anything else?
    }
}
