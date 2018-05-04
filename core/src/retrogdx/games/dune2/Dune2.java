package retrogdx.games.dune2;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.games.dune2.nodes.PakNode;
import retrogdx.games.dune2.readers.Pak;
import retrogdx.games.dune2.readers.Pal;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class Dune2 extends GameNode {
    // TODO find a better way to implement this!
    public static int[] PALETTE;
    // TODO find a better way to implement this!
    public static int[] PALETTE_ALT;

    public Dune2() {
        super("Dune II: The Building of a Dynasty");
    }

    public AssetFileNode resolve(FileHandle file) {
        if (file.extension().equalsIgnoreCase("PAK")) {
            return new PakNode(file, this);
        }

        // TODO find a better way to implement this!
        if (file.name().equalsIgnoreCase("DUNE.PAK")) {
            Pak pak = new Pak(SmartByteBuffer.wrap(file.readBytes()));

            for (Map.Entry<String, SmartByteBuffer> entry : pak.getFiles().entrySet()) {
                if (entry.getKey().equalsIgnoreCase("IBM.PAL")) {
                    Dune2.PALETTE = new Pal(entry.getValue()).colors;
                } else if (entry.getKey().equalsIgnoreCase("BENE.PAL")) {
                    Dune2.PALETTE_ALT = new Pal(entry.getValue()).colors;
                }
            }
        }

        return super.resolve(file);
    }

    public boolean verifyExecutableExists(FileHandle folder) {
        return this.verifyExecutableExists(folder, "DUNE2.EXE");
    }
}
