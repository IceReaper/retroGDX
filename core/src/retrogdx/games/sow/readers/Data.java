package retrogdx.games.sow.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Data {
    private SmartByteBuffer dataBuffer;
    private SmartByteBuffer indexBuffer;

    public Data(SmartByteBuffer dataBuffer, SmartByteBuffer indexBuffer) {
        this.dataBuffer = dataBuffer;
        this.indexBuffer = indexBuffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
        this.indexBuffer.order(ByteOrder.LITTLE_ENDIAN);
        this.indexBuffer.position(0);

        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        this.indexBuffer.readInt(); // 0x01010101
        int entries = this.indexBuffer.readInt();

        for (int i = 0; i < entries; i++) {
            int temp;
            String name = "";

            while ((temp = this.indexBuffer.readUByte()) != 0x00) {
                name += (char) (temp - 0xa);
            }

            int offset = this.indexBuffer.readInt();
            int length = this.indexBuffer.readInt();
            files.put(name, this.dataBuffer.slice(offset, length));
        }

        return files;
    }
}
