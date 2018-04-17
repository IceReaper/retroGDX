package retrogdx.ui.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.ui.GamesTree;
import retrogdx.utils.SmartByteBuffer;

public abstract class GameNode extends AssetFolderNode {
    private FileHandle folder;

    public GameNode(String name) {
        super(name);
    }

    public abstract boolean verifyExecutableExists(FileHandle folder);

    protected boolean verifyExecutableExists(FileHandle folder, String executable) {
        for (FileHandle file : folder.list()) {
            if (file.name().equalsIgnoreCase(executable)) {
                this.folder = folder;
                return true;
            }
        }

        return false;
    }

    protected Array<Tree.Node> populate() {
        Array<Tree.Node> files = new Array<>();

        for (FileHandle file : this.folder.list()) {
            if (file.isDirectory()) {
                files.add(new FolderFilesystemNode(file, this));
            } else {
                AssetFileNode node = this.resolve(file);

                if (node != null) {
                    files.add(node);
                }
            }
        }

        GamesTree.sort(files);

        return files;
    }

    public AssetFileNode resolve(FileHandle file) {
        return new AssetUnsupportedNode(file.name(), SmartByteBuffer.wrap(file.readBytes()));
    }
}
