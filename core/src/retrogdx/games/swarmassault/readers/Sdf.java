package retrogdx.games.swarmassault.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Sdf {
    private SmartByteBuffer buffer;

    public Sdf(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        int numFiles = this.buffer.readInt();

        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        for (int i = 0; i < numFiles; i++) {
            String fileName = this.buffer.readString(64);
            SmartByteBuffer clone = this.buffer.slice(0, this.buffer.capacity());
            clone.position(this.buffer.position());
            this.buffer.position(this.buffer.position() + 28);
            files.put(fileName, clone);
        }

        return files;
    }
}
