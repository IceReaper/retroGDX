package retrogdx.games.warcraft.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.warcraft.readers.Pal;
import retrogdx.games.warcraft.readers.Spr;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.AnimationPreview;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class SprNode extends AssetFileNode {
    private Map<String, Pal> palettes;

    public SprNode(Table previewArea, String name, SmartByteBuffer buffer, Map<String, Pal> palettes) {
        super(previewArea, name, buffer);
        this.palettes = palettes;
    }

    protected void showPreview() {
        Spr spr = new Spr(this.buffer);

        Sprite[] sprites = new Sprite[spr.frames.length];

        for (int i = 0; i < spr.frames.length; i++) {
            Spr.SprFrame frame = spr.frames[i];
            Pixmap pixmap = new Pixmap(frame.width, frame.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < frame.height; y++) {
                for (int x = 0; x < frame.width; x++) {
                    int index = frame.pixels[x + y * frame.width] & 0xff;
                    // TODO how to determine the correct palette?! - possibly the last one before?
                    pixmap.drawPixel(x, y, this.palettes.get("423.PAL").colors[index]);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(frame.originX, frame.originY);
        }

        Animation<Sprite> animation = new Animation<>(0.15f, sprites);

        this.previewArea.add(new AnimationPreview(animation));
    }
}
