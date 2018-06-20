package retrogdx.games.swarmassault.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.swarmassault.readers.Ddf;
import retrogdx.games.swarmassault.readers.Palette;
import retrogdx.games.swarmassault.readers.Sdf;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map.Entry;

public class DdfNode extends FolderVirtualNode {
    public DdfNode(FileHandle file, GameNode gameNode) {
        super(file, gameNode);
    }

    public DdfNode(String name, SmartByteBuffer buffer, GameNode gameNode) {
        super(name, buffer, gameNode);
    }

    protected Array<Tree.Node> populate() {
        Ddf ddf = new Ddf(this.buffer);

        Array<Tree.Node> nodes = new Array<>();

        int[] palette = new int[0];

        for (Entry<String, SmartByteBuffer> file: ddf.getFiles().entrySet()) {
            if (file.getKey().endsWith(".pal")) {
                palette = new Palette(file.getValue()).colors;
            } else {
                nodes.add(new ImageNode(file.getKey(), file.getValue(), palette));
            }
        }

        return nodes;
    }
}
