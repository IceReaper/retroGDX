package retrogdx.games.bam.nodes;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import retrogdx.games.bam.readers.Sqb;
import retrogdx.ui.AssetFileNode;
import retrogdx.ui.TextPreview;
import retrogdx.utils.SmartByteBuffer;

public class SqbNode extends AssetFileNode {
    private SmartByteBuffer smartByteBuffer;

    public SqbNode(Table previewArea, String name, SmartByteBuffer smartByteBuffer) {
        super(previewArea, name);

        this.smartByteBuffer = smartByteBuffer;
    }

    protected void showPreview() {
        Sqb sql = new Sqb(this.smartByteBuffer);
        this.previewArea.add(new TextPreview(sql.text));
    }
}
