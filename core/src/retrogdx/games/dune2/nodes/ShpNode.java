package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.Dune2;
import retrogdx.games.dune2.readers.Shp;
import retrogdx.games.dune2.readers.Shp.ShpImage;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.ImagePreview;
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

        int width = 0;
        int height = 0;

        for (Shp.ShpImage image : shp.images) {
            width = Math.max(image.width, width);
            height = Math.max(image.height, height);
        }

        int totalX = (int) Math.ceil(Math.sqrt(shp.images.length));
        int totalY = (int) Math.ceil(shp.images.length / (float) totalX);

        Pixmap pixmap = new Pixmap(totalX * width, totalY * height, Pixmap.Format.RGBA8888);
        int[] palette = Dune2.PALETTE;

        if (this.name.equals("MENSHPM.SHP")) {
            palette = Dune2.PALETTE_ALT;
        }

        for (int ty = 0; ty < totalX; ty++) {
            for (int tx = 0; tx < totalY; tx++) {
                int tileIndex = tx + ty * totalX;

                if (tileIndex >= shp.images.length) {
                    break;
                }

                ShpImage image = shp.images[tileIndex];

                for (int y = 0; y < image.height; y++) {
                    for (int x = 0; x < image.width; x++) {
                        int index = image.pixels[x + y * image.width] & 0xff;
                        pixmap.drawPixel(tx * width + x, ty * height + y, palette[index]);
                    }
                }
            }
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
