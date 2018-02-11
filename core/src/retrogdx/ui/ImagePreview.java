package retrogdx.ui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisScrollPane;

public class ImagePreview extends VisScrollPane implements Disposable {
    public ImagePreview(Pixmap image) {
        super(new VisImage(new Texture(image)));
        this.setFadeScrollBars(false);
        this.setOverscroll(false, false);
        ((VisImage) this.getActor()).setScaling(Scaling.none);

        // TODO implement zooming
    }

    public void dispose() {
        ((TextureRegionDrawable) ((VisImage) this.getActor()).getDrawable()).getRegion().getTexture().dispose();
    }
}
