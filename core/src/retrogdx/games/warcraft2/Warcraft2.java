package retrogdx.games.warcraft2;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.warcraft2.nodes.MpqNode;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;

public class Warcraft2 extends GameNode {
    public Warcraft2() {
        super("Warcraft II: Battle.net Edition");
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "WARCRAFT II BNE.EXE");
    }

    public AssetFileNode resolve(FileHandle file) {
        if (file.extension().equalsIgnoreCase("MPQ")) {
            return new MpqNode(file, this);
        }

        return super.resolve(file);
    }
}
