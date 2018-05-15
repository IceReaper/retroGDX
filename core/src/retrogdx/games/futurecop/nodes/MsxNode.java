package retrogdx.games.futurecop.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.futurecop.readers.Msx;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.AudioPreview;
import retrogdx.utils.SmartByteBuffer;

public class MsxNode extends AssetFileNode {
    public MsxNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Msx msx = new Msx(this.buffer);

        previewArea.add(new AudioPreview(msx.clip));
    }
}
