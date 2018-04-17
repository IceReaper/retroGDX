package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.earth2140.readers.Mix;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.ImageSetPreview;
import retrogdx.ui.previews.TileSetPreview;
import retrogdx.utils.SmartByteBuffer;

public class MixNode extends AssetFileNode {
    private String name;

    public MixNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
        this.name = name;
    }

    public void showPreview(Table previewArea) {
        Mix mix = new Mix(this.buffer);

        Sprite[] sprites = new Sprite[mix.frames.length];

        for (int i = 0; i < mix.frames.length; i++) {
            Mix.MixFrame frame = mix.frames[i];
            Pixmap pixmap = new Pixmap(frame.width, frame.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < frame.height; y++) {
                for (int x = 0; x < frame.width; x++) {
                    int color;

                    if (frame.paletteIndex == -1) {
                        color = frame.pixelsRgb[x + y * frame.width];
                    } else {
                        color = mix.palettes[frame.paletteIndex][frame.pixelsIndexed[x + y * frame.width] & 0xff];
                    }

                    pixmap.drawPixel(x, y, color);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(frame.width / 2, frame.height / 2);
        }

        if (this.name.startsWith("SPRT")) {
            previewArea.add(new TileSetPreview(sprites));
        } else {
            previewArea.add(new ImageSetPreview(sprites));
        }
    }
}
