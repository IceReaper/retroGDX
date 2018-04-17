package retrogdx.games.dominion.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.dominion.readers.Zero;
import retrogdx.ui.nodes.GameNode;
import retrogdx.ui.nodes.AssetUnsupportedNode;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class BinNode extends FolderVirtualNode {
    private FileHandle imagesFile;

    public BinNode(FileHandle file, FileHandle imagesFile, GameNode gameNode) {
        super(file, gameNode);

        this.imagesFile = imagesFile;
    }

    protected Array<Tree.Node> populate() {
        Zero zero = new Zero(this.buffer, SmartByteBuffer.wrap(this.imagesFile.readBytes()));

        Array<Tree.Node> nodes = new Array<>();

        for (Map.Entry<String, SmartByteBuffer> file : zero.getFiles().entrySet()) {
            if (file.getKey().endsWith(".spr")) {
                nodes.add(new SprNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".UNK")) {
                // TODO find out what this stuff is.
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else {
                System.out.println("Unknown file format: " + file.getKey());
            }
        }

        return nodes;
    }
}
