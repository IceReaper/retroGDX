package retrogdx.games.warwind;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;
import retrogdx.games.warwind.nodes.DatNode;
import retrogdx.games.warwind.nodes.ResNode;
import retrogdx.ui.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class WarWind extends Game {
    protected FileHandle folder;

    public WarWind(Table previewArea) {
        super(previewArea, "War Wind");
    }

    protected WarWind(Table previewArea, String game) {
        super(previewArea, game);
    }

    public boolean verify(FileHandle folder) {
        for (FileHandle file : folder.list()) {
            if (file.name().equalsIgnoreCase("WW.EXE")) {
                this.folder = folder;
                return true;
            }
        }

        return false;
    }

    protected Array<Node> populate() {
        Map<String, Node> files = new HashMap<>();

        for (FileHandle file : this.folder.child("DATA").list()) {
            // TODO .000 includes strings only, but breaks all the further reading.
            if (file.name().toUpperCase().startsWith("RES.") && !file.extension().equals("000")) {
                files.put(file.name(), new ResNode(this.previewArea, file));
            } else if (file.name().equalsIgnoreCase("LEVELS.DAT")) {
                files.put(file.name(), new DatNode(this.previewArea, file));
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
