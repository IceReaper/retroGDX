package retrogdx.ui.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;

public abstract class AssetFolderNode extends AssetFileNode {
    public AssetFolderNode(String name) {
        super(name, null);

        // Prevent hiding "+" while not populated.
        this.add(new Node(new VisLabel("...")));
    }

    public void setExpanded(boolean expanded) {
        if (expanded) {
            this.removeAll();
            this.addAll(this.populate());
        } else {
            this.removeAll();
            // Prevent hiding "+" while not populated.
            this.add(new Node(new VisLabel("...")));
        }

        super.setExpanded(expanded);
    }

    protected abstract Array<Node> populate();

    public void showPreview(Table previewArea) {
    }
}
