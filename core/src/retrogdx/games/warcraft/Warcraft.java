package retrogdx.games.warcraft;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.warcraft.nodes.WarNode;
import retrogdx.ui.Game;
import retrogdx.ui.GamesTree;

public class Warcraft extends Game {
    public Warcraft(Table previewArea) {
        super(previewArea, "Warcraft: Orcs & Humans");
    }

    protected Warcraft(Table previewArea, String game) {
        super(previewArea, game);
    }

    public boolean verify(FileHandle folder) {
        return this.verify(folder, "WAR.EXE");
    }

    protected Array<Node> populate() {
        Array<Node> files = new Array<>();

        // TODO case insensitive
        for (FileHandle file : this.folder.child("DATA").list()) {
            if (file.extension().equalsIgnoreCase("WAR")) {
                files.add(new WarNode(this.previewArea, file));
            }
        }

        return GamesTree.sort(files);
    }
}
