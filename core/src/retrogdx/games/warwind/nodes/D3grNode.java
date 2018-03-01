package retrogdx.games.warwind.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.warwind.readers.D3gr;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.ImagePreview;
import retrogdx.utils.SmartByteBuffer;

public class D3grNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;

    public D3grNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
    }

    protected void showPreview() {
        D3gr d3gr = new D3gr(this.smartByteBuffer);

        int width = 0;
        int height = 0;

        for (D3gr.Frame frame : d3gr.frames) {
            width = Math.max(frame.width, width);
            height = Math.max(frame.height, height);
        }

        int totalX = (int) Math.ceil(Math.sqrt(d3gr.frames.length));
        int totalY = (int) Math.ceil(d3gr.frames.length / (float) totalX);

        Pixmap pixmap = new Pixmap(totalX * width, totalY * height, Pixmap.Format.RGBA8888);

        for (int ty = 0; ty < totalX; ty++) {
            for (int tx = 0; tx < totalY; tx++) {
                int tileIndex = tx + ty * totalX;

                if (tileIndex >= d3gr.frames.length) {
                    break;
                }

                D3gr.Frame frame = d3gr.frames[tileIndex];

                for (int y = 0; y < frame.height; y++) {
                    for (int x = 0; x < frame.width; x++) {
                        int index = frame.pixels[x + y * frame.width] & 0xff;
                        pixmap.drawPixel(tx * width + x, ty * height + y, (index << 24) | (index << 16) | (index << 8) | 0xff);
                    }
                }
            }
        }

        this.previewArea.add(new ImagePreview(pixmap));
    }
}
