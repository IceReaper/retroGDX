package retrogdx.games.warwind.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.warwind.WarWind;
import retrogdx.games.warwind.readers.Pal;
import retrogdx.games.warwind.readers.Res;
import retrogdx.generic.nodes.RiffWaveNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map.Entry;

public class ResNode extends FolderVirtualNode {
    public ResNode(FileHandle file, GameNode gameNode) {
        super(file, gameNode);
    }

    protected Array<Tree.Node> populate() {
        Res res = new Res(this.buffer);

        Array<Tree.Node> nodes = new Array<>();

        for (Entry<String, SmartByteBuffer> file : res.getFiles().entrySet()) {
            if (file.getKey().endsWith(".WAV")) {
                nodes.add(new RiffWaveNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".D3GR")) {
                nodes.add(new D3grNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".PAL")) {
                nodes.add(new PalNode(file.getKey(), file.getValue()));
                WarWind.PALETTES.put(file.getKey(), new Pal(file.getValue()));
            } else {
                System.out.println("Unknown file format: " + file.getKey());
            }
        }

        return nodes;
    }
}
