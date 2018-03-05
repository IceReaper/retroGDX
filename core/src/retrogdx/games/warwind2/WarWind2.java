package retrogdx.games.warwind2;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.VisLabel;
import retrogdx.games.warwind.WarWind;

public class WarWind2 extends WarWind {
    public WarWind2(Table previewArea) {
        super(previewArea, "War Wind 2");
    }

    public boolean verify(FileHandle folder) {
        for (FileHandle file : folder.list()) {
            if (file.name().equalsIgnoreCase("WARWIND2.EXE")) {
                this.folder = folder;
                return true;
            }
        }

        return false;
    }

    protected void showPreview() {
        this.previewArea.add(new VisLabel("Preview..."));
    }
}
