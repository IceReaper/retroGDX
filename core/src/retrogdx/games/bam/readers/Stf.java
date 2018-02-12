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
        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        String[] fileTypes = new String[]{null, "ANI", null, "HMP", "wav", "PAL", null, "FNT", "SQB", null, null, null, "BNK", null, null, null, null, null, "TLB", "MIF"};

        while (this.buffer.position() < this.buffer.capacity()) {
            int id = this.buffer.readUShort();
            int compressionType = this.buffer.readInt();
            int uncompressedSize = this.buffer.readInt();
            int compressedSize = this.buffer.readInt();
            int fileType = this.buffer.readShort();
            int headerEntries = this.buffer.readUShort();
            int headerSize = this.buffer.readInt();
            int unk1 = this.buffer.readShort(); // 0
            int unk2 = this.buffer.readShort(); // 0
            int unk3 = this.buffer.readShort(); // 0xfefe

            if (headerSize == 0 && compressedSize == 0 && fileType == 5) {
                // This format seems a little broken!
                compressedSize = 256 * 3;
            }

            files.put(id + "." + fileTypes[fileType], this.buffer.slice(this.buffer.position() + headerSize, compressedSize));
            this.buffer.position(this.buffer.position() + headerSize + compressedSize);
        }

        return files;
    }
}
