package retrogdx.games.eradicator.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.eradicator.readers.Picture;
import retrogdx.games.eradicator.readers.Texture;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

public class PictureNode extends AssetFileNode {
    public PictureNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Picture picture = new Picture(this.buffer);

        Pixmap pixmap = new Pixmap(picture.width, picture.height, Pixmap.Format.RGBA8888);

        for (int y = 0; y < picture.height; y++) {
            for (int x = 0; x < picture.width; x++) {
                int index = picture.pixels[x + y * picture.width] & 0xff;
                // TODO implement palette
                pixmap.drawPixel(x, y, (index << 24) | (index << 16) | (index << 8) | 0xff);
            }
        }

        previewArea.add(new ImagePreview(pixmap));
    }
}
