package retrogdx.generic.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.Palette;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

public class PaletteNode extends AssetFileNode {
    public PaletteNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name, buffer);
    }

    protected void showPreview() {
        Palette palette = new Palette(this.buffer);

        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);

        for (int i = 0; i < 256; i++) {
            pixmap.drawPixel(i % 16, i / 16, palette.colors[i]);
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
