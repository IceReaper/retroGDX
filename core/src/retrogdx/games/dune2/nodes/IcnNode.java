package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.Dune2;
import retrogdx.games.dune2.readers.Icn;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.TileSetPreview;
import retrogdx.utils.SmartByteBuffer;

public class IcnNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;
    private String name;

    public IcnNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
        this.name = name;
    }

    protected void showPreview() {
        Icn icn = new Icn(this.smartByteBuffer);

        Pixmap[] pixmaps = new Pixmap[icn.tiles.length];
        int[] palette = Dune2.PALETTE;

        for (int i = 0; i < icn.tiles.length; i++) {
            pixmaps[i] = new Pixmap(icn.width, icn.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < icn.height; y++) {
                for (int x = 0; x < icn.width; x++) {
                    int index = icn.tiles[i][x + y * icn.width] & 0xff;
                    pixmaps[i].drawPixel(x, y, palette[index]);
                }
            }
        }

        this.previewArea.add(new TileSetPreview(pixmaps));
    }
}
