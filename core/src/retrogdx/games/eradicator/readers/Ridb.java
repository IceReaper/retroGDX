package retrogdx.games.eradicator.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Ridb {
    private SmartByteBuffer buffer;

    public Ridb(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        this.buffer.readString(4);; // RIDB
        int entriesAmount = this.buffer.readInt();
        int entriesOffset = this.buffer.readInt();

        this.buffer.position(entriesOffset);

        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        for (int i = 0; i < entriesAmount; i++) {
            String fileName = this.buffer.readString(12);
            int fileOffset = this.buffer.readInt();
            int fileSize = this.buffer.readInt();

            if (fileSize > 0) {
                files.put(fileName, this.buffer.slice(fileOffset, fileSize));
            }
        }

        return files;
    }
}
