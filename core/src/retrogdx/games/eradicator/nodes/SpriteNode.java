package retrogdx.games.eradicator.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.eradicator.readers.Sprite;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.ImageSetPreview;
import retrogdx.utils.SmartByteBuffer;

public class SpriteNode extends AssetFileNode {
    public SpriteNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Sprite sprite = new Sprite(this.buffer);

        com.badlogic.gdx.graphics.g2d.Sprite[] sprites = new com.badlogic.gdx.graphics.g2d.Sprite[sprite.frames.length];

        for (int i = 0; i < sprite.frames.length; i++) {
            Sprite.SpriteFrame frame = sprite.frames[i];
            Pixmap pixmap = new Pixmap(frame.width, frame.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < frame.height; y++) {
                for (int x = 0; x < frame.width; x++) {
                    int index = frame.pixels[x + y * frame.width] & 0xff;
                    pixmap.drawPixel(x, y, (index << 24) | (index << 16) | (index << 8) | 0xff);
                }
            }

            sprites[i] = new com.badlogic.gdx.graphics.g2d.Sprite(new Texture(pixmap));
            sprites[i].setOrigin(frame.width / 2, frame.height / 2);
        }

        previewArea.add(new ImageSetPreview(sprites));
    }
}
