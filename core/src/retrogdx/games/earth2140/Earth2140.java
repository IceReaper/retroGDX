package retrogdx.games.earth2140;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;
import retrogdx.games.earth2140.nodes.WdNode;
import retrogdx.ui.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Earth2140 extends Game {
    private FileHandle folder;

    public Earth2140(Table previewArea) {
        super(previewArea, "Earth 2140");
    }

    public boolean parse(FileHandle folder) {
        for (FileHandle file : folder.list()) {
            if (file.name().equalsIgnoreCase("E2140.EXE")) {
                this.folder = folder;
                return true;
            }
        }

        return false;
    }

    protected Array<Node> populate() {
        Map<String, Node> files = new HashMap<>();

        for (FileHandle file : this.folder.list()) {
            if (file.extension().equalsIgnoreCase("WD")) {
                files.put(file.name(), new WdNode(this.previewArea, file));
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
