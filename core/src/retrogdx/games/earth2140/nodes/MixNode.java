package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.earth2140.readers.Mix;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.ImageSetPreview;
import retrogdx.ui.previews.TileSetPreview;
import retrogdx.utils.SmartByteBuffer;

public class MixNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;
    private String name;

    public MixNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
        this.name = name;
    }

    protected void showPreview() {
        Mix mix = new Mix(this.smartByteBuffer);

        Pixmap[] pixmaps = new Pixmap[mix.images.length];

        for (int i = 0; i < mix.images.length; i++) {
            Mix.MixImage image = mix.images[i];
            pixmaps[i] = new Pixmap(image.width, image.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < image.height; y++) {
                for (int x = 0; x < image.width; x++) {
                    int color;

                    if (image.paletteIndex == -1) {
                        color = image.pixelsRgb[x + y * image.width];
                    } else {
                        color = mix.palettes[image.paletteIndex][image.pixelsIndexed[x + y * image.width] & 0xff];
                    }

                    pixmaps[i].drawPixel(x, y, color);
                }
            }
        }

        if (this.name.startsWith("SPRT")) {
            this.previewArea.add(new TileSetPreview(pixmaps));
        } else {
            this.previewArea.add(new ImageSetPreview(pixmaps));
        }
    }
}
