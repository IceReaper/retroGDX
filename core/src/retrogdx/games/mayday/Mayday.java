package retrogdx.games.mayday;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.mayday.nodes.ArtNode;
import retrogdx.ui.Game;
import retrogdx.ui.GamesTree;

public class Mayday extends Game {
    public Mayday(Table previewArea) {
        super(previewArea, "Dominion: Conflict Earth");
    }

    protected Mayday(Table previewArea, String game) {
        super(previewArea, game);
    }

    public boolean verify(FileHandle folder) {
        return this.verify(folder, "MAYDAY.EXE");
    }

    protected Array<Node> populate() {
        Array<Node> files = new Array<>();

        for (FileHandle file : this.folder.list()) {
            if (file.name().equalsIgnoreCase("MAYDAY.ART")) {
                files.add(new ArtNode(this.previewArea, file));
            }
        }

        return GamesTree.sort(files);
    }
}
