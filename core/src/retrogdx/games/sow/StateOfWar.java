package retrogdx.games.sow;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;
import retrogdx.ui.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class StateOfWar extends Game {
    private FileHandle folder;

    public StateOfWar(Table previewArea) {
        super(previewArea, "State of War");
    }

    protected StateOfWar(Table previewArea, String game) {
        super(previewArea, game);
    }

    public boolean parse(FileHandle folder) {
        for (FileHandle file : folder.list()) {
            if (file.name().equalsIgnoreCase("State of War.exe")) {
                this.folder = folder;
                return true;
            }
        }

        return false;
    }

    protected Array<Node> populate() {
        Map<String, Node> files = new HashMap<>();

        for (FileHandle file : this.folder.list()) {
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
