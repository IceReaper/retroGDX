package retrogdx;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Disposable;
import com.kotcrab.vis.ui.widget.VisTable;

public class PreviewArea extends VisTable {
    public void clearChildren() {
        for (Cell cell : this.getCells()) {
            Actor actor = cell.getActor();

            if (actor instanceof Disposable) {
                ((Disposable) actor).dispose();
            }
        }

        super.clearChildren();
    }

    public <T extends Actor> Cell<T> add(T actor) {
        return super.add(actor).expand().fill();
    }
}
