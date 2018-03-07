package retrogdx.games.warwind.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Res {
    private SmartByteBuffer buffer;

    public Res(SmartByteBuffer buffer) {
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
            SmartByteBuffer fileBuffer = this.buffer.slice(position, length);
            int test = fileBuffer.readUShort();
            fileBuffer.position(0);

            switch (test) {
                case 0x3344:
                    files.put(i + ".D3GR", fileBuffer);
                    break;
                case 0x4952:
                    files.put(i + ".wav", fileBuffer);
                    break;
                case 0x0a0d:
                    // Nothing here - removed file.
                    break;
                default:
                    files.put(i + ".PAL", fileBuffer);
                    break;
            }
        }

        return files;
    }
}
