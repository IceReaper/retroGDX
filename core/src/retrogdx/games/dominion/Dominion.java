package retrogdx.games.dominion;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.dominion.nodes.RdfNode;
import retrogdx.games.dominion.nodes.ZeroNode;
import retrogdx.ui.Game;
import retrogdx.ui.GamesTree;

public class Dominion extends Game {
    public Dominion(Table previewArea) {
        super(previewArea, "Dominion: Storm over Gift 3");
    }

    protected Dominion(Table previewArea, String game) {
        super(previewArea, game);
    }

    public boolean verify(FileHandle folder) {
        return this.verify(folder, "DOMINION.EXE");
    }

    protected Array<Node> populate() {
        Array<Node> files = new Array<>();

        for (FileHandle file : this.folder.list()) {
            if (file.name().equalsIgnoreCase("AUDIO.RDF")) {
                files.add(new RdfNode(this.previewArea, file));
            } else if (file.extension().equals("000")) {
                // TODO case insensitive .BIN
                files.add(new ZeroNode(this.previewArea, file, file.sibling(file.nameWithoutExtension() + ".BIN")));
            }
        }

        return GamesTree.sort(files);
    }
}
