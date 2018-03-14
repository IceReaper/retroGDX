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
    private String name;

    public SprNode(Table previewArea, String name, SmartByteBuffer buffer, Map<String, Pal> palettes) {
        super(previewArea, name, buffer);
        this.palettes = palettes;
        this.name = name;
    }

    protected void showPreview() {
        Spr spr = new Spr(this.buffer);

        Sprite[] sprites = new Sprite[spr.frames.length];

        int[] palette = new int[256];
        // TODO verify if we do not need variant 2: 194 + 211
        System.arraycopy(this.palettes.get("191.PAL").colors, 0, palette, 0, 128);
        System.arraycopy(this.palettes.get("210.PAL").colors, 0, palette, 128, 128);
        int imageId = Integer.parseInt(this.name.replace(".SPR", ""));

        if (imageId == 425 || imageId == 426 || imageId == 427) {
            palette = this.palettes.get("424.PAL").colors;
        } else if (imageId == 428 || imageId == 429 || imageId == 430 || imageId == 431) {
            palette = this.palettes.get("423.PAL").colors;
        } else if (imageId == 460) {
            palette = this.palettes.get("459.PAL").colors;
        }

        for (int i = 0; i < spr.frames.length; i++) {
            Spr.SprFrame frame = spr.frames[i];
            Pixmap pixmap = new Pixmap(frame.width, frame.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < frame.height; y++) {
                for (int x = 0; x < frame.width; x++) {
                    int index = frame.pixels[x + y * frame.width] & 0xff;
                    pixmap.drawPixel(x, y, palette[index]);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(frame.originX, frame.originY);
        }

        Animation<Sprite> animation = new Animation<>(0.15f, sprites);

        this.previewArea.add(new AnimationPreview(animation));
    }
}
