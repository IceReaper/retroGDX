package retrogdx.generic.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.generic.readers.AutodeskFlic;
import retrogdx.ui.AnimationPreview;
import retrogdx.ui.AssetFileNode;
import retrogdx.utils.SmartByteBuffer;

public class AutodeskFlicNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;

    public AutodeskFlicNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
    }

    protected void showPreview() {
        AutodeskFlic flic = new AutodeskFlic(this.smartByteBuffer);

        Texture[] frames = new Texture[flic.frames.length];

        for (int i = 0; i < flic.frames.length; i++) {
            Pixmap pixmap = new Pixmap(flic.width, flic.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < flic.height; y++) {
                for (int x = 0; x < flic.width; x++) {
                    pixmap.drawPixel(x, y, flic.frames[i][x + y * flic.width]);
                }
            }

            frames[i] = new Texture(pixmap);
        }

        Animation<Texture> animation = new Animation<>(flic.speed / 1000f, frames);

        this.previewArea.add(new AnimationPreview(animation));
    }
}
