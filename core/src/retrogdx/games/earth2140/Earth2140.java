package retrogdx.games.earth2140;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.earth2140.nodes.WdNode;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;

public class Earth2140 extends GameNode {
    public Earth2140() {
        super("Earth 2140");
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "E2140.EXE");
    }

    public AssetFileNode resolve(FileHandle file) {
        if (file.extension().equalsIgnoreCase("WD")) {
            return new WdNode(file, this);
        }

        return super.resolve(file);
    }
}
