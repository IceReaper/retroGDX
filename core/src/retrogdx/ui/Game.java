package retrogdx.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class Game extends AssetFolderNode {
    protected FileHandle folder;

    public Game(Table previewArea, String name) {
        super(previewArea, name);
    }

    public abstract boolean verify(FileHandle folder);

    protected boolean verify(FileHandle folder, String executable) {
        for (FileHandle file : folder.list()) {
            if (file.name().equalsIgnoreCase(executable)) {
                this.folder = folder;
                return true;
            }
        }

        return false;
    }
}
