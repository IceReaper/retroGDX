package retrogdx.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;

public abstract class AssetNode extends Node {
    protected Table previewArea;

    public AssetNode(Table previewArea, String name) {
        super(new VisLabel(name));

        this.previewArea = previewArea;

        this.add(new Node(new VisLabel("...")));
    }

    public void setExpanded(boolean expanded) {
        if (expanded) {
            this.removeAll();
            this.addAll(this.populate());
        } else {
            this.removeAll();
            this.add(new Node(new VisLabel("...")));
        }

        super.setExpanded(expanded);
    }

    protected abstract Array<Node> populate();

    protected abstract void showPreview();
}
