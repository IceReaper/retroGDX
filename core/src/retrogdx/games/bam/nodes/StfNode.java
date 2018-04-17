package retrogdx.games.bam.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.bam.readers.Stf;
import retrogdx.generic.nodes.RiffWaveNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.ui.nodes.AssetUnsupportedNode;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map.Entry;

public class StfNode extends FolderVirtualNode {
    public StfNode(FileHandle file, GameNode gameNode) {
        super(file, gameNode);
    }

    protected Array<Tree.Node> populate() {
        Stf stf = new Stf(this.buffer);

        Array<Tree.Node> nodes = new Array<>();

        for (Entry<String, SmartByteBuffer> file : stf.getFiles().entrySet()) {
            if (file.getKey().endsWith(".WAV")) {
                nodes.add(new RiffWaveNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".HMP")) {
                // TODO music
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".PAL")) {
                nodes.add(new PalNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".ANI")) {
                nodes.add(new AniNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".MIF")) {
                // TODO map
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".TLB")) {
                nodes.add(new TlbNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".BNK")) {
                // TODO soundbank
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".SQB")) {
                nodes.add(new SqbNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".FNT")) {
                // TODO font
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else {
                System.out.println("Unknown file format: " + file.getKey());
            }
        }

        return nodes;
    }
}
