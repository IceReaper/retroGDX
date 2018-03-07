package retrogdx.games.warwind.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Dat {
    private SmartByteBuffer buffer;

    public Dat(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        int numFiles = this.buffer.readInt();

        for (int i = 0; i < numFiles; i++) {
            int position = this.buffer.readInt();

            // This format uses no length, so use next file offset.
            int length = this.buffer.readInt() - position;
            this.buffer.position(this.buffer.position() - 4);

            // This format has no content type information, so use first byte of file to guess.
            files.put(i + ".LVL", this.buffer.slice(position, length));
        }

        return files;
    }
}
