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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;

public class GamesTree extends VisScrollPane {
    private Table previewArea;
    private Node lastSelected = null;

    public GamesTree(Table previewArea) {
        super(new VisTree());

        this.previewArea = previewArea;
        this.setFadeScrollBars(false);

        VisTree tree = ((VisTree) this.getActor());
        tree.getSelection().setMultiple(false);

        tree.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Node selected = tree.getSelection().first();

                if (lastSelected == selected) {
                    return;
                }

                lastSelected = selected;
                previewArea.clearChildren();

                if (!(selected instanceof AssetFileNode)) {
                    showPreview();
                } else {
                    ((AssetFileNode) selected).showPreview();
                }
            }
        });

        Set<Class<? extends Game>> gameClasses = new Reflections("retrogdx.games").getSubTypesOf(Game.class);
        Map<String, Game> games = new HashMap<>();

        for (FileHandle file : Gdx.files.internal("games").list()) {
            for (Class<? extends Game> gameClass : gameClasses) {
                try {
                    Game game = gameClass.getConstructor(Table.class).newInstance(this.previewArea);

                    if (game.parse(file)) {
                        games.put(((VisLabel) game.getActor()).getText().toString(), game);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }

        Map<String, Node> sorted = new TreeMap<>(games);

        for (String game : sorted.keySet()) {
            ((VisTree) this.getActor()).add(sorted.get(game));
        }

        this.showPreview();
    }

    private void showPreview() {
        this.previewArea.add(new VisLabel("Readme..."));
    }
}
