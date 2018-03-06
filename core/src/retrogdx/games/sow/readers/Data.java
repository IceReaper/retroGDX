package retrogdx.games.sow.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Data {
    private SmartByteBuffer dataBuffer;
    private SmartByteBuffer infoBuffer;

    public Data(SmartByteBuffer dataBuffer, SmartByteBuffer infoBuffer) {
        this.dataBuffer = dataBuffer;
        this.infoBuffer = infoBuffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        this.dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
        this.infoBuffer.order(ByteOrder.LITTLE_ENDIAN);
        this.infoBuffer.position(0);

        int magic = this.infoBuffer.readInt(); // 0x01010101
        int entries = this.infoBuffer.readInt();

        for (int i = 0; i < entries; i++) {
            int temp;
            String name = "";

            while ((temp = this.infoBuffer.readUByte()) != 0x00) {
                name += (char) (temp - 0xa);
            }

            int offset = this.infoBuffer.readInt();
            int length = this.infoBuffer.readInt();
            files.put(name, this.dataBuffer.slice(offset, length));
        }

        return files;
    }
}
