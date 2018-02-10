package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.earth2140.readers.Mix;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.ImagePreview;
import retrogdx.utils.SliceInfo;

public class MixNode extends AssetFileNode {
    private SliceInfo sliceInfo;

    public MixNode(Table previewArea, String name, SliceInfo sliceInfo) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
    }

    protected void showPreview() {
        Mix mix = new Mix(this.sliceInfo.slice());

        int width = 0;
        int height = 0;

        for (Mix.MixImage image : mix.images) {
            width = Math.max(image.width, width);
            height = Math.max(image.height, height);
        }

        int totalX = (int) Math.ceil(Math.sqrt(mix.images.length));
        int totalY = (int) Math.ceil(mix.images.length / (float) totalX);

        Pixmap pixmap = new Pixmap(totalX * width, totalY * height, Pixmap.Format.RGBA8888);

        for (int ty = 0; ty < totalX; ty++) {
            for (int tx = 0; tx < totalY; tx++) {
                int tileIndex = tx + ty * totalX;

                if (tileIndex >= mix.images.length) {
                    break;
                }

                Mix.MixImage image = mix.images[tileIndex];

                for (int y = 0; y < image.height; y++) {
                    for (int x = 0; x < image.width; x++) {
                        int color;

                        if (image.paletteIndex == -1) {
                            color = image.pixelsRgb[x + y * image.width];
                        } else {
                            color = mix.palettes[image.paletteIndex][image.pixelsIndexed[x + y * image.width] & 0xff];
                        }

                        pixmap.drawPixel(tx * width + x, ty * height + y, color);
                    }
                }
            }
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
