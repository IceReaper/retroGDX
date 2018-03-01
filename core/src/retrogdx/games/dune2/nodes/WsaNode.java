package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.Dune2;
import retrogdx.games.dune2.readers.Pal;
import retrogdx.games.dune2.readers.Wsa;
import retrogdx.ui.AnimationPreview;
import retrogdx.ui.AssetFileNode;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class WsaNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;
    private String name;
    private Map<String, SmartByteBuffer> palettes;
    private Map<String, SmartByteBuffer> animations;

    public WsaNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer, Map<String, SmartByteBuffer> palettes, Map<String, SmartByteBuffer> animations) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
        this.name = name;
        this.palettes = palettes;
        this.animations = animations;
    }

    protected void showPreview() {
        Wsa lastWsa = null;

        if (this.name.equals("INTRO7B.WSA")) {
            lastWsa = new Wsa(this.animations.get("INTRO7A.WSA"));
        } else if (this.name.equals("INTRO8B.WSA")) {
            lastWsa = new Wsa(this.animations.get("INTRO8A.WSA"));
        } else if (this.name.equals("INTRO8C.WSA")) {
            lastWsa = new Wsa(this.animations.get("INTRO8A.WSA"));
        } else if (this.name.equals("HFINALC.WSA")) {
            lastWsa = new Wsa(this.animations.get("HFINALB.WSA"));
        } else if (this.name.equals("OFINALB.WSA")) {
            lastWsa = new Wsa(this.animations.get("OFINALA.WSA"));
        } else if (this.name.equals("OFINALC.WSA")) {
            lastWsa = new Wsa(this.animations.get("OFINALA.WSA"));
        }

        Wsa wsa = new Wsa(this.smartByteBuffer, lastWsa == null ? null : lastWsa.frames[lastWsa.frames.length - 1]);

        Texture[] frames = new Texture[wsa.frames.length];

        for (int i = 0; i < wsa.frames.length; i++) {
            Pixmap pixmap = new Pixmap(wsa.width, wsa.height, Pixmap.Format.RGBA8888);
            int[] palette = Dune2.PALETTE;

            if (this.name.equals("WESTWOOD.WSA")) {
                palette = new Pal(this.palettes.get("WESTWOOD.PAL")).colors;
            } else if (this.name.startsWith("INTRO")) {
                palette = new Pal(this.palettes.get("INTRO.PAL")).colors;
            }

            for (int y = 0; y < wsa.height; y++) {
                for (int x = 0; x < wsa.width; x++) {
                    int index = wsa.frames[i][x + y * wsa.width] & 0xff;
                    pixmap.drawPixel(x, y, palette[index]);
                }
            }

            frames[i] = new Texture(pixmap);
        }

        Animation<Texture> animation = new Animation<>(0.15f, frames);

        this.previewArea.add(new AnimationPreview(animation));
    }
}
