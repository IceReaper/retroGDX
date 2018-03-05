package retrogdx.games.soww;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.VisLabel;
import retrogdx.games.sow.StateOfWar;

public class StateOfWarWarmonger extends StateOfWar {
    private FileHandle folder;

    public StateOfWarWarmonger(Table previewArea) {
        super(previewArea, "State of War: Warmonger");
    }

    public boolean verify(FileHandle folder) {
        if (folder.child("Warmonger.exe").exists()) {
            this.folder = folder;
            return true;
        }

        return false;
    }

    protected void showPreview() {
        this.previewArea.add(new VisLabel("Preview..."));
    }
}
