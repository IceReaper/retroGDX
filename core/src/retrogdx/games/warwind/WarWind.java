package retrogdx.games.warwind;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.warwind.nodes.DatNode;
import retrogdx.games.warwind.nodes.ResNode;
import retrogdx.ui.Game;
import retrogdx.ui.GamesTree;

public class WarWind extends Game {
    public WarWind(Table previewArea) {
        super(previewArea, "War Wind");
    }

    protected WarWind(Table previewArea, String game) {
        super(previewArea, game);
    }

    public boolean verify(FileHandle folder) {
        return this.verify(folder, "WW.EXE");
    }

    protected Array<Node> populate() {
        Array<Node> files = new Array<>();

        for (FileHandle file : this.folder.child("DATA").list()) {
            // TODO .000 includes strings only, but breaks all the further reading.
            if (file.name().toUpperCase().startsWith("RES.") && !file.extension().equals("000")) {
                files.add(new ResNode(this.previewArea, file));
            } else if (file.name().equalsIgnoreCase("LEVELS.DAT")) {
                files.add(new DatNode(this.previewArea, file));
            }
        }

        return GamesTree.sort(files);
    }
}
