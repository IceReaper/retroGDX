package retrogdx.ui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisScrollPane;

public class AnimationPreview extends VisScrollPane {
    private Animation<TextureRegion> animation;
    private float stateTime = 0;

    public AnimationPreview(Animation<TextureRegion> animation) {
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
        ((VisImage) this.getActor()).setDrawable(new TextureRegionDrawable(this.animation.getKeyFrame(this.stateTime, true)));
        super.draw(batch, parentAlpha);
    }
}
