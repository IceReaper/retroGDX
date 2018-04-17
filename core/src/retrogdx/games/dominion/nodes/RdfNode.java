package retrogdx.games.dominion.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.dominion.readers.Rdf;
import retrogdx.generic.nodes.RiffWaveNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class RdfNode extends FolderVirtualNode {
    public RdfNode(FileHandle file, GameNode gameNode) {
        super(file, gameNode);
    }

    protected Array<Tree.Node> populate() {
        Rdf rdf = new Rdf(this.buffer);

        Array<Tree.Node> nodes = new Array<>();

        for (Map.Entry<String, SmartByteBuffer> file : rdf.getFiles().entrySet()) {
            nodes.add(new RiffWaveNode(file.getKey(), file.getValue()));
        }

        return nodes;
    }
}
