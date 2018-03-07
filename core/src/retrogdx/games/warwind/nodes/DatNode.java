package retrogdx.games.warwind.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.warwind.readers.Dat;
import retrogdx.ui.AssetFolderNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map.Entry;

public class DatNode extends AssetFolderNode {
    public DatNode(Table previewArea, FileHandle file) {
        super(previewArea, file);
    }

    protected Array<Tree.Node> populate() {
        Dat dat = new Dat(SmartByteBuffer.wrap(this.file.readBytes()));
        Array<Tree.Node> nodes = new Array<>();

        for (Entry<String, SmartByteBuffer> file : dat.getFiles().entrySet()) {
            nodes.add(new LvlNode(this.previewArea, file.getKey(), file.getValue()));
        }

        return nodes;
    }
}
