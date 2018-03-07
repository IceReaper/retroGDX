package retrogdx.games.earth2140;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.earth2140.nodes.WdNode;
import retrogdx.ui.Game;
import retrogdx.ui.GamesTree;

public class Earth2140 extends Game {
    public Earth2140(Table previewArea) {
        super(previewArea, "Earth 2140");
    }

    public boolean verify(FileHandle folder) {
        return this.verify(folder, "E2140.EXE");
    }

    protected Array<Node> populate() {
        Array<Node> files = new Array<>();

        for (FileHandle file : this.folder.list()) {
            if (file.extension().equalsIgnoreCase("WD")) {
                files.add(new WdNode(this.previewArea, file));
            }
        }

        return GamesTree.sort(files);
    }
}
