package retrogdx.games.mayday.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.mayday.readers.Art;
import retrogdx.ui.AssetFolderNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class ArtNode extends AssetFolderNode {
    public ArtNode(Table previewArea, FileHandle file) {
        super(previewArea, file);
    }

    protected Array<Tree.Node> populate() {
        Art art = new Art(SmartByteBuffer.wrap(this.file.readBytes()));

        Array<Tree.Node> nodes = new Array<>();

        for (Map.Entry<String, SmartByteBuffer> file : art.getFiles().entrySet()) {
            System.out.println(file.getKey());
        }

        return nodes;
    }
}
