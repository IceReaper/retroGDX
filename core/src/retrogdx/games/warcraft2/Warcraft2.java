package retrogdx.games.warcraft2;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.warcraft2.nodes.MpqNode;
import retrogdx.ui.Game;
import retrogdx.ui.GamesTree;

public class Warcraft2 extends Game {
    public Warcraft2(Table previewArea) {
        super(previewArea, "Warcraft II: Battle.net Edition");
    }

    protected Warcraft2(Table previewArea, String game) {
        super(previewArea, game);
    }

    public boolean verify(FileHandle folder) {
        return this.verify(folder, "WARCRAFT II BNE.EXE");
    }

    protected Array<Node> populate() {
        Array<Node> files = new Array<>();

        for (FileHandle file : this.folder.list()) {
            if (file.extension().equalsIgnoreCase("MPQ")) {
                files.add(new MpqNode(this.previewArea, file));
            }
        }

        return GamesTree.sort(files);
    }
}
