package retrogdx.generic.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import retrogdx.generic.readers.CreativeVoc;
import retrogdx.ui.AssetFileNode;
import retrogdx.utils.SmartByteBuffer;

public class CreativeVocNode extends AssetFileNode {
    private SmartByteBuffer buffer;

    public CreativeVocNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name);

        this.buffer = buffer;
    }

    protected void showPreview() {
        CreativeVoc voc = new CreativeVoc(this.buffer);
        voc.clip.start();
    }
}
