package retrogdx.ui.nodes;

import com.badlogic.gdx.files.FileHandle;
import retrogdx.utils.SmartByteBuffer;

public abstract class FolderVirtualNode extends AssetFolderNode {
    protected SmartByteBuffer buffer;
    protected GameNode gameNode;

    public FolderVirtualNode(FileHandle file, GameNode gameNode) {
        this(file.name(), SmartByteBuffer.wrap(file.readBytes()), gameNode);
    }

    public FolderVirtualNode(String name, SmartByteBuffer buffer, GameNode gameNode) {
        super(name);
        this.buffer = buffer;
        this.gameNode = gameNode;
        // TODO add virtual folder structure in here!
    }
}
