package retrogdx.games.bam;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.bam.nodes.StfNode;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;

public class BloodAndMagic extends GameNode {
    public BloodAndMagic() {
        super("Blood & Magic");
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "BAMMAIN.EXE");
    }

    public AssetFileNode resolve(FileHandle file) {
        if (file.extension().equalsIgnoreCase("STF")) {
            return new StfNode(file, this);
        }

        return super.resolve(file);
    }
}
