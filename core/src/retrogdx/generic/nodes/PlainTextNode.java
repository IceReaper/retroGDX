package retrogdx.generic.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.PlainText;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.TextPreview;
import retrogdx.utils.SmartByteBuffer;

public class PlainTextNode extends AssetFileNode {
    private String encoding;

    public PlainTextNode(Table previewArea, String name, SmartByteBuffer buffer) {
        this(previewArea, name, buffer, "UTF-8");
    }

    public PlainTextNode(Table previewArea, String name, SmartByteBuffer buffer, String encoding) {
        super(previewArea, name, buffer);
        this.encoding = encoding;
    }

    protected void showPreview() {
        PlainText ini = new PlainText(this.buffer, this.encoding);

        this.previewArea.add(new TextPreview(ini.text));
    }
}
