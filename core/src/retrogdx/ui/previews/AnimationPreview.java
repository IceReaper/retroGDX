package retrogdx.ui.previews;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisScrollPane;

public class AnimationPreview extends VisScrollPane implements Disposable {
    private Container container;
    private VisImage image;
    private Animation<Sprite> animation;
    private float stateTime = 0;

    public AnimationPreview(Animation<Sprite> animation) {
        super(new Container<>(new VisImage()));

        this.container = (Container) this.getActor();
        this.image = (VisImage) ((Container) this.getActor()).getActor();
        this.animation = animation;

        this.setFadeScrollBars(false);
        this.container.align(Align.topLeft);

        // TODO implement zooming
    }

    public void act(float delta) {
        this.stateTime += delta;
        super.act(delta);
    }

    public void draw(Batch batch, float parentAlpha) {
        Sprite sprite = this.animation.getKeyFrame(this.stateTime, true);
        this.image.setDrawable(new SpriteDrawable(sprite));
        this.container.pad(
                this.getHeight() / 2 - sprite.getOriginY(),
                this.getWidth() / 2 - sprite.getOriginX(),
                0,
                0
        );
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        for (Sprite frame : this.animation.getKeyFrames()) {
            frame.getTexture().dispose();
        }
    }
}
