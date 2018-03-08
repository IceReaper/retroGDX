package retrogdx.games.dominion.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dominion.readers.Spr;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

public class SprNode extends AssetFileNode {
    public SprNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name, buffer);
    }

    protected void showPreview() {
        Spr spr = new Spr(this.buffer);

        Pixmap pixmap = new Pixmap(spr.width, spr.height, Pixmap.Format.RGBA8888);

        for (int y = 0; y < spr.height; y++) {
            for (int x = 0; x < spr.width; x++) {
                int index = spr.pixels[x + y * spr.width] & 0xff;
                // TODO implement palette
                pixmap.drawPixel(x, y, (index << 24) | (index << 16) | (index << 8) | 0xff);
            }
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
