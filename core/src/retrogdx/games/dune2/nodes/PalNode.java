package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.readers.Pal;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.ImagePreview;
import retrogdx.utils.SmartByteBuffer.SliceInfo;

public class PalNode extends AssetFileNode {
    private SliceInfo sliceInfo;

    public PalNode(Table previewArea, String name, SliceInfo sliceInfo) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
    }

    protected void showPreview() {
        Pal pal = new Pal(this.sliceInfo.slice());

        int[] colors = pal.getColors();

        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);

        for (int i = 0; i < 256; i++) {
            pixmap.drawPixel(i % 16, i / 16, colors[i]);
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
