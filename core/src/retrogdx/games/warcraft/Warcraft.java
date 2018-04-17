package retrogdx.games.warcraft;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.warcraft.nodes.WarNode;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.ui.GamesTree;

public class Warcraft extends GameNode {
    public Warcraft() {
        super("Warcraft: Orcs & Humans");
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "WAR.EXE");
    }

    public AssetFileNode resolve(FileHandle file) {
        if (file.extension().equalsIgnoreCase("WAR")) {
            return new WarNode(file, this);
        }

        return super.resolve(file);
    }
}
