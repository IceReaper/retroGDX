package retrogdx.games.swarmassault.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.swarmassault.readers.Sound;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.AudioPreview;
import retrogdx.utils.SmartByteBuffer;

public class SoundNode extends AssetFileNode {
    public SoundNode(String name, SmartByteBuffer buffer) {
        super(name, buffer);
    }

    public void showPreview(Table previewArea) {
        Sound sound = new Sound(this.buffer);

        previewArea.add(new AudioPreview(sound.clip));
    }
}
