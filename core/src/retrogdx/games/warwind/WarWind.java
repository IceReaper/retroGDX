package retrogdx.games.warwind;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.warwind.nodes.DatNode;
import retrogdx.games.warwind.nodes.ResNode;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;

public class WarWind extends GameNode {
    public WarWind() {
        super("War Wind");
    }

    protected WarWind(String game) {
        super(game);
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "WW.EXE");
    }

    public AssetFileNode resolve(FileHandle file) {
        if (file.name().toUpperCase().startsWith("RES.") && !file.extension().equals("000")) {
            return new ResNode(file, this);
        } else if (file.name().equalsIgnoreCase("LEVELS.DAT")) {
            return new DatNode(file, this);
        }

        return super.resolve(file);
    }
}
