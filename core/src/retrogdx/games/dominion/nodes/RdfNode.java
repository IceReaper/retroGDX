package retrogdx.games.dominion.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.dominion.readers.Rdf;
import retrogdx.generic.nodes.RiffWaveNode;
import retrogdx.ui.AssetFolderNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class RdfNode extends AssetFolderNode {
    public RdfNode(Table previewArea, FileHandle file) {
        super(previewArea, file);
    }

    protected Array<Tree.Node> populate() {
        Rdf rdf = new Rdf(SmartByteBuffer.wrap(this.file.readBytes()));

        Array<Tree.Node> nodes = new Array<>();

        for (Map.Entry<String, SmartByteBuffer> file : rdf.getFiles().entrySet()) {
            nodes.add(new RiffWaveNode(this.previewArea, file.getKey(), file.getValue()));
        }

        return nodes;
    }
}
