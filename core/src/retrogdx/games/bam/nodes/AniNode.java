package retrogdx.games.bam.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.bam.readers.Ani;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.AnimationPreview;
import retrogdx.utils.SmartByteBuffer;

public class AniNode extends AssetFileNode {
    public AniNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Ani ani = new Ani(this.buffer);

        Sprite[] sprites = new Sprite[ani.frames.length];

        for (int i = 0; i < ani.frames.length; i++) {
            Ani.AniFrame frame = ani.frames[i];
            Pixmap pixmap = new Pixmap(frame.width, frame.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < frame.height; y++) {
                for (int x = 0; x < frame.width; x++) {
                    int index = frame.pixels[x + y * frame.width] & 0xff;
                    // TODO implement palette
                    pixmap.drawPixel(x, y, (index << 24) | (index << 16) | (index << 8) | 0xff);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(frame.originX, frame.originY);
        }

        Animation<Sprite> animation = new Animation<>(0.15f, sprites);

        previewArea.add(new AnimationPreview(animation));
    }
}
