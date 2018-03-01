package retrogdx.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class Game extends AssetFolderNode {
    public Game(Table previewArea, String name) {
        super(previewArea, name);
    }

    public abstract boolean parse(FileHandle folder);
}
