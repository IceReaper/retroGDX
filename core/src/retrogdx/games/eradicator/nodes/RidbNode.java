package retrogdx.games.eradicator.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.eradicator.readers.Ridb;
import retrogdx.ui.nodes.AssetUnsupportedNode;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map.Entry;

public class RidbNode extends FolderVirtualNode {
    public RidbNode(FileHandle file, GameNode gameNode) {
        super(file, gameNode);
    }

    public RidbNode(String name, SmartByteBuffer buffer, GameNode gameNode) {
        super(name, buffer, gameNode);
    }

    protected Array<Tree.Node> populate() {
        Ridb ridb = new Ridb(this.buffer);

        Array<Tree.Node> nodes = new Array<>();

        for (Entry<String, SmartByteBuffer> file: ridb.getFiles().entrySet()) {
            if (this.name.equals("SOUNDS")) {
                nodes.add(new SoundNode(file.getKey(), file.getValue()));
            } else if (this.name.equals("TEXTURES") ||this.name.equals("SKIES")) {
                nodes.add(new TextureNode(file.getKey(), file.getValue()));
            } else if (this.name.equals("PICTURES")) {
                nodes.add(new PictureNode(file.getKey(), file.getValue()));
            } else if (this.name.equals("SPR_ACTIONS")) {
                nodes.add(new SpriteNode(file.getKey(), file.getValue()));
            } else if (file.getValue().readString(4).equals("RIDB")) {
                nodes.add(new RidbNode(file.getKey(), file.getValue(), this.gameNode));
            } else {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            }
        }

        return nodes;
    }
}
