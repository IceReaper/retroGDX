package retrogdx.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.kotcrab.vis.ui.widget.VisLabel;

public abstract class AssetFileNode extends Node {
    protected Table previewArea;

    public AssetFileNode(Table previewArea, String name) {
        super(new VisLabel(name));

        this.previewArea = previewArea;
    }

    protected abstract void showPreview();
}
