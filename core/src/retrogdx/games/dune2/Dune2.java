package retrogdx.games.dune2;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;
import retrogdx.games.dune2.nodes.PakNode;
import retrogdx.games.dune2.readers.Pak;
import retrogdx.games.dune2.readers.Pal;
import retrogdx.ui.Game;
import retrogdx.utils.SmartByteBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Dune2 extends Game {
    public static int[] PALETTE;
    public static int[] PALETTE_ALT;
    private FileHandle folder;

    public Dune2(Table previewArea) {
        super(previewArea, "Dune II: The Building of a Dynasty");
    }

    public boolean parse(FileHandle folder) {
        for (FileHandle file : folder.list()) {
            if (file.name().equalsIgnoreCase("DUNE2.EXE")) {
                this.folder = folder;
            }
        }

        if (this.folder == null) {
            return false;
        }

        for (FileHandle file : folder.list()) {
            if (!file.name().equalsIgnoreCase("DUNE.PAK")) {
                continue;
            }

            Pak pak = new Pak(SmartByteBuffer.wrap(file.readBytes()));

            for (Map.Entry<String, SmartByteBuffer> entry : pak.getFiles().entrySet()) {
                if (entry.getKey().equalsIgnoreCase("IBM.PAL")) {
                    Dune2.PALETTE = new Pal(entry.getValue()).colors;
                } else if (entry.getKey().equalsIgnoreCase("BENE.PAL")) {
                    Dune2.PALETTE_ALT = new Pal(entry.getValue()).colors;
                }
            }
        }

        return true;
    }

    protected Array<Node> populate() {
        Map<String, Node> files = new HashMap<>();

        for (FileHandle file : this.folder.list()) {
            if (file.extension().equalsIgnoreCase("PAK")) {
                files.put(file.name(), new PakNode(this.previewArea, file));
            }
        }

        Map<String, Node> sorted = new TreeMap<>(files);
        Array<Node> result = new Array<>();

        for (String key : sorted.keySet()) {
            result.add(files.get(key));
        }

        return result;
    }

    protected void showPreview() {
        this.previewArea.add(new VisLabel("Preview..."));
    }
}
