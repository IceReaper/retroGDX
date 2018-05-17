package retrogdx.games.futurecop.nodes;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.futurecop.readers.Bmp;
import retrogdx.games.futurecop.readers.Obj;
import retrogdx.ui.nodes.AssetFileNode;
import retrogdx.ui.previews.ModelPreview;
import retrogdx.utils.SmartByteBuffer;

import java.util.List;

public class ObjNode extends AssetFileNode {
    private List<Bmp> textures;

    public ObjNode(String name, SmartByteBuffer buffer, List<Bmp> textures) {
        super(name, buffer);
        this.textures = textures;
    }

    public void showPreview(Table previewArea) {
        Obj obj = new Obj(this.buffer);

        obj.model.meshParts.forEach(meshPart -> {
            int bitmap = Integer.parseInt(meshPart.id);

            Pixmap pixmap = new Pixmap(256, 256, Pixmap.Format.RGBA8888);

            for (int y = 0; y < 256; y++) {
                for (int x = 0; x < 256; x++) {
                    pixmap.drawPixel(x, y, this.textures.get(bitmap).pixels[y * 256 + x]);
                }
            }

            obj.model.getMaterial(meshPart.id).set((TextureAttribute.createDiffuse(new Texture(pixmap))));
        });

        previewArea.add(new ModelPreview(obj.model));
    }
}
