package retrogdx.games.warwind.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.warwind.readers.Res;
import retrogdx.generic.nodes.RiffWaveNode;
import retrogdx.ui.AssetFolderNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map.Entry;

public class ResNode extends AssetFolderNode {
    public ResNode(Table previewArea, FileHandle file) {
        super(previewArea, file);
    }

    protected Array<Tree.Node> populate() {
        Res res = new Res(SmartByteBuffer.wrap(this.file.readBytes()));

        Array<Tree.Node> nodes = new Array<>();

        for (Entry<String, SmartByteBuffer> file : res.getFiles().entrySet()) {
            if (file.getKey().endsWith(".wav")) {
                nodes.add(new RiffWaveNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".D3GR")) {
                nodes.add(new D3grNode(this.previewArea, file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".PAL")) {
                nodes.add(new PalNode(this.previewArea, file.getKey(), file.getValue()));
            }
        }

        return nodes;
    }
}
