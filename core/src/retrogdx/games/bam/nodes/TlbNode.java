package retrogdx.games.bam.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.bam.readers.Tlb;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.ImageSetPreview;
import retrogdx.utils.SmartByteBuffer;

public class TlbNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;

    public TlbNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
    }

    protected void showPreview() {
        Tlb tlb = new Tlb(this.smartByteBuffer);

        Pixmap[] pixmaps = new Pixmap[tlb.tiles.length];

        for (int i = 0; i < tlb.tiles.length; i++) {
            pixmaps[i] = new Pixmap(tlb.width, tlb.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < tlb.height; y++) {
                for (int x = 0; x < tlb.width; x++) {
                    int index = tlb.tiles[i].pixels[x + y * tlb.width] & 0xff;
                    pixmaps[i].drawPixel(x, y, (index << 24) | (index << 16) | (index << 8) | 0xff);
                }
            }
        }

        this.previewArea.add(new ImageSetPreview(pixmaps));
    }
}
