package retrogdx.games.bam.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.bam.readers.Stf;
import retrogdx.generic.nodes.RiffWaveNode;
import retrogdx.ui.AssetFolderNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map.Entry;

import static com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;

public class StfNode extends AssetFolderNode {
    public StfNode(Table previewArea, FileHandle file) {
        super(previewArea, file);
    }

    protected Array<Node> populate() {
        Stf stf = new Stf(SmartByteBuffer.wrap(this.file.readBytes()));
        Array<Node> nodes = new Array<>();

        for (Entry<String, SmartByteBuffer> file : stf.getFiles().entrySet()) {
            if (file.getKey().endsWith(".WAV")) {
                nodes.add(new RiffWaveNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".HMP")) {
                // TODO music
            } else if (file.getKey().endsWith(".PAL")) {
                nodes.add(new PalNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".ANI")) {
                nodes.add(new AniNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".MIF")) {
                // TODO map
            } else if (file.getKey().endsWith(".TLB")) {
                nodes.add(new TlbNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".BNK")) {
                // TODO soundbank
            } else if (file.getKey().endsWith(".SQB")) {
                nodes.add(new SqbNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".FNT")) {
                // TODO font
            }
        }

        return nodes;
    }
}
