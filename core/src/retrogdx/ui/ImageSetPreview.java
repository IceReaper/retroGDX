package retrogdx.ui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.kotcrab.vis.ui.widget.*;

public class ImageSetPreview extends VisTable implements Disposable {
    private Pixmap[] images;

    public ImageSetPreview(Pixmap[] images) {
        if (images.length == 0) {
            this.images = new Pixmap[]{new Pixmap(1, 1, Pixmap.Format.RGBA8888)};
        } else {
            this.images = images;
        }

        VisImage image = new VisImage(new Texture(this.images[0]));
        image.setScaling(Scaling.none);
        VisScrollPane scrollPane = new VisScrollPane(image);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setOverscroll(false, false);
        this.add(scrollPane).colspan(2).expand().fill().row();

        VisLabel current = new VisLabel();
        current.setText("1 / " + this.images.length);
        this.add(current).expandX().row();

        VisSlider slider = new VisSlider(1, this.images.length, 1, false);
        slider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                current.setText(((int) slider.getValue()) + " / " + images.length);
                image.setDrawable(new Texture(images[(int) (slider.getValue() - 1)]));
            }
        });
        this.add(slider).expandX().fill();

        // TODO implement zooming
    }

    public void dispose() {
        for (Pixmap image : this.images) {
            image.dispose();
        }
    }
}
