package retrogdx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTree;
import org.reflections.Reflections;
import retrogdx.Game;

import java.util.Set;

import static com.badlogic.gdx.scenes.scene2d.ui.Tree.*;

public class GamesTree extends VisScrollPane {
    private Table previewArea;

    public GamesTree(Table previewArea) {
        super(new VisTree());

        this.previewArea = previewArea;
        this.setFadeScrollBars(false);

        VisTree tree = ((VisTree) this.getActor());
        tree.getSelection().setMultiple(false);

        tree.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Node selected = tree.getSelection().first();
                previewArea.clearChildren();

                if (!(selected instanceof AssetFileNode)) {
                    showPreview();
                } else {
                    ((AssetFileNode) selected).showPreview();
                }
            }
        });

        Set<Class<? extends Game>> gameClasses = new Reflections("retrogdx.games").getSubTypesOf(Game.class);

        for (FileHandle file : Gdx.files.internal("games").list()) {
            for (Class<? extends Game> gameClass : gameClasses) {
                try {
                    Game game = gameClass.getConstructor(Table.class).newInstance(this.previewArea);

                    if (game.parse(file)) {
                        ((VisTree) this.getActor()).add((Node) game);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }

        this.showPreview();
    }

    private void showPreview() {
        this.previewArea.add(new VisLabel("Readme..."));
    }
}
