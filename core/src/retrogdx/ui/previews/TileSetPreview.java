package retrogdx.ui.previews;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;

public class TileSetPreview extends VisScrollPane implements Disposable {
    private Sprite[] sprites;

    public TileSetPreview(Sprite[] sprites) {
        super(new VisTable());

        this.sprites = sprites;
        this.setFadeScrollBars(false);
        VisTable table = (VisTable) this.getActor();

        for (int i = 0; i < this.sprites.length; i++) {
            if (i > 0 && i % 16 == 0) {
                table.row();
            }

            VisImage image = new VisImage(new SpriteDrawable(this.sprites[i]));
            image.setScaling(Scaling.none);
            table.add(image);
        }

        // TODO implement zooming
    }

    public void dispose() {
        for (Sprite image : this.sprites) {
            image.getTexture().dispose();
        }
    }
}
