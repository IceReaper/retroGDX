package retrogdx.games.warcraft2.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.warcraft2.readers.Mpq;
import retrogdx.ui.AssetFolderNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class MpqNode extends AssetFolderNode {
    public MpqNode(Table previewArea, FileHandle file) {
        super(previewArea, file);
    }

    protected Array<Tree.Node> populate() {
        Mpq mpq = new Mpq(SmartByteBuffer.wrap(this.file.readBytes()));

        Array<Tree.Node> nodes = new Array<>();
        Map<String, SmartByteBuffer> files = mpq.getFiles();

        for (Map.Entry<String, SmartByteBuffer> file : files.entrySet()) {
            System.out.println(file.getKey());
        }

        return nodes;
    }
}
