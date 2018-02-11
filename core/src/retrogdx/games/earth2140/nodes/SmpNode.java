package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.earth2140.readers.Smp;
import retrogdx.ui.AssetFileNode;
import retrogdx.utils.SmartByteBuffer;

public class SmpNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;

    public SmpNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
    }

    protected void showPreview() {
        Smp smp = new Smp(this.smartByteBuffer);

        // TODO replace with audio player widget
        smp.clip.start();
    }
}
