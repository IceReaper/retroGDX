package retrogdx.games.futurecop.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.futurecop.readers.Bmp;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

public class BmpNode extends AssetFileNode {
    public BmpNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Bmp bmp = new Bmp(this.buffer);

        Pixmap pixmap = new Pixmap(256, 256, Pixmap.Format.RGBA8888);

        for (int y = 0; y < 256; y++) {
            for (int x = 0; x < 256; x++) {
                pixmap.drawPixel(x, y, bmp.pixels[y * 256 + x]);
            }
        }

        previewArea.add(new ImagePreview(pixmap));
    }
}
