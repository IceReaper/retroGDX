package retrogdx.ui.previews;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;

public class TileSetPreview extends VisScrollPane implements Disposable {
    private Pixmap[] images;

    public TileSetPreview(Pixmap[] images) {
        super(new VisTable());

        this.images = images;
        this.setFadeScrollBars(false);
        VisTable table = (VisTable) this.getActor();

        for (int i = 0; i < this.images.length; i++) {
            if (i > 0 && i % 16 == 0) {
                table.row();
            }

            VisImage image = new VisImage(new Texture(this.images[i]));
            image.setScaling(Scaling.none);
            table.add(image);
        }

        // TODO implement zooming
    }

    public void dispose() {
        for (Pixmap image : this.images) {
            image.dispose();
        }
    }
}
