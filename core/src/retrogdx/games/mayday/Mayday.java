package retrogdx.games.mayday;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.mayday.nodes.ArtNode;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;

public class Mayday extends GameNode {
    public Mayday() {
        super("Mayday: Conflict Earth");
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "MAYDAY.EXE");
    }

    public AssetFileNode resolve(FileHandle file) {
        if (file.extension().equalsIgnoreCase("ART")) {
            return new ArtNode(file, this);
        }

        return super.resolve(file);
    }
}
