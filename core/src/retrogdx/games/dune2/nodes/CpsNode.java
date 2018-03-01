package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.Dune2;
import retrogdx.games.dune2.readers.Cps;
import retrogdx.games.dune2.readers.Pal;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

public class CpsNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;
    private String name;

    public CpsNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
        this.name = name;
    }

    protected void showPreview() {
        Cps cps = new Cps(this.smartByteBuffer);

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

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
