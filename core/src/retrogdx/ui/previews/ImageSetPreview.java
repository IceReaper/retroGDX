package retrogdx.ui.previews;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
        this.add(scrollPane).colspan(2).expand().fill().row();

        VisLabel current = new VisLabel();
        current.setText("1 / " + this.sprites.length);
        this.add(current).expandX().row();

        VisSlider slider = new VisSlider(1, this.sprites.length, 1, false);
        slider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                current.setText(((int) slider.getValue()) + " / " + sprites.length);
                image.setDrawable(new SpriteDrawable(sprites[(int) (slider.getValue() - 1)]));
            }
        });
        this.add(slider).expandX().fill();

        // TODO implement zooming
    }

    public void dispose() {
        for (Sprite frame : this.sprites) {
            frame.getTexture().dispose();
        }
    }
}
