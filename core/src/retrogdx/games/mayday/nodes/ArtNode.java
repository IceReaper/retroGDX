package retrogdx.games.mayday.nodes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.mayday.readers.Art;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.nodes.GameNode;
import retrogdx.ui.previews.ImageSetPreview;
import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

// TODO this should be a VitualFolderNode
public class ArtNode extends AssetFileNode {
    private FileHandle file;

    public ArtNode(FileHandle file, GameNode gameNode) {
        super(file.name(), SmartByteBuffer.wrap(file.readBytes()));
        this.file = file;
    }

    public void showPreview(Table previewArea) {
        Art art = new Art(this.buffer);

        Sprite[] sprites = new Sprite[art.frames.length];

        // TODO case insensitive.
        SmartByteBuffer paletteBuffer = SmartByteBuffer.wrap(this.file.sibling("DSpiel.pal").readBytes());
        paletteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        paletteBuffer.position(0x36);

        int[] palette = new int[256];

        for (int i = 0; i < palette.length - 1; i++) {
            palette[i] = (paletteBuffer.readInt() << 8) | 0xff;
        }

        for (int i = 0; i < art.frames.length; i++) {
            Art.ArtFrame frame = art.frames[i];
            Pixmap pixmap = new Pixmap(frame.width, frame.height, Pixmap.Format.RGBA8888);

            for (int y = 0; y < frame.height; y++) {
                for (int x = 0; x < frame.width; x++) {
                    int index = frame.pixels[x + y * frame.width] & 0xff;
                    pixmap.drawPixel(x, y, palette[index]);
                }
            }

            sprites[i] = new Sprite(new Texture(pixmap));
            sprites[i].setOrigin(frame.width / 2, frame.height / 2);
        }

        previewArea.add(new ImageSetPreview(sprites));
    }
}
