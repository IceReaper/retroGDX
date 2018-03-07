package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.Dune2;
import retrogdx.games.dune2.readers.Shp;
import retrogdx.games.dune2.readers.Shp.ShpFrame;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.ImageSetPreview;
import retrogdx.utils.SmartByteBuffer;

public class ShpNode extends AssetFileNode {
    private String name;

    public ShpNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name, buffer);
        this.name = name;
    }

    protected void showPreview() {
        Shp shp = new Shp(this.buffer);

        Sprite[] sprites = new Sprite[shp.frames.length];
        int[] palette = Dune2.PALETTE;

        if (this.name.equals("MENSHPM.SHP")) {
            palette = Dune2.PALETTE_ALT;
        }

        for (int i = 0; i < shp.frames.length; i++) {
            ShpFrame frame = shp.frames[i];
            Pixmap pixmap = new Pixmap(frame.width, frame.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < frame.height; y++) {
                for (int x = 0; x < frame.width; x++) {
                    int index = frame.pixels[x + y * frame.width] & 0xff;
                    pixmap.drawPixel(x, y, palette[index]);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(frame.width / 2, frame.height / 2);
        }

        this.previewArea.add(new ImageSetPreview(sprites));
    }
}
