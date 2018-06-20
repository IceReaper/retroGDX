package retrogdx.games.swarmassault.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.swarmassault.readers.Sdf;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map.Entry;

public class SdfNode extends FolderVirtualNode {
    public SdfNode(FileHandle file, GameNode gameNode) {
        super(file, gameNode);
    }

    public SdfNode(String name, SmartByteBuffer buffer, GameNode gameNode) {
        super(name, buffer, gameNode);
    }

    protected Array<Tree.Node> populate() {
        Sdf sdf = new Sdf(this.buffer);

        Array<Tree.Node> nodes = new Array<>();

        for (Entry<String, SmartByteBuffer> file: sdf.getFiles().entrySet()) {
            nodes.add(new SoundNode(file.getKey(), file.getValue()));
        }

        return nodes;
    }
}
