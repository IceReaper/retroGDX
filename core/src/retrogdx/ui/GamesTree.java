package retrogdx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTree;
import retrogdx.Game;
import retrogdx.games.dune2.Dune2;

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
                AssetNode selected = (AssetNode) tree.getSelection().first();
                previewArea.clearChildren();

                if (selected == null) {
                    showPreview();
                } else {
                    selected.showPreview();
                }
            }
        });

        for (FileHandle file : Gdx.files.internal("games").list()) {
            Game game = new Dune2(previewArea);

            if (game.parse(file)) {
                ((VisTree) this.getActor()).add((Node) game);
            }
        }

        this.showPreview();
    }

    private void showPreview() {
        this.previewArea.add(new VisLabel("Readme..."));
    }
}
