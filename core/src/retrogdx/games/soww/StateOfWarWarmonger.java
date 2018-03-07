package retrogdx.games.soww;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.sow.StateOfWar;

public class StateOfWarWarmonger extends StateOfWar {
    public StateOfWarWarmonger(Table previewArea) {
        super(previewArea, "State of War: Warmonger");
    }

    public boolean verify(FileHandle folder) {
        return this.verify(folder, "WARMONGER.EXE");
    }
}
