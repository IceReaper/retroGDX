package retrogdx.games.bam.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

public class Sqb {
    public Map<Integer, String> texts = new HashMap<>();

    public Sqb(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        int texts = buffer.readInt();

        for (int i = 0; i < texts; i++) {
            int textOffset = buffer.readInt();
            int id = buffer.readInt();

            buffer.block(4 + texts * 8 + textOffset, (SmartByteBuffer blockBuffer) -> {
                this.texts.put(id, blockBuffer.readString("CP850"));
            });
        }
    }
}
