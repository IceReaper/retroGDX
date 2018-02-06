package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.readers.Ini;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.TextPreview;
import retrogdx.utils.SmartByteBuffer;
import retrogdx.utils.SmartByteBuffer.SliceInfo;

public class IniNode extends AssetFileNode {
    private SliceInfo sliceInfo;

    public IniNode(Table previewArea, String name, SliceInfo sliceInfo) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
    }

    protected void showPreview() {
        Ini ini = new Ini(this.sliceInfo.slice());
        // TODO encoding?! Or is this just a gdx bug?
        this.previewArea.add(new TextPreview(ini.getText()));
    }
}
