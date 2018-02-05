package retrogdx.games.dune2;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;
import retrogdx.ui.AssetNode;
import retrogdx.Game;

public class Dune2 extends AssetNode implements Game {
    private FileHandle folder;

    public Dune2(Table previewArea) {
        super(previewArea, "Dune II: The Building of a Dynasty");
    }

    public boolean parse(FileHandle folder) {
        if (folder.child("DUNE2.EXE").exists()) {
            this.folder = folder;
            return true;
        }

        return false;
    }

    protected Array<Node> populate() {
        Array<Node> files = new Array<>();

        for (FileHandle file : this.folder.list()) {
            if (file.extension().equals("PAK")) {
                files.add(new PakFile(this.previewArea, file));
            }
        }

        return files;
    }

    protected void showPreview() {
        this.previewArea.add(new VisLabel("Preview..."));
    }
}
