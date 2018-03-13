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

    public ImgNode(Table previewArea, String name, SmartByteBuffer buffer, Map<String, retrogdx.games.warcraft.readers.Pal> palettes) {
        super(previewArea, name, buffer);
        this.palettes = palettes;
    }

    protected void showPreview() {
        Img img = new Img(this.buffer);

        Pixmap pixmap = new Pixmap(img.width, img.height, Pixmap.Format.RGBA8888);

        for (int y = 0; y < img.height; y++) {
            for (int x = 0; x < img.width; x++) {
                int index = img.pixels[x + y * img.width] & 0xff;
                // TODO how to determine the correct palette?! - possibly the last one before?
                pixmap.drawPixel(x, y, this.palettes.get("255.PAL").colors[index]);
            }
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
