package retrogdx.games.dominion.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.dominion.readers.Zero;
import retrogdx.ui.AssetFolderNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class ZeroNode extends AssetFolderNode {
    private FileHandle dataFile;
    private FileHandle indexFile;

    public ZeroNode(Table previewArea, FileHandle dataFile, FileHandle indexFile) {
        super(previewArea, dataFile);

        this.dataFile = dataFile;
        this.indexFile = indexFile;
    }

    protected Array<Tree.Node> populate() {
        Zero zero = new Zero(SmartByteBuffer.wrap(this.file.readBytes()));

        Array<Tree.Node> nodes = new Array<>();

        for (Map.Entry<String, SmartByteBuffer> file : zero.getFiles().entrySet()) {
            System.out.println(file.getKey());
        }

        return nodes;
    }
}
