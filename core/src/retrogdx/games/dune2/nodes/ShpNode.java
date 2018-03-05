package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.Dune2;
import retrogdx.games.dune2.readers.Shp;
import retrogdx.games.dune2.readers.Shp.ShpImage;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.ImageSetPreview;
import retrogdx.utils.SmartByteBuffer;

public class ShpNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;
    private String name;

    public ShpNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
        this.name = name;
    }

    protected void showPreview() {
        Shp shp = new Shp(this.smartByteBuffer);

        Pixmap[] pixmaps = new Pixmap[shp.images.length];
        int[] palette = Dune2.PALETTE;

        if (this.name.equals("MENSHPM.SHP")) {
            palette = Dune2.PALETTE_ALT;
        }

        for (int i = 0; i < shp.images.length; i++) {
            ShpImage image = shp.images[i];
            pixmaps[i] = new Pixmap(image.width, image.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < image.height; y++) {
                for (int x = 0; x < image.width; x++) {
                    int index = image.pixels[x + y * image.width] & 0xff;
                    pixmaps[i].drawPixel(x, y, palette[index]);
                }
            }
        }

        this.previewArea.add(new ImageSetPreview(pixmaps));
    }
}
