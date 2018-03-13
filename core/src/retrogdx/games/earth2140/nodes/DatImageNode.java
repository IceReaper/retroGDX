package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.earth2140.readers.DatImage;
import retrogdx.games.earth2140.readers.Pal;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

import java.util.Map;

public class DatImageNode extends AssetFileNode {
    private String name;
    private Map<String, SmartByteBuffer> palettes;

    public DatImageNode(Table previewArea, String name, SmartByteBuffer buffer, Map<String, SmartByteBuffer> palettes) {
        super(previewArea, name, buffer);
        this.name = name;
        this.palettes = palettes;
    }

    protected void showPreview() {
        DatImage dat = new DatImage(this.buffer);

        Pal pal = new Pal(this.palettes.get(this.name.substring(0, this.name.length() - 3) + "PAL"));
        Pixmap pixmap = new Pixmap(dat.width, dat.height, Pixmap.Format.RGBA8888);

        for (int y = 0; y < dat.height; y++) {
            for (int x = 0; x < dat.width; x++) {
                int index = dat.pixels[x + y * dat.width] & 0xff;
                pixmap.drawPixel(x, y, pal.colors[index]);
            }
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
