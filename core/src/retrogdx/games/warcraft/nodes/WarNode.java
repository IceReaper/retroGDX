package retrogdx.games.warcraft.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.warcraft.readers.Pal;
import retrogdx.games.warcraft.readers.War;
import retrogdx.generic.nodes.AutodeskFlicNode;
import retrogdx.generic.nodes.CreativeVocNode;
import retrogdx.generic.nodes.PlainTextNode;
import retrogdx.generic.nodes.RiffWaveNode;
import retrogdx.ui.nodes.AssetUnsupportedNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WarNode extends FolderVirtualNode {
    private Map<String, Pal> palettes = new HashMap<>();

    public WarNode(FileHandle file, GameNode gameNode) {
        super(file, gameNode);
    }

    protected Array<Node> populate() {
        War war = new War(this.buffer);

        Array<Node> nodes = new Array<>();
        Map<String, SmartByteBuffer> files = war.getFiles();

        for (Entry<String, SmartByteBuffer> file : files.entrySet()) {
            if (file.getKey().endsWith(".WAV")) {
                nodes.add(new RiffWaveNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".VOC")) {
                nodes.add(new CreativeVocNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".XMI")) {
                // TODO music
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));

            } else if (file.getKey().endsWith(".PAL")) {
                nodes.add(new PalNode(file.getKey(), file.getValue()));
                this.palettes.put(file.getKey(), new Pal(file.getValue()));
            } else if (file.getKey().endsWith(".IMG")) {
                nodes.add(new ImgNode(file.getKey(), file.getValue(), this.palettes));
            } else if (file.getKey().endsWith(".CUR")) {
                nodes.add(new CurNode(file.getKey(), file.getValue(), this.palettes));
            } else if (file.getKey().endsWith(".SPR")) {
                nodes.add(new SprNode(file.getKey(), file.getValue(), this.palettes));
            } else if (file.getKey().endsWith(".FLC")) {
                nodes.add(new AutodeskFlicNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".TILE")) {
                int id = Integer.parseInt(file.getKey().replace(".TILE", ""));
                nodes.add(new TileNode(file.getKey(), file.getValue(), files.get((id + 1) + ".TILEPART"), files.get((id + 2) + ".PAL")));

            } else if (file.getKey().endsWith(".TXT")) {
                nodes.add(new PlainTextNode(file.getKey(), file.getValue(), "CP850"));
            } else if (file.getKey().endsWith(".MAP")) {
                // TODO map preview
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".GUI")) {
                // TODO gui setups
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else {
                System.out.println("Unknown file format: " + file.getKey());
            }
        }

        return nodes;
    }
}
