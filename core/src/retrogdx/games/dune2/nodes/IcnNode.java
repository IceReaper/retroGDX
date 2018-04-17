package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.Dune2;
import retrogdx.games.dune2.readers.Icn;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.TileSetPreview;
import retrogdx.utils.SmartByteBuffer;

public class IcnNode extends AssetFileNode {
    public IcnNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Icn icn = new Icn(this.buffer);

        Sprite[] sprites = new Sprite[icn.tiles.length];
        int[] palette = Dune2.PALETTE;

        for (int i = 0; i < icn.tiles.length; i++) {
            Pixmap pixmap = new Pixmap(icn.width, icn.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < icn.height; y++) {
                for (int x = 0; x < icn.width; x++) {
                    int index = icn.tiles[i][x + y * icn.width] & 0xff;
                    pixmap.drawPixel(x, y, palette[index]);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(icn.width / 2, icn.height / 2);
        }

        previewArea.add(new TileSetPreview(sprites));
    }
}
