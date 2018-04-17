package retrogdx.generic.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.CreativeVoc;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.AudioPreview;
import retrogdx.utils.SmartByteBuffer;

public class CreativeVocNode extends AssetFileNode {
    public CreativeVocNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        CreativeVoc voc = new CreativeVoc(this.buffer);

        previewArea.add(new AudioPreview(voc.clip));
    }
}
