package retrogdx.ui.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.kotcrab.vis.ui.widget.VisLabel;
import retrogdx.utils.SmartByteBuffer;

public abstract class AssetFileNode extends Node {
    protected String name;
    protected SmartByteBuffer buffer;

    public AssetFileNode(String name, SmartByteBuffer buffer) {
        super(new VisLabel(name));
        this.name = name;
        this.buffer = buffer;
    }

    public abstract void showPreview(Table previewArea);
}
