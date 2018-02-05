package retrogdx.games.dune2;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;
import retrogdx.ui.AssetFolderNode;
import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class PakFile extends AssetFolderNode {
    private SmartByteBuffer buffer;

    public PakFile(Table previewArea, FileHandle file) {
        super(previewArea, file.name());

        this.buffer = SmartByteBuffer.wrap(file);
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    protected Array<Node> populate() {
        Array<Node> nodes = new Array<>();
        this.buffer.position(0);

        while (true) {
            int offset = this.buffer.readInt();

            if (offset == 0) {
                break;
            }

            String fileName = this.buffer.readString();

            int endOffset = this.buffer.readInt();
            this.buffer.position(this.buffer.position() - 4);

            if (endOffset == 0) {
                endOffset = this.buffer.capacity();
            }

            if (fileName.endsWith(".INI")) {
                nodes.add(new IniNode(this.previewArea, fileName, this.buffer.getSliceInfo(offset, endOffset - offset)));
            } else {
                nodes.add(new Node(new VisLabel(fileName)));
            }
        }

        return nodes;
    }

    protected void showPreview() {
        this.previewArea.add(new VisLabel("Preview..."));
    }
}
