package retrogdx.games.warwind2;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.warwind.WarWind;

public class WarWind2 extends WarWind {
    public WarWind2() {
        super("War Wind II: Human Onslaught");
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "WARWIND2.EXE");
    }
}
