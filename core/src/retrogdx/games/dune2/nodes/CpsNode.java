package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.Dune2;
import retrogdx.games.dune2.readers.Cps;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

public class CpsNode extends AssetFileNode {
    public CpsNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Cps cps = new Cps(this.buffer);

        Pixmap pixmap = new Pixmap(320, 200, Pixmap.Format.RGBA8888);
        int[] palette = cps.palette != null ? cps.palette.colors : Dune2.PALETTE;

        if (this.name.equals("MENTATM.CPS")) {
            palette = Dune2.PALETTE_ALT;
        }

        for (int y = 0; y < 200; y++) {
            for (int x = 0; x < 320; x++) {
                pixmap.drawPixel(x, y, palette[cps.pixels[y * 320 + x] & 0xff]);
            }
        }

        previewArea.add(new ImagePreview(pixmap));
    }
}
