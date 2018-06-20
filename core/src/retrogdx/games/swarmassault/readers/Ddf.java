package retrogdx.games.swarmassault.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Ddf {
    private SmartByteBuffer buffer;

    public Ddf(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        int numFiles = this.buffer.readInt();
        this.buffer.readShort(); // 768
        this.buffer.readShort(); // 256

        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        files.put("palette.pal", this.buffer.slice(256 * 4));

        for (int i = 0; i < numFiles; i++) {
            String fileName = this.buffer.readString(32);
            SmartByteBuffer clone = this.buffer.slice(0, this.buffer.capacity());
            clone.position(this.buffer.position());
            this.buffer.position(this.buffer.position() + 20);
            files.put(fileName, clone);
        }

        return files;
    }
}
