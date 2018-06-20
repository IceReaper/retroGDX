package retrogdx.games.swarmassault.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.swarmassault.readers.Image;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

public class ImageNode extends AssetFileNode {
    private int[] palette;

    public ImageNode(String name, SmartByteBuffer buffer, int[] palette) {
        super(name, buffer);
        this.palette = palette;
    }

    public void showPreview(Table previewArea) {
        Image image = new Image(this.buffer);

        Pixmap pixmap = new Pixmap(image.width, image.height, Pixmap.Format.RGBA8888);

        for (int y = 0; y < image.height; y++) {
            for (int x = 0; x < image.width; x++) {
                int index = image.pixels[x + y * image.width] & 0xff;
                pixmap.drawPixel(x, y, this.palette[index]);
            }
        }

        previewArea.add(new ImagePreview(pixmap));
    }
}
