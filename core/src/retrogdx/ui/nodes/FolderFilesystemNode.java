package retrogdx.ui.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Array;
import retrogdx.ui.GamesTree;

public class FolderFilesystemNode extends AssetFolderNode {
    protected FileHandle folder;
    private GameNode gameNode;

    public FolderFilesystemNode(FileHandle folder, GameNode gameNode) {
        super(folder.name());
        this.folder = folder;
        this.gameNode = gameNode;
    }

    protected Array<Tree.Node> populate() {
        Array<Tree.Node> files = new Array<>();

        for (FileHandle file : this.folder.list()) {
            if (file.isDirectory()) {
                files.add(new FolderFilesystemNode(file, this.gameNode));
            } else {
                AssetFileNode node = this.gameNode.resolve(file);

                if (node != null) {
                    files.add(node);
                }
            }
        }

        GamesTree.sort(files);

        return files;
    }
}
