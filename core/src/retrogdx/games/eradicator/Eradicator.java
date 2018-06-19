package retrogdx.games.eradicator;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.eradicator.nodes.RawNode;
import retrogdx.games.eradicator.nodes.RidbNode;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.utils.SmartByteBuffer;

public class Eradicator extends GameNode {
    public Eradicator() {
        super("Eradicator");
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "ERAD.EXE");
    }

    public AssetFileNode resolve(FileHandle file) {
        if (file.extension().equalsIgnoreCase("RID") || file.extension().equalsIgnoreCase("RIM")) {
            return new RidbNode(file, this);
        } else if (file.extension().equalsIgnoreCase("RAW")) {
                return new RawNode(file.name(), SmartByteBuffer.wrap(file.readBytes()));
        }

        return super.resolve(file);
    }
}
