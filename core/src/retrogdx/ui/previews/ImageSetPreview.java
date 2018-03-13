package retrogdx.ui.previews;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.kotcrab.vis.ui.widget.*;

public class ImageSetPreview extends VisTable implements Disposable {
    private Sprite[] sprites;

    public ImageSetPreview(Sprite[] spritesParam) {
        if (spritesParam.length == 0) {
            this.sprites = new Sprite[]{new Sprite(new Texture(new Pixmap(1, 1, Pixmap.Format.RGBA8888)))};
        } else {
            this.sprites = spritesParam;
        }

        VisImage image = new VisImage(new SpriteDrawable(this.sprites[0]));
        image.setScaling(Scaling.none);
        VisScrollPane scrollPane = new VisScrollPane(image);
        scrollPane.setFadeScrollBars(false);

        VisLabel current = new VisLabel("1 / " + this.sprites.length);

        VisSlider slider = new VisSlider(1, this.sprites.length, 1, false);
        slider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                current.setText(((int) slider.getValue()) + " / " + sprites.length);
                image.setDrawable(new SpriteDrawable(sprites[(int) (slider.getValue() - 1)]));
            }
        });

        VisTextButton prev = new VisTextButton("<");
        prev.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                prev.focusLost();
                slider.setValue(Math.max(slider.getMinValue(), slider.getValue() - 1));
                return true;
            }
        });

        VisTextButton next = new VisTextButton(">");
        next.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                next.focusLost();
                slider.setValue(Math.min(slider.getMaxValue(), slider.getValue() + 1));
                return true;
            }
        });

        this.add(scrollPane).colspan(3).expand().fill().row();
        this.add(prev);
        this.add(current);
        this.add(next);
        this.row();
        this.add(slider).colspan(3).expandX().fill();

        // TODO implement zooming
    }

    public void dispose() {
        for (Sprite frame : this.sprites) {
            frame.getTexture().dispose();
        }
    }
}
