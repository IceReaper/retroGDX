package retrogdx.games.warcraft.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.warcraft.readers.Pal;
import retrogdx.games.warcraft.readers.Tile;
import retrogdx.games.warcraft.readers.Tilepart;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.TileSetPreview;
import retrogdx.utils.SmartByteBuffer;

public class TileNode extends AssetFileNode {
    private SmartByteBuffer tileBuffer;
    private SmartByteBuffer tilepartBuffer;
    private SmartByteBuffer paletteBuffer;

    public TileNode(Table previewArea, String name, SmartByteBuffer tileBuffer, SmartByteBuffer tilepartBuffer, SmartByteBuffer paletteBuffer) {
        super(previewArea, name, null);

        this.tileBuffer = tileBuffer;
        this.tilepartBuffer = tilepartBuffer;
        this.paletteBuffer = paletteBuffer;
    }

    protected void showPreview() {
        Pal pal = new Pal(this.paletteBuffer);
        Tilepart tilepart = new Tilepart(this.tilepartBuffer);
        Tile tile = new Tile(this.tileBuffer);

        Sprite[] sprites = new Sprite[tile.tiles.length];

        for (int i = 0; i < tile.tiles.length; i++) {
            Pixmap pixmap = new Pixmap(2 * 8, 2 * 8, Pixmap.Format.RGBA8888);

            for (int j = 0; j < 2 * 2; j++) {
                Tile.TileInfo tileInfo = tile.tiles[i][j];

                for (int k = 0; k < 8 * 8; k++) {
                    int index = tilepart.tiles[tileInfo.index][k] & 0xff;
                    // TODO this modulo is defenitely wrong!

                    pixmap.drawPixel(
                            tileInfo.flipX ? (j % 2) * 8 + 7 - (k % 8) : (j % 2) * 8 + (k % 8),
                            tileInfo.flipY ? (j / 2) * 8 + 7 - (k / 8) : (j / 2) * 8 + (k / 8),
                            pal.colors[index % 128]
                    );
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(8, 8);
        }

        this.previewArea.add(new TileSetPreview(sprites));
    }
}
