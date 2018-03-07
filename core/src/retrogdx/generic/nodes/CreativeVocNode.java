package retrogdx.generic.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.CreativeVoc;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.AudioPreview;
import retrogdx.utils.SmartByteBuffer;

public class CreativeVocNode extends AssetFileNode {
    public CreativeVocNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name, buffer);
    }

    protected void showPreview() {
        CreativeVoc voc = new CreativeVoc(this.buffer);

        this.previewArea.add(new AudioPreview(voc.clip));
    }
}
