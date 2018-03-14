package retrogdx.games.warcraft.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.warcraft.readers.Img;
import retrogdx.games.warcraft.readers.Pal;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class ImgNode extends AssetFileNode {
    private Map<String, Pal> palettes;
    private String name;

    public ImgNode(Table previewArea, String name, SmartByteBuffer buffer, Map<String, retrogdx.games.warcraft.readers.Pal> palettes) {
        super(previewArea, name, buffer);
        this.palettes = palettes;
        this.name = name;
    }

    protected void showPreview() {
        Img img = new Img(this.buffer);

        Pixmap pixmap = new Pixmap(img.width, img.height, Pixmap.Format.RGBA8888);

        int[] palette;
        int imageId = Integer.parseInt(this.name.replace(".IMG", ""));

        if (imageId == 216) {
            palette = this.palettes.get("217.PAL").colors;
        } else if (imageId == 261 || imageId == 470 || imageId == 471) {
            palette = this.palettes.get("260.PAL").colors;
        } else if (imageId == 411 || imageId == 419 || imageId == 420) {
            palette = this.palettes.get("413.PAL").colors;
        } else if (imageId == 412) {
            palette = this.palettes.get("414.PAL").colors;
        } else if (imageId == 415) {
            palette = this.palettes.get("416.PAL").colors;
        } else if (imageId == 417) {
            palette = this.palettes.get("418.PAL").colors;
        } else if (imageId == 421) {
            palette = this.palettes.get("423.PAL").colors;
        } else if (imageId == 422) {
            palette = this.palettes.get("424.PAL").colors;
        } else if (imageId == 456) {
            palette = this.palettes.get("457.PAL").colors;
        } else if (imageId == 458) {
            palette = this.palettes.get("459.PAL").colors;
        } else if (imageId >= 218 && imageId <= 229 || (imageId >= 237 && imageId <= 242) || (imageId >= 406 && imageId <= 409)) {
            palette = new int[256];

            if (imageId % 2 == 0) {
                System.arraycopy(this.palettes.get("191.PAL").colors, 0, palette, 0, 128);
                System.arraycopy(this.palettes.get("210.PAL").colors, 0, palette, 128, 128);
            } else {
                System.arraycopy(this.palettes.get("194.PAL").colors, 0, palette, 0, 128);
                System.arraycopy(this.palettes.get("211.PAL").colors, 0, palette, 128, 128);
            }
        } else if ((imageId >= 233 && imageId <= 236) || (imageId >= 248 && imageId <= 252)) {
            palette = new int[256];

            if (imageId % 2 == 1) {
                System.arraycopy(this.palettes.get("191.PAL").colors, 0, palette, 0, 128);
                System.arraycopy(this.palettes.get("210.PAL").colors, 0, palette, 128, 128);
            } else {
                System.arraycopy(this.palettes.get("194.PAL").colors, 0, palette, 0, 128);
                System.arraycopy(this.palettes.get("211.PAL").colors, 0, palette, 128, 128);
            }
        } else {
            palette = this.palettes.get("255.PAL").colors;
        }

        for (int y = 0; y < img.height; y++) {
            for (int x = 0; x < img.width; x++) {
                int index = img.pixels[x + y * img.width] & 0xff;
                // TODO how to determine the correct palette?! - possibly the last one before?
                pixmap.drawPixel(x, y, palette[index]);
            }
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
