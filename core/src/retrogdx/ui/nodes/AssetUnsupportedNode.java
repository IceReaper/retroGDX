package retrogdx.ui.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.utils.SmartByteBuffer;

public class AssetUnsupportedNode extends AssetFileNode {
    public AssetUnsupportedNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        // TODO show "unsupported" text!
    }
}
