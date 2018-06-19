package retrogdx.games.eradicator.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.eradicator.readers.Raw;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.AudioPreview;
import retrogdx.utils.SmartByteBuffer;

public class RawNode extends AssetFileNode {
    public RawNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Raw raw = new Raw(this.buffer);

        previewArea.add(new AudioPreview(raw.clip));
    }
}
