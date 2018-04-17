package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.dune2.readers.Pak;
import retrogdx.generic.nodes.CreativeVocNode;
import retrogdx.generic.nodes.PlainTextNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.ui.nodes.AssetUnsupportedNode;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PakNode extends FolderVirtualNode {
    private Map<String, SmartByteBuffer> palettes = new HashMap<>();
    private Map<String, SmartByteBuffer> animations = new HashMap<>();

    public PakNode(FileHandle file, GameNode gameNode) {
        super(file, gameNode);
    }

    protected Array<Node> populate() {
        Pak pak = new Pak(this.buffer);
        Array<Node> nodes = new Array<>();

        for (Entry<String, SmartByteBuffer> file : pak.getFiles().entrySet()) {
            if (file.getKey().endsWith(".INI")) {
                nodes.add(new PlainTextNode(file.getKey(), file.getValue(), "CP850"));
            } else if (file.getKey().endsWith(".SHP")) {
                nodes.add(new ShpNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".WSA")) {
                nodes.add(new WsaNode(file.getKey(), file.getValue(), this.palettes, this.animations));
                this.animations.put(file.getKey(), file.getValue());
            } else if (file.getKey().endsWith(".VOC")) {
                nodes.add(new CreativeVocNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".PAL")) {
                nodes.add(new PalNode(file.getKey(), file.getValue()));
                this.palettes.put(file.getKey(), file.getValue());
            } else if (file.getKey().endsWith(".ICN")) {
                nodes.add(new IcnNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".MAP")) {
                // TODO Use in conjuction with ICN?
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".FNT")) {
                // TODO font
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".CPS")) {
                nodes.add(new CpsNode(file.getKey(), file.getValue()));

            } else if (file.getKey().endsWith(".ADL")) {
                // TODO music
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".C55")) {
                // TODO music
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".XMI")) {
                // TODO music
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".PCS")) {
                // TODO music
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".TAN")) {
                // TODO music
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".DRV")) {
                // TODO UNK - pc speaker related?
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".ADV")) {
                // TODO UNK - Soundcard driver?
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));

            } else if (file.getKey().endsWith(".TBL")) {
                nodes.add(new TblNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".EMC")) {
                // TODO scripts
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));

            } else if (file.getKey().endsWith(".ENG") || file.getKey().endsWith(".FRE") || file.getKey().endsWith(".GER")) {
                // TODO strings (compressed)
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else {
                System.out.println("Unknown file format: " + file.getKey());
            }
        }

        return nodes;
    }
}
