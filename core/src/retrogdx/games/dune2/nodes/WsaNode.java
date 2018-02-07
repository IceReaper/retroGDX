package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.Dune2;
import retrogdx.games.dune2.readers.Wsa;
import retrogdx.ui.AnimationPreview;
import retrogdx.ui.AssetFileNode;
import retrogdx.utils.SmartByteBuffer.SliceInfo;

public class WsaNode extends AssetFileNode {
    private SliceInfo sliceInfo;

    public WsaNode(Table previewArea, String name, SliceInfo sliceInfo) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
    }

    protected void showPreview() {
        Wsa wsa = new Wsa(this.sliceInfo.slice());

        Pixmap pixmap = new Pixmap(wsa.width * wsa.frames.length, wsa.height, Pixmap.Format.RGBA8888);

        for (int i = 0; i < wsa.frames.length; i++) {
            for (int y = 0; y < wsa.height; y++) {
                for (int x = 0; x < wsa.width; x++) {
                    int index = wsa.frames[i][x + y * wsa.width] & 0xff;
                    pixmap.drawPixel(i * wsa.width + x, y, Dune2.PALETTE[index]);
                }
            }
        }

        // TODO what is dune > static?
        // TODO finale > all broken?
        // TODO intro > see finale
        // TODO palette ?

        Texture texture = new Texture(pixmap);
        TextureRegion[][] frames = TextureRegion.split(texture, wsa.width, wsa.height);
        Animation<TextureRegion> animation = new Animation<>(1024f / wsa.animationSpeed, frames[0]);

        // TODO finish animation player preview widget

        this.previewArea.add(new AnimationPreview(animation));
    }
}
