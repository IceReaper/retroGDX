package retrogdx.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTree;
import org.reflections.Reflections;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;

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
                    ((AssetFileNode) selected).showPreview(previewArea);
                }
            }
        });

        Set<Class<? extends GameNode>> gameClasses = new Reflections("retrogdx.games").getSubTypesOf(GameNode.class);
        Map<String, GameNode> games = new HashMap<>();

        for (FileHandle file : Gdx.files.internal("games").list()) {
            for (Class<? extends GameNode> gameClass : gameClasses) {
                try {
                    GameNode gameNode = gameClass.newInstance();

                    if (gameNode.verifyExecutableExists(file)) {
                        games.put(((VisLabel) gameNode.getActor()).getText().toString(), gameNode);
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

    public static Array<Node> sort(Array<Tree.Node> nodes) {
        nodes.sort((a, b) -> ((VisLabel) a.getActor()).getText().toString().compareToIgnoreCase(((VisLabel) b.getActor()).getText().toString()));
        return nodes;
    }
}
