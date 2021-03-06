package retrogdx.games.bam.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.bam.readers.Tlb;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.TileSetPreview;
import retrogdx.utils.SmartByteBuffer;

public class TlbNode extends AssetFileNode {
    public TlbNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Tlb tlb = new Tlb(this.buffer);

        Sprite[] sprites = new Sprite[tlb.tiles.length];

        for (int i = 0; i < tlb.tiles.length; i++) {
            Pixmap pixmap = new Pixmap(tlb.width, tlb.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < tlb.height; y++) {
                for (int x = 0; x < tlb.width; x++) {
                    int index = tlb.tiles[i].pixels[x + y * tlb.width] & 0xff;
                    pixmap.drawPixel(x, y, (index << 24) | (index << 16) | (index << 8) | 0xff);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(tlb.width / 2, tlb.height / 2);
        }

        previewArea.add(new TileSetPreview(sprites));
    }
}
