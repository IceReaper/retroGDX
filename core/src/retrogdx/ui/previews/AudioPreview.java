package retrogdx.ui.previews;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

import javax.sound.sampled.Clip;

public class AudioPreview extends VisTable implements Disposable {
    private Clip clip;

    public AudioPreview(Clip clip) {
        this.clip = clip;
        this.clip.start();

        VisTextButton action = new VisTextButton("Start / Stop");
        action.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (clip.isRunning()) {
                    clip.stop();
                } else {
                    clip.stop();
                    clip.setFramePosition(0);
                    clip.start();
                }
            }
        });
        this.add(action).expandX().row();

        // TODO implement progress slider, loop-checkbox, start/pause, stop
    }

    public void dispose() {
        if (this.clip.isRunning()) {
            this.clip.stop();
        }
    }
}
