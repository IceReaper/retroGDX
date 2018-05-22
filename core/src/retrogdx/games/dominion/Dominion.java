package retrogdx.games.dominion;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.dominion.nodes.BinNode;
import retrogdx.games.dominion.nodes.RdfNode;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;

public class Dominion extends GameNode {
    public Dominion() {
        super("Dominion: Storm over Gift 3");
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "DOMINION.EXE");
    }

    public AssetFileNode resolve(FileHandle file) {
        if (file.extension().equalsIgnoreCase("RDF")) {
            return new RdfNode(file, this);
        } else if (file.extension().equalsIgnoreCase("BIN")) {
            return new BinNode(file, this.getFileIgnoreCase(file.parent(), file.nameWithoutExtension() + ".000"), this);
        } else if (file.extension().equalsIgnoreCase("000")) {
            return null;
        }

        return super.resolve(file);
    }
}
