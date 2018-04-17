package retrogdx.generic.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.PlainText;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.TextPreview;
import retrogdx.utils.SmartByteBuffer;

public class PlainTextNode extends AssetFileNode {
    private String encoding;

    public PlainTextNode(String name, SmartByteBuffer buffer) {
        this(name, buffer, "UTF-8");
    }

    public PlainTextNode(String name, SmartByteBuffer buffer, String encoding) {
        super(name, buffer);
        this.encoding = encoding;
    }

    public void showPreview(Table previewArea) {
        PlainText ini = new PlainText(this.buffer, this.encoding);

        previewArea.add(new TextPreview(ini.text));
    }
}
