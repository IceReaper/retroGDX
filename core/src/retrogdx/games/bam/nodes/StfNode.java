package retrogdx.games.bam.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.bam.readers.Stf;
import retrogdx.generic.nodes.PaletteNode;
import retrogdx.generic.nodes.PlainTextNode;
import retrogdx.ui.AssetFolderNode;
import retrogdx.utils.SliceInfo;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map.Entry;

import static com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;

public class StfNode extends AssetFolderNode {
    private FileHandle file;

    public StfNode(Table previewArea, FileHandle file) {
        super(previewArea, file.name());
        this.file = file;
    }

    protected Array<Node> populate() {
        Stf stf = new Stf(SmartByteBuffer.wrap(this.file.readBytes()));
        Array<Node> nodes = new Array<>();

        for (Entry<String, SliceInfo> file : stf.getFiles().entrySet()) {
            // TODO merge other files from https://github.com/IceReaper/GameExtractor-Blood-Magic/tree/master/src/bam/formats
            if (file.getKey().endsWith(".SND")) {
            } else if (file.getKey().endsWith(".MSX")) {
            } else if (file.getKey().endsWith(".PAL")) {
                nodes.add(new PaletteNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".ANI")) {
            } else if (file.getKey().endsWith(".MAP")) {
            } else if (file.getKey().endsWith(".TIL")) {
            } else if (file.getKey().endsWith(".BNK")) {
            } else if (file.getKey().endsWith(".SQB")) {
            } else if (file.getKey().endsWith(".FNT")) {
            }
        }

        return nodes;
    }

    protected void showPreview() {
    }
}
