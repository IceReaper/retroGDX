package retrogdx.games.dune2.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Tbl {
    public byte[] map;

    public Tbl(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        // TODO what is this exactly?
        this.map = buffer.readBytes(256);
    }
}
