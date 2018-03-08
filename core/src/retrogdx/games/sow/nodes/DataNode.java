package retrogdx.games.sow.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.sow.readers.Data;
import retrogdx.ui.AssetFolderNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class DataNode extends AssetFolderNode {
    private FileHandle dataFile;
    private FileHandle indexFile;

    public DataNode(Table previewArea, FileHandle dataFile, FileHandle indexFile) {
        super(previewArea, dataFile);

        this.dataFile = dataFile;
        this.indexFile = indexFile;
    }

    protected Array<Tree.Node> populate() {
        Data data = new Data(SmartByteBuffer.wrap(this.dataFile.readBytes()), SmartByteBuffer.wrap(this.indexFile.readBytes()));

        Array<Tree.Node> nodes = new Array<>();

        for (Map.Entry<String, SmartByteBuffer> file : data.getFiles().entrySet()) {
            if (file.getKey().endsWith(".ps6")) {
                nodes.add(new Ps6Node(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".po1")) {
                // TODO offset?
            } else if (file.getKey().endsWith(".po2")) {
                // TODO offset?
            } else if (file.getKey().endsWith(".msk")) {
                // TODO mask?
            } else if (file.getKey().endsWith(".sha")) {
                // TODO shadow?
            }
        }

        return nodes;
    }
}
