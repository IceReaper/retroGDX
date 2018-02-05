package retrogdx.ui;

import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;

public class TextPreview extends VisScrollPane {
    public TextPreview(String text) {
        super(new VisLabel(text));
        this.setFadeScrollBars(false);
        // TODO get this to fullscreen!
    }
}
