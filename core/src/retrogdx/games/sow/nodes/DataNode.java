package retrogdx.games.sow.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.sow.readers.Data;
import retrogdx.ui.nodes.GameNode;
import retrogdx.ui.nodes.AssetUnsupportedNode;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class DataNode extends FolderVirtualNode {
    private FileHandle indexFile;

    public DataNode(FileHandle file, FileHandle indexFile, GameNode gameNode) {
        super(file, gameNode);

        this.indexFile = indexFile;
    }

    protected Array<Tree.Node> populate() {
        Data data = new Data(this.buffer, SmartByteBuffer.wrap(this.indexFile.readBytes()));

        Array<Tree.Node> nodes = new Array<>();

        for (Map.Entry<String, SmartByteBuffer> file : data.getFiles().entrySet()) {
            if (file.getKey().endsWith(".ps6")) {
                nodes.add(new Ps6Node(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".po1")) {
                // TODO offset?
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".po2")) {
                // TODO offset?
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".msk")) {
                // TODO mask?
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".sha")) {
                // TODO shadow?
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else {
                System.out.println("Unknown file format: " + file.getKey());
            }
        }

        return nodes;
    }
}
