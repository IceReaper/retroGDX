package retrogdx.generic.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.PlainText;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.TextPreview;
import retrogdx.utils.SliceInfo;

public class PlainTextNode extends AssetFileNode {
    private SliceInfo sliceInfo;

    public PlainTextNode(Table previewArea, String name, SliceInfo sliceInfo) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
    }

    protected void showPreview() {
        PlainText ini = new PlainText(this.sliceInfo.slice());
        // TODO encoding?! Or is this just a gdx bug?
        this.previewArea.add(new TextPreview(ini.text));
    }
}
