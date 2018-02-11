package retrogdx.generic.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.PlainText;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.TextPreview;
import retrogdx.utils.SmartByteBuffer;

public class PlainTextNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;

    public PlainTextNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
    }

    protected void showPreview() {
        PlainText ini = new PlainText(this.smartByteBuffer);
        // TODO encoding?! Or is this just a gdx bug?
        this.previewArea.add(new TextPreview(ini.text));
    }
}
