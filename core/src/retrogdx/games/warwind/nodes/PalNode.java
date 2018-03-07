package retrogdx.games.warwind.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.warwind.readers.Pal;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

public class PalNode extends AssetFileNode {
    public PalNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name, buffer);
    }

    protected void showPreview() {
        Pal pal = new Pal(this.buffer);

        Pixmap pixmap = new Pixmap(256, 256, Pixmap.Format.RGBA8888);

        for (int i = 0; i < 256 * 256; i++) {
            pixmap.drawPixel(i % 256, i / 256, pal.colors[i]);
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
