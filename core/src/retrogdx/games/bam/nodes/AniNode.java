package retrogdx.games.bam.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.bam.readers.Ani;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.AnimationPreview;
import retrogdx.utils.SmartByteBuffer;

public class AniNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;

    public AniNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);
        this.smartByteBuffer = smartByteBuffer;
    }

    protected void showPreview() {
        Ani ani = new Ani(this.smartByteBuffer);

        Sprite[] sprites = new Sprite[ani.images.length];

        for (int i = 0; i < ani.images.length; i++) {
            Ani.AniImage image = ani.images[i];
            Pixmap pixmap = new Pixmap(image.width, image.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < image.height; y++) {
                for (int x = 0; x < image.width; x++) {
                    int index = image.pixels[x + y * image.width] & 0xff;
                    pixmap.drawPixel(x, y, (index << 24) | (index << 16) | (index << 8) | 0xff);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(image.originX, image.originY);
        }

        Animation<Sprite> animation = new Animation<>(0.15f, sprites);

        this.previewArea.add(new AnimationPreview(animation));
    }
}
