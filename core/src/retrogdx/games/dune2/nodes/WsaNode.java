package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.Dune2;
import retrogdx.games.dune2.readers.Pal;
import retrogdx.games.dune2.readers.Wsa;
import retrogdx.ui.AnimationPreview;
import retrogdx.ui.AssetFileNode;
import retrogdx.utils.SmartByteBuffer.SliceInfo;

import java.util.Map;

public class WsaNode extends AssetFileNode {
    private SliceInfo sliceInfo;
    private String name;
    private Map<String, SliceInfo> palettes;
    private Map<String, SliceInfo> animations;

    public WsaNode(Table previewArea, String name, SliceInfo sliceInfo, Map<String, SliceInfo> palettes, Map<String, SliceInfo> animations) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
        this.name = name;
        this.palettes = palettes;
        this.animations = animations;
    }

    protected void showPreview() {
        Wsa lastWsa = null;

        if (this.name.equals("INTRO7B.WSA")) {
            lastWsa = new Wsa(this.animations.get("INTRO7A.WSA").slice());
        } else if (this.name.equals("INTRO8B.WSA")) {
            lastWsa = new Wsa(this.animations.get("INTRO8A.WSA").slice());
        } else if (this.name.equals("INTRO8C.WSA")) {
            lastWsa = new Wsa(this.animations.get("INTRO8A.WSA").slice());
        } else if (this.name.equals("HFINALC.WSA")) {
            lastWsa = new Wsa(this.animations.get("HFINALB.WSA").slice());
        } else if (this.name.equals("OFINALB.WSA")) {
            lastWsa = new Wsa(this.animations.get("OFINALA.WSA").slice());
        } else if (this.name.equals("OFINALC.WSA")) {
            lastWsa = new Wsa(this.animations.get("OFINALA.WSA").slice());
        }

        Wsa wsa = new Wsa(this.sliceInfo.slice(), lastWsa == null ? null : lastWsa.frames[lastWsa.frames.length - 1]);

        Pixmap pixmap = new Pixmap(wsa.width * wsa.frames.length, wsa.height, Pixmap.Format.RGBA8888);
        int[] palette = Dune2.PALETTE;

        if (this.name.equals("WESTWOOD.WSA")) {
            palette = new Pal(this.palettes.get("WESTWOOD.PAL").slice()).colors;
        } else if (this.name.startsWith("INTRO")) {
            palette = new Pal(this.palettes.get("INTRO.PAL").slice()).colors;
        }

        for (int i = 0; i < wsa.frames.length; i++) {
            for (int y = 0; y < wsa.height; y++) {
                for (int x = 0; x < wsa.width; x++) {
                    int index = wsa.frames[i][x + y * wsa.width] & 0xff;
                    pixmap.drawPixel(i * wsa.width + x, y, palette[index]);
                }
            }
        }

        Texture texture = new Texture(pixmap);
        TextureRegion[][] frames = TextureRegion.split(texture, wsa.width, wsa.height);
        Animation<TextureRegion> animation = new Animation<>(1024f / wsa.animationSpeed, frames[0]);

        this.previewArea.add(new AnimationPreview(animation));
    }
}
