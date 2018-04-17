package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.earth2140.readers.Wd;
import retrogdx.generic.nodes.AutodeskFlicNode;
import retrogdx.generic.nodes.PlainTextNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.ui.nodes.AssetUnsupportedNode;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WdNode extends FolderVirtualNode {
    private Map<String, SmartByteBuffer> palettes = new HashMap<>();

    public WdNode(FileHandle file, GameNode gameNode) {
        super(file, gameNode);
    }

    protected Array<Tree.Node> populate() {
        Wd wd = new Wd(this.buffer);

        Array<Tree.Node> nodes = new Array<>();

        for (Entry<String, SmartByteBuffer> file : wd.getFiles().entrySet()) {
            if (file.getKey().endsWith(".TXT")) {
                nodes.add(new PlainTextNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".INI")) {
                nodes.add(new PlainTextNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".FLC")) {
                nodes.add(new AutodeskFlicNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".SMP")) {
                nodes.add(new SmpNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".PAL")) {
                this.palettes.put(file.getKey(), file.getValue());
            } else if (file.getKey().endsWith(".DAT")) {
                if (file.getKey().startsWith("DATA/LEVEL")) {
                    nodes.add(new DatMapNode(file.getKey(), file.getValue()));
                } else {
                    nodes.add(new DatImageNode(file.getKey(), file.getValue(), this.palettes));
                }
            } else if (file.getKey().endsWith(".KER")) {
                // TODO font metadata
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".PCX")) {
                // TODO font image
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".MIX")) {
                if (file.getKey().startsWith("MIXMAX")) {
                    // TODO UNK what is this ?!
                    nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
                } else {
                    nodes.add(new MixNode(file.getKey(), file.getValue()));
                }
            } else {
                System.out.println("Unknown file format: " + file.getKey());
            }
        }

        return nodes;
    }
}
