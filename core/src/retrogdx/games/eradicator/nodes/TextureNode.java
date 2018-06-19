package retrogdx.games.eradicator.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.eradicator.readers.Texture;
import retrogdx.games.futurecop.readers.Bmp;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

public class TextureNode extends AssetFileNode {
    public TextureNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Texture texture = new Texture(this.buffer);

        Pixmap pixmap = new Pixmap(texture.width, texture.height, Pixmap.Format.RGBA8888);

        for (int y = 0; y < texture.height; y++) {
            for (int x = 0; x < texture.width; x++) {
                int index = texture.pixels[x + y * texture.width] & 0xff;
                // TODO implement palette
                pixmap.drawPixel(x, y, (index << 24) | (index << 16) | (index << 8) | 0xff);
            }
        }

        previewArea.add(new ImagePreview(pixmap));
    }
}
