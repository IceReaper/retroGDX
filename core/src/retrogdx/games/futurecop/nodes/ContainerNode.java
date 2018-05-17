package retrogdx.games.futurecop.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.futurecop.readers.Bmp;
import retrogdx.games.futurecop.readers.Container;
import retrogdx.generic.nodes.RiffWaveNode;
import retrogdx.ui.PreviewArea;
import retrogdx.ui.nodes.AssetUnsupportedNode;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class ContainerNode extends FolderVirtualNode {
    private List<Bmp> textures = new ArrayList<>();

    public ContainerNode(FileHandle file, GameNode gameNode) {
        super(file, gameNode);
    }

    protected Array<Tree.Node> populate() {
        Container container = new Container(this.buffer);

        Array<Tree.Node> nodes = new Array<>();

        for (Entry<String, SmartByteBuffer> file : container.getFiles().entrySet()) {
            if (file.getKey().endsWith(".wav")) {
                nodes.add(new RiffWaveNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".snds")) {
                nodes.add(new SndsNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".msx")) {
                nodes.add(new MsxNode(file.getKey(), file.getValue()));

            } else if (file.getKey().endsWith(".bmp")) {
                nodes.add(new BmpNode(file.getKey(), file.getValue()));
                this.textures.add(new Bmp(file.getValue()));

            } else if (file.getKey().endsWith(".obj")) {
                nodes.add(new ObjNode(file.getKey(), file.getValue(), this.textures));

            } else if (file.getKey().endsWith(".tos")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".ptc")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".til")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".dcs")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".ctr")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".shd")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".fun")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".net")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".fnt")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".pyr")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".sfx")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".act")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".sac")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));
            } else if (file.getKey().endsWith(".canm")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));

            } else if (file.getKey().endsWith(".RPNS")) {
                nodes.add(new AssetUnsupportedNode(file.getKey(), file.getValue()));

            } else {
                System.out.println("Unknown file format: " + file.getKey());
            }
        }

        return nodes;
    }
}
