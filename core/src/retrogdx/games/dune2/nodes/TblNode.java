package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.readers.Tbl;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.ImagePreview;
import retrogdx.utils.SmartByteBuffer.SliceInfo;

public class TblNode extends AssetFileNode {
    private SliceInfo sliceInfo;

    public TblNode(Table previewArea, String name, SliceInfo sliceInfo) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
    }

    protected void showPreview() {
        Tbl tbl = new Tbl(this.sliceInfo.slice());

        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);

        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                int grey = tbl.map[x + y * 16] & 0xff;
                pixmap.drawPixel(x, y, (grey << 24) | (grey << 16) | (grey << 8) | 0xff);
            }
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
