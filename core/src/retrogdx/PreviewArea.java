package retrogdx;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.kotcrab.vis.ui.widget.VisTable;

public class PreviewArea extends VisTable {
    public <T extends Actor> Cell<T> add(T actor) {
        return super.add(actor).expand().fill();
    }
}
