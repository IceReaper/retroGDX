package retrogdx.games.dominion.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Rdf {
    private SmartByteBuffer buffer;

    public Rdf(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        // TODO possibly 6 bytes less and stuff before numFiles
        this.buffer.readString(198); // magic
        short numFiles = this.buffer.readShort();
        int fileTableOffset = this.buffer.readInt();

        this.buffer.position(fileTableOffset);

        for (int i = 0; i < numFiles; i++) {
            String fileName = this.buffer.readString(118).trim();
            this.buffer.readShort(); // fileIndex
            int fileLength = this.buffer.readInt();
            int fileOffset = this.buffer.readInt();

            files.put(fileName, this.buffer.slice(fileOffset, fileLength));
        }

        return files;
    }
}
