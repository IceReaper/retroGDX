package retrogdx.ui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisScrollPane;

public class ImagePreview extends VisScrollPane {
    public ImagePreview(Pixmap image) {
        super(new VisImage(new Texture(image)));
        this.setFadeScrollBars(false);
        // TODO implement zooming with left / right click!
    }
}
