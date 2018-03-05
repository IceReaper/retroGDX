package retrogdx.ui.previews;

import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;

public class TextPreview extends VisScrollPane {
    public TextPreview(String text) {
        super(new VisLabel(text));
        this.setFadeScrollBars(false);
        this.setOverscroll(false, false);
        ((VisLabel) this.getActor()).setAlignment(Align.topLeft);
    }
}
