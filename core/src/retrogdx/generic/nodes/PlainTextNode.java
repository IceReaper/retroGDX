package retrogdx.generic.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.PlainText;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.TextPreview;
import retrogdx.utils.SmartByteBuffer;

public class PlainTextNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;
    private String encoding;

    public PlainTextNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        this(previewArea, name, smartByteBuffer, "UTF-8");
    }

    public PlainTextNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer, String encoding) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
        this.encoding = encoding;
    }

    protected void showPreview() {
        PlainText ini = new PlainText(this.smartByteBuffer, this.encoding);

        this.previewArea.add(new TextPreview(ini.text));
    }
}
