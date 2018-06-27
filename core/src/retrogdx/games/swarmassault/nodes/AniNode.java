package retrogdx.games.swarmassault.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.swarmassault.readers.Image;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.AnimationPreview;
import retrogdx.utils.SmartByteBuffer;

public class AniNode extends AssetFileNode {
    private SmartByteBuffer[] frames;
    private int[] palette;

    public AniNode(String name, SmartByteBuffer[] frames, int[] palette) {
        super(name, null);
        this.frames = frames;
        this.palette = palette;
    }

    public void showPreview(Table previewArea) {
        Sprite[] sprites = new Sprite[this.frames.length];

        for (int i = 0; i < sprites.length; i++) {
            Image image = new Image(this.frames[i]);
            Pixmap pixmap = new Pixmap(image.width, image.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < image.height; y++) {
                for (int x = 0; x < image.width; x++) {
                    int index = image.pixels[x + y * image.width] & 0xff;
                    pixmap.drawPixel(x, y, this.palette[index]);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            // TODO we might be able to fetch this from meta block!
            sprites[i].setOrigin(image.width / 2, image.height / 2);
        }

        Animation<Sprite> animation = new Animation<>(0.15f, sprites);

        previewArea.add(new AnimationPreview(animation));
    }
}
