package retrogdx.games.warwind.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.warwind.readers.D3gr;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.previews.ImageSetPreview;
import retrogdx.utils.SmartByteBuffer;

public class D3grNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;

    public D3grNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
    }

    protected void showPreview() {
        D3gr d3gr = new D3gr(this.smartByteBuffer);

        Pixmap[] pixmaps = new Pixmap[d3gr.images.length];

        for (int i = 0; i < d3gr.images.length; i++) {
            D3gr.D3grImage image = d3gr.images[i];
            pixmaps[i] = new Pixmap(image.width, image.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < image.height; y++) {
                for (int x = 0; x < image.width; x++) {
                    int index = image.pixels[x + y * image.width] & 0xff;
                    pixmaps[i].drawPixel(x, y, (index << 24) | (index << 16) | (index << 8) | 0xff);
                }
            }
        }

        this.previewArea.add(new ImageSetPreview(pixmaps));
    }
}
