package retrogdx.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisScrollPane;

public class AnimationPreview extends VisScrollPane implements Disposable {
    private Animation<Texture> animation;
    private float stateTime = 0;

    public AnimationPreview(Animation<Texture> animation) {
        super(new VisImage());
        this.animation = animation;
        this.setFadeScrollBars(false);
        this.setOverscroll(false, false);
        ((VisImage) this.getActor()).setScaling(Scaling.none);

        // TODO implement zooming
        // TODO implement player controls
    }

    public void act(float delta) {
        this.stateTime += delta;
        super.act(delta);
    }

    public void draw(Batch batch, float parentAlpha) {
        ((VisImage) this.getActor()).setDrawable(this.animation.getKeyFrame(this.stateTime, true));
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        for (Texture frame : this.animation.getKeyFrames()) {
            frame.dispose();
        }
    }
}
