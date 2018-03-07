package retrogdx.games.sow;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.sow.nodes.DataNode;
import retrogdx.ui.Game;
import retrogdx.ui.GamesTree;

public class StateOfWar extends Game {
    public StateOfWar(Table previewArea) {
        super(previewArea, "State of War");
    }

    protected StateOfWar(Table previewArea, String game) {
        super(previewArea, game);
    }

    public boolean verify(FileHandle folder) {
        return this.verify(folder, "STATE OF WAR.EXE");
    }

    protected Array<Node> populate() {
        Array<Node> files = new Array<>();

        for (FileHandle file : this.folder.list()) {
            if (file.extension().equalsIgnoreCase("DATA")) {
                // TODO case insensitive .info
                files.add(new DataNode(this.previewArea, file, file.sibling(file.nameWithoutExtension() + ".info")));
            }
        }

        return GamesTree.sort(files);
    }
}
