package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.earth2140.readers.Smp;
import retrogdx.ui.AssetFileNode;
import retrogdx.utils.SliceInfo;

public class SmpNode extends AssetFileNode {
    private SliceInfo sliceInfo;

    public SmpNode(Table previewArea, String name, SliceInfo sliceInfo) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
    }

    protected void showPreview() {
        Smp smp = new Smp(this.sliceInfo.slice());

        // TODO replace with audio player widget
        smp.clip.start();
    }
}
