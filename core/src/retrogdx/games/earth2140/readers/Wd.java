package retrogdx.games.earth2140.readers;

import retrogdx.utils.SmartByteBuffer;
import retrogdx.utils.SliceInfo;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Wd {
    private SmartByteBuffer buffer;

    public Wd(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, SliceInfo> getFiles() {
        Map<String, SliceInfo> files = new LinkedHashMap<>();

        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        int numFiles = this.buffer.readInt();

        if (numFiles == 0) {
            while (this.buffer.position() < 0x400) {
                int fileStartOffset = this.buffer.readInt();
                int fileEndOffset = this.buffer.readInt();

                files.put(files.size() + ".SMP", this.buffer.getSliceInfo(fileStartOffset + 0x400, fileEndOffset - fileStartOffset));

                if (fileEndOffset == 0x00) {
                    break;
                }
            }
        } else {
            int fileNamesOffset = this.buffer.position() + numFiles * 24 + 4;

            for (int i = 0; i < numFiles; i++) {
                int offset = this.buffer.readInt();
                int length = this.buffer.readInt();
                int unk1 = this.buffer.readInt();
                int unk2 = this.buffer.readInt();
                int unk3 = this.buffer.readInt();
                int fileNameOffset = this.buffer.readInt();

                int position = this.buffer.position();
                this.buffer.position(fileNamesOffset + fileNameOffset);
                String fileName = this.buffer.readString().toUpperCase();
                this.buffer.position(position);

                files.put(fileName, this.buffer.getSliceInfo(offset, length));
            }
        }

        return files;
    }
}
