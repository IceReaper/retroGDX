package retrogdx.games.dune2;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.dune2.nodes.PakNode;
import retrogdx.games.dune2.readers.Pak;
import retrogdx.games.dune2.readers.Pal;
import retrogdx.ui.Game;
import retrogdx.ui.GamesTree;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class Dune2 extends Game {
    public static int[] PALETTE;
    public static int[] PALETTE_ALT;

    public Dune2(Table previewArea) {
        super(previewArea, "Dune II: The Building of a Dynasty");
    }

    public boolean verify(FileHandle folder) {
        if (!this.verify(folder, "DUNE2.EXE")) {
            return false;
        }

        for (FileHandle file : folder.list()) {
            if (!file.name().equalsIgnoreCase("DUNE.PAK")) {
                continue;
            }

            Pak pak = new Pak(SmartByteBuffer.wrap(file.readBytes()));

            for (Map.Entry<String, SmartByteBuffer> entry : pak.getFiles().entrySet()) {
                if (entry.getKey().equalsIgnoreCase("IBM.PAL")) {
                    Dune2.PALETTE = new Pal(entry.getValue()).colors;
                } else if (entry.getKey().equalsIgnoreCase("BENE.PAL")) {
                    Dune2.PALETTE_ALT = new Pal(entry.getValue()).colors;
                }
            }
        }

        return true;
    }

    protected Array<Node> populate() {
        Array<Node> files = new Array<>();

        for (FileHandle file : this.folder.list()) {
            if (file.extension().equalsIgnoreCase("PAK")) {
                files.add(new PakNode(this.previewArea, file));
            }
        }

        return GamesTree.sort(files);
    }
}
