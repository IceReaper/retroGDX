package retrogdx.games.swarmassault;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.swarmassault.nodes.DdfNode;
import retrogdx.games.swarmassault.nodes.SdfNode;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;

public class SwarmAssault extends GameNode {
    public SwarmAssault() {
        super("Swarm Assault");
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "SWARM ASSAULT.EXE");
    }

    public AssetFileNode resolve(FileHandle file) {
        if (file.extension().equalsIgnoreCase("DDF")) {
            return new DdfNode(file, this.getFileIgnoreCase(file.parent(), file.nameWithoutExtension() + ".ANI"), this);
        } else if (file.extension().equalsIgnoreCase("SDF")) {
            return new SdfNode(file, this);
        }

        return super.resolve(file);
    }
}
