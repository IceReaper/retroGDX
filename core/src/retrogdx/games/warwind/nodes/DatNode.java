package retrogdx.games.warwind.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;
import retrogdx.games.warwind.readers.Dat;
import retrogdx.games.warwind.readers.Res;
import retrogdx.generic.nodes.RiffWaveNode;
import retrogdx.ui.AssetFolderNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map.Entry;

public class DatNode extends AssetFolderNode {
    private FileHandle file;

    public DatNode(Table previewArea, FileHandle file) {
        super(previewArea, file.name());
        this.file = file;
    }

    protected Array<Tree.Node> populate() {
        Dat dat = new Dat(SmartByteBuffer.wrap(this.file.readBytes()));
        Array<Tree.Node> nodes = new Array<>();

        for (Entry<String, SmartByteBuffer> file : dat.getFiles().entrySet()) {
            nodes.add(new LvlNode(this.previewArea, file.getKey(), file.getValue()));
        }

        return nodes;
    }

    protected void showPreview() {
        this.previewArea.add(new VisLabel("Preview..."));
    }
}
