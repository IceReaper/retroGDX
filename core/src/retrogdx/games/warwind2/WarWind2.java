package retrogdx.games.warwind2;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.warwind.WarWind;

public class WarWind2 extends WarWind {
    public WarWind2(Table previewArea) {
        super(previewArea, "War Wind II: Human Onslaught");
    }

    public boolean verify(FileHandle folder) {
        return this.verify(folder, "WARWIND2.EXE");
    }
}
