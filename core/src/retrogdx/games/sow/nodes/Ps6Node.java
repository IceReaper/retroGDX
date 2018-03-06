package retrogdx.games.sow.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.sow.readers.Ps6;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.AnimationPreview;
import retrogdx.utils.SmartByteBuffer;

public class Ps6Node extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;

    public Ps6Node(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
    }

    protected void showPreview() {
        Ps6 ps6 = new Ps6(this.smartByteBuffer);

        Sprite[] sprites = new Sprite[ps6.frames.length];

        for (int i = 0; i < ps6.frames.length; i++) {
            Ps6.Ps6Frame frame = ps6.frames[i];
            Pixmap pixmap = new Pixmap(frame.width, frame.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < frame.height; y++) {
                for (int x = 0; x < frame.width; x++) {
                    pixmap.drawPixel(x, y, ps6.frames[i].pixels[x + y * frame.width]);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(frame.originX, frame.originY);
        }

        Animation<Sprite> animation = new Animation<>(0.15f, sprites);

        this.previewArea.add(new AnimationPreview(animation));
    }
}
