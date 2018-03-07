package retrogdx.games.bam.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Stf {
    private SmartByteBuffer buffer;

    public Stf(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        String[] fileTypes = new String[]{null, "ANI", null, "HMP", "wav", "PAL", null, "FNT", "SQB", null, null, null, "BNK", null, null, null, null, null, "TLB", "MIF"};

        while (this.buffer.position() < this.buffer.capacity()) {
            int id = this.buffer.readUShort();
            this.buffer.readInt(); // compressionType
            this.buffer.readInt(); // uncompressedSize
            int compressedSize = this.buffer.readInt();
            int fileType = this.buffer.readShort();
            this.buffer.readUShort(); // headerEntries
            int headerSize = this.buffer.readInt();
            this.buffer.readShort(); // 0
            this.buffer.readShort(); // 0
            this.buffer.readShort(); // 0xfefe

            if (compressedSize == 0 && fileType == 5) {
                // This format seems a little broken!
                // TODO find out if this is correct, or this palettes are empty.
                compressedSize = 256 * 3;
            }

            files.put(id + "." + fileTypes[fileType], this.buffer.slice(headerSize + compressedSize));
        }

        return files;
    }
}
