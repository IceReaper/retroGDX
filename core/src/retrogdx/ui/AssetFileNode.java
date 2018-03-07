package retrogdx.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.kotcrab.vis.ui.widget.VisLabel;
import retrogdx.utils.SmartByteBuffer;

public abstract class AssetFileNode extends Node {
    protected SmartByteBuffer buffer;
    protected FileHandle file;
    protected Table previewArea;

    public AssetFileNode(Table previewArea, String name, SmartByteBuffer buffer) {
        this(previewArea, name);
        this.buffer = buffer;
    }

    private AssetFileNode(Table previewArea, String name) {
        super(new VisLabel(name));
        this.previewArea = previewArea;
    }

    public AssetFileNode(Table previewArea, FileHandle file) {
        this(previewArea, file.name());
        this.file = file;
    }

    protected abstract void showPreview();
}
