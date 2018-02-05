package retrogdx.games.dune2;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.TextPreview;
import retrogdx.utils.SmartByteBuffer;
import retrogdx.utils.SmartByteBuffer.SliceInfo;

public class IniNode extends AssetFileNode {
    private SliceInfo sliceInfo;

    public IniNode(Table previewArea, String name, SliceInfo sliceInfo) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
    }

    protected void showPreview() {
        SmartByteBuffer buffer = this.sliceInfo.slice();
        this.previewArea.add(new TextPreview(buffer.readString(buffer.capacity())));
    }
}
