package retrogdx.games.warwind.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.warwind.readers.D3gr;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.ImageSetPreview;
import retrogdx.utils.SmartByteBuffer;

public class D3grNode extends AssetFileNode {
    public D3grNode(Table previewArea, String name, SmartByteBuffer buffer) {
        super(previewArea, name, buffer);
    }

    protected void showPreview() {
        D3gr d3gr = new D3gr(this.buffer);

        Sprite[] sprites = new Sprite[d3gr.frames.length];

        for (int i = 0; i < d3gr.frames.length; i++) {
            D3gr.D3grFrame frame = d3gr.frames[i];
            Pixmap pixmap = new Pixmap(frame.width, frame.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < frame.height; y++) {
                for (int x = 0; x < frame.width; x++) {
                    int index = frame.pixels[x + y * frame.width] & 0xff;
                    pixmap.drawPixel(x, y, (index << 24) | (index << 16) | (index << 8) | 0xff);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(frame.width / 2, frame.height / 2);
        }

        this.previewArea.add(new ImageSetPreview(sprites));
    }
}
