package retrogdx.games.earth2140.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.earth2140.readers.DatImage;
import retrogdx.generic.readers.Palette;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.ImagePreview;
import retrogdx.utils.SliceInfo;

import java.util.Map;

public class DatImageNode extends AssetFileNode {
    private SliceInfo sliceInfo;
    private String name;
    private Map<String, SliceInfo> palettes;

    public DatImageNode(Table previewArea, String name, SliceInfo sliceInfo, Map<String, SliceInfo> palettes) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
        this.name = name;
        this.palettes = palettes;
    }

    protected void showPreview() {
        DatImage dat = new DatImage(this.sliceInfo.slice());
        Palette palette = new Palette(this.palettes.get(this.name.substring(0, this.name.length() - 3) + "PAL").slice());

        Pixmap pixmap = new Pixmap(dat.width, dat.height, Pixmap.Format.RGBA8888);

        for (int y = 0; y < dat.height; y++) {
            for (int x = 0; x < dat.width; x++) {
                int index = dat.pixels[x + y * dat.width] & 0xff;
                pixmap.drawPixel(x, y, palette.colors[index]);
            }
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
