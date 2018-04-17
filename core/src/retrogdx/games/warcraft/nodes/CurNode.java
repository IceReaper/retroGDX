package retrogdx.games.warcraft.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.warcraft.readers.Cur;
import retrogdx.games.warcraft.readers.Pal;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class CurNode extends AssetFileNode {
    private Map<String, Pal> palettes;

    public CurNode(String name, SmartByteBuffer buffer, Map<String, Pal> palettes) {
        super(name, buffer);
        this.palettes = palettes;
    }

    public void showPreview(Table previewArea) {
        Cur cur = new Cur(this.buffer);

        Pixmap pixmap = new Pixmap(cur.width, cur.height, Pixmap.Format.RGBA8888);

        for (int y = 0; y < cur.height; y++) {
            for (int x = 0; x < cur.width; x++) {
                int index = cur.pixels[x + y * cur.width] & 0xff;
                pixmap.drawPixel(x, y, this.palettes.get("262.PAL").colors[index]);
            }
        }

        previewArea.add(new ImagePreview(pixmap));
    }
}
