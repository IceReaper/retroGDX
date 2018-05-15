package retrogdx.games.futurecop;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.futurecop.nodes.ContainerNode;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;

public class FutureCop extends GameNode {
    public FutureCop() {
        super("Future Cop: LAPD");
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "FCOPLAPD.EXE");
    }

    public AssetFileNode resolve(FileHandle file) {
        if (file.extension().equalsIgnoreCase("")) {
            return new ContainerNode(file, this);
        }

        return super.resolve(file);
    }
}
