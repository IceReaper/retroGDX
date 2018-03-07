package retrogdx.generic.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.AutodeskFlic;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.AnimationPreview;
import retrogdx.utils.SmartByteBuffer;

public class AutodeskFlicNode extends AssetFileNode {
    public AutodeskFlicNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name, buffer);
    }

    protected void showPreview() {
        AutodeskFlic flic = new AutodeskFlic(this.buffer);

        Sprite[] sprites = new Sprite[flic.frames.length];

        for (int i = 0; i < flic.frames.length; i++) {
            Pixmap pixmap = new Pixmap(flic.width, flic.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < flic.height; y++) {
                for (int x = 0; x < flic.width; x++) {
                    pixmap.drawPixel(x, y, flic.frames[i][x + y * flic.width]);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(flic.width / 2, flic.height / 2);
        }

        Animation<Sprite> animation = new Animation<>(flic.speed / 1000f, sprites);

        this.previewArea.add(new AnimationPreview(animation));
    }
}
