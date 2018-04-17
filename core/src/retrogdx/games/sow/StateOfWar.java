package retrogdx.games.sow;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.sow.nodes.DataNode;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;

public class StateOfWar extends GameNode {
    public StateOfWar() {
        super("State of War");
    }

    protected StateOfWar(String game) {
        super(game);
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "STATE OF WAR.EXE");
    }

    public AssetFileNode resolve(FileHandle file) {
        if (file.extension().equalsIgnoreCase("DATA")) {
            return new DataNode(file, file.sibling(file.nameWithoutExtension() + ".INFO"), this);
        } else if (file.extension().equalsIgnoreCase("INFO")) {
            return null;
        }

        return super.resolve(file);
    }
}
