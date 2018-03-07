package retrogdx.games.earth2140.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Wd {
    private SmartByteBuffer buffer;

    public Wd(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        int numFiles = this.buffer.readInt();

        if (numFiles == 0) {
            while (this.buffer.position() < 0x400) {
                int fileStartOffset = this.buffer.readInt();
                int fileEndOffset = this.buffer.readInt();
                this.buffer.position(this.buffer.position() - 4);

                files.put(files.size() + ".SMP", this.buffer.slice(fileStartOffset + 0x400, fileEndOffset - fileStartOffset));

                if (fileEndOffset + 0x400 == this.buffer.capacity()) {
                    break;
                }
            }
        } else {
            int fileNamesOffset = this.buffer.position() + numFiles * 24 + 4;

            for (int i = 0; i < numFiles; i++) {
                int offset = this.buffer.readInt();
                int length = this.buffer.readInt();
                this.buffer.readInt(); // 0
                this.buffer.readInt(); // 0
                int unk = this.buffer.readInt(); // TODO this contains a value or can be 0
                int fileNameOffset = this.buffer.readInt();

                this.buffer.block(fileNamesOffset + fileNameOffset, (blockBuffer) -> {
                    files.put(blockBuffer.readString().toUpperCase(), this.buffer.slice(offset, length));
                });

            }
        }

        return files;
    }
}
