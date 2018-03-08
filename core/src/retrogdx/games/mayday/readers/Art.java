package retrogdx.games.mayday.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Art {
    private SmartByteBuffer buffer;

    public Art(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        // TODO what the hell. this file seems to have no index?! containts images 8bpp indexed, uncompressed.

        return files;
    }
}
