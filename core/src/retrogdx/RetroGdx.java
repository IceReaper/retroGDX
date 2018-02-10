package retrogdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import retrogdx.ui.GamesTree;

public class RetroGdx extends ApplicationAdapter {
    private Stage stage;

    public void create() {
        VisUI.load();

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);

        VisTable root = new VisTable();
        root.setFillParent(true);

        PreviewArea previewArea = new PreviewArea();
        previewArea.debug();
        GamesTree gamesTree = new GamesTree(previewArea);

        root.add(gamesTree).width(320).expandY().fillY();
        root.add(previewArea).expand().fill();
        this.stage.addActor(root);

        // TODO we need generic preview classes for:
        // 1. Spritesheet (multiple animations)
        // 2. Audio (plain sound)
        // 3. Video (animation + aligned audio)
        // TODO and special solutions for additional file types.
    }

    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(delta);
        this.stage.draw();
    }

    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
        this.stage.getRoot().setSize(width, height);
    }

    public void dispose() {
        VisUI.dispose();
        this.stage.dispose();
    }
}
