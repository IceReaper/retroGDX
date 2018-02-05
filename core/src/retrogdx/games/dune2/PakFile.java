package retrogdx.games.dune2;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;
import retrogdx.ui.AssetNode;
import retrogdx.utils.SmartByteBuffer;

public class PakFile extends AssetNode {
    private SmartByteBuffer input;

    public PakFile(Table previewArea, FileHandle file) {
        super(previewArea, file.name());

        this.input = SmartByteBuffer.wrap(file);
    }

    protected Array<Node> populate() {
        Array<Node> nodes = new Array<>();

        while (true) {
            int offset = input.readInt();

            if (offset == 0) {
                break;
            }

            String fileName = input.readString();

            nodes.add(new Node(new VisLabel(fileName)));
        }

        return nodes;
    }

    protected void showPreview() {
        this.previewArea.add(new VisLabel("Preview..."));
    }
}
