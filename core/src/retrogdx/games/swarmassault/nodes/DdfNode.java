package retrogdx.games.swarmassault.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.games.swarmassault.readers.Ani;
import retrogdx.games.swarmassault.readers.Ddf;
import retrogdx.games.swarmassault.readers.Palette;
import retrogdx.ui.nodes.FolderVirtualNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DdfNode extends FolderVirtualNode {
    private FileHandle aniFile;

    public DdfNode(FileHandle file, FileHandle aniFile, GameNode gameNode) {
        super(file, gameNode);
        this.aniFile = aniFile;
    }

    protected Array<Tree.Node> populate() {
        Ddf ddf = new Ddf(this.buffer);
        Ani ani = new Ani(SmartByteBuffer.wrap(this.aniFile.readBytes()));

        Array<Tree.Node> nodes = new Array<>();

        Map<String, SmartByteBuffer> ddfFiles = ddf.getFiles();

        int[] palette = new Palette(ddfFiles.get("palette.pal")).colors;

        List<String> usedFrames = new ArrayList<>();

        for (Entry<String, String[]> entry: ani.getAnimations().entrySet()) {
            List<SmartByteBuffer> frames = new ArrayList<>();

            for (String frame : entry.getValue()) {
                frames.add(ddfFiles.get(frame));
                usedFrames.add(frame);
            }

            nodes.add(new AniNode(entry.getKey(), frames.toArray(new SmartByteBuffer[0]), palette));
        }

        for (Entry<String, SmartByteBuffer> file: ddf.getFiles().entrySet()) {
            if (!file.getKey().equals("palette.pal") && !usedFrames.contains(file.getKey())) {
                nodes.add(new ImageNode(file.getKey(), file.getValue(), palette));
            }
        }

        return nodes;
    }
}
