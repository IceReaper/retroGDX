package retrogdx.games.soww;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.sow.StateOfWar;

public class StateOfWarWarmonger extends StateOfWar {
    public StateOfWarWarmonger() {
        super("State of War: Warmonger");
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "WARMONGER.EXE");
    }
}
