package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.Dune2;
import retrogdx.games.dune2.readers.Shp;
import retrogdx.games.dune2.readers.Shp.ShpImage;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.ImagePreview;
import retrogdx.utils.SmartByteBuffer.SliceInfo;

public class ShpNode extends AssetFileNode {
    private SliceInfo sliceInfo;
    private String name;

    public ShpNode(Table previewArea, String name, SliceInfo sliceInfo) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
        this.name = name;
    }

    protected void showPreview() {
        Shp shp = new Shp(this.sliceInfo.slice());

        int width = 0;
        int height = 0;

        for (Shp.ShpImage image : shp.images) {
            width = Math.max(image.width, width);
            height = Math.max(image.height, height);
        }

        Pixmap pixmap = new Pixmap(width * shp.images.length, height, Pixmap.Format.RGBA8888);
        int[] palette = Dune2.PALETTE;

        if (this.name.equals("MENSHPM.SHP")) {
            palette = Dune2.PALETTE_ALT;
        }

        // TODO PIECES palette?

        for (int i = 0; i < shp.images.length; i++) {
            ShpImage image = shp.images[i];
            for (int y = 0; y < image.height; y++) {
                for (int x = 0; x < image.width; x++) {
                    int index = image.pixels[x + y * image.width] & 0xff;
                    pixmap.drawPixel(i * width + x, y, palette[index]);
                }
            }
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
