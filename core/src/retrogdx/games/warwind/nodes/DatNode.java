package retrogdx.games.warwind.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.warwind.readers.Dat;
import retrogdx.ui.nodes.GameNode;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map.Entry;

public class DatNode extends FolderVirtualNode {
    public DatNode(FileHandle file, GameNode gameNode) {
        super(file, gameNode);
    }

    protected Array<Tree.Node> populate() {
        Dat dat = new Dat(this.buffer);

        Array<Tree.Node> nodes = new Array<>();

        for (Entry<String, SmartByteBuffer> file : dat.getFiles().entrySet()) {
            nodes.add(new LvlNode(file.getKey(), file.getValue()));
        }

        return nodes;
    }
}
