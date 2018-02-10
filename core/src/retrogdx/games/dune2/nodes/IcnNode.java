package retrogdx.games.dune2.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.dune2.Dune2;
import retrogdx.games.dune2.readers.Icn;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.ImagePreview;
import retrogdx.utils.SliceInfo;

public class IcnNode extends AssetFileNode {
    private SliceInfo sliceInfo;
    private String name;

    public IcnNode(Table previewArea, String name, SliceInfo sliceInfo) {
        super(previewArea, name);

        this.sliceInfo = sliceInfo;
        this.name = name;
    }

    protected void showPreview() {
        Icn icn = new Icn(this.sliceInfo.slice());
        int totalX = (int) Math.ceil(Math.sqrt(icn.tiles.length));
        int totalY = (int) Math.ceil(icn.tiles.length / (float) totalX);

        Pixmap pixmap = new Pixmap(totalX * icn.width, totalY * icn.height, Pixmap.Format.RGBA8888);
        int[] palette = Dune2.PALETTE;

        for (int ty = 0; ty < totalX; ty++) {
            for (int tx = 0; tx < totalY; tx++) {
                int tileIndex = tx + ty * totalX;

                if (tileIndex >= icn.tiles.length) {
                    break;
                }

                for (int y = 0; y < icn.height; y++) {
                    for (int x = 0; x < icn.width; x++) {
                        int index = icn.tiles[tileIndex][x + y * icn.width] & 0xff;
                        pixmap.drawPixel(tx * icn.width + x, ty * icn.height + y, palette[index]);
                    }
                }
            }
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
