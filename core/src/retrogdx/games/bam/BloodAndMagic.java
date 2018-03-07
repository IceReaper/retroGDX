package retrogdx.games.bam;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.bam.nodes.StfNode;
import retrogdx.ui.Game;
import retrogdx.ui.GamesTree;

public class BloodAndMagic extends Game {
    public BloodAndMagic(Table previewArea) {
        super(previewArea, "Blood & Magic");
    }

    public boolean verify(FileHandle folder) {
        return this.verify(folder, "BAMMAIN.EXE");
    }

    protected Array<Node> populate() {
        Array<Node> files = new Array<>();

        for (FileHandle file : this.folder.list()) {
            if (file.extension().equalsIgnoreCase("STF")) {
                files.add(new StfNode(this.previewArea, file));
            }
        }

        return GamesTree.sort(files);
    }
}
