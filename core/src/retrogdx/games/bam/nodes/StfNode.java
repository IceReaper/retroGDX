package retrogdx.games.bam.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.bam.readers.Stf;
import retrogdx.generic.nodes.PaletteNode;
import retrogdx.generic.nodes.RiffWaveNode;
import retrogdx.ui.AssetFolderNode;
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

        for (Entry<String, SmartByteBuffer> file : stf.getFiles().entrySet()) {
            // TODO merge other files from https://github.com/IceReaper/GameExtractor-Blood-Magic/tree/master/src/bam/formats
            if (file.getKey().endsWith(".wav")) {
                nodes.add(new RiffWaveNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".HMP")) {
                // TODO music
            } else if (file.getKey().endsWith(".PAL")) {
                nodes.add(new PaletteNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".ANI")) {
                nodes.add(new AniNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".MIF")) {
            } else if (file.getKey().endsWith(".TLB")) {
            } else if (file.getKey().endsWith(".BNK")) {
            } else if (file.getKey().endsWith(".SQB")) {
                nodes.add(new SqbNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".FNT")) {
            }
        }

        return nodes;
    }

    protected void showPreview() {
    }
}
