package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import retrogdx.generic.nodes.AutodeskFlicNode;
import retrogdx.generic.nodes.PlainTextNode;
import retrogdx.games.earth2140.readers.Wd;
import retrogdx.ui.AssetFolderNode;
import retrogdx.utils.SmartByteBuffer;
import retrogdx.utils.SliceInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;

public class WdNode extends AssetFolderNode {
    private FileHandle file;
    private Map<String, SliceInfo> palettes = new HashMap<>();

    public WdNode(Table previewArea, FileHandle file) {
        super(previewArea, file.name());
        this.file = file;
    }

    protected Array<Node> populate() {
        Wd wd = new Wd(SmartByteBuffer.wrap(this.file.readBytes()));
        Array<Node> nodes = new Array<>();

        for (Entry<String, SliceInfo> file : wd.getFiles().entrySet()) {
            if (file.getKey().endsWith(".TXT")) {
                nodes.add(new PlainTextNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".INI")) {
                nodes.add(new PlainTextNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".FLC")) {
                nodes.add(new AutodeskFlicNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".SMP")) {
                nodes.add(new SmpNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".PAL")) {
                this.palettes.put(file.getKey(), file.getValue());
            } else if (file.getKey().endsWith(".DAT")) {
                if (file.getKey().startsWith("DATA/LEVEL")) {
                    // TODO map
                } else {
                    nodes.add(new DatImageNode(this.previewArea, file.getKey(), file.getValue(), this.palettes));
                }
            } else if (file.getKey().endsWith(".KER")) {
                // TODO font metadata
            } else if (file.getKey().endsWith(".PCX")) {
                // TODO font image
            } else if (file.getKey().endsWith(".MIX")) {
                if (file.getKey().startsWith("MIXMAX")) {
                    // TODO UNK what is this ?!
                } else {
                    nodes.add(new MixNode(this.previewArea, file.getKey(), file.getValue()));
                }
            } else {
                System.out.println(file.getKey());
            }
        }

        return nodes;
    }

    protected void showPreview() {
    }
}
