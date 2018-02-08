package retrogdx.games.dune2.readers;

import retrogdx.utils.SmartByteBuffer;

public class Tbl {
    public byte[] map;

    public Tbl(SmartByteBuffer buffer) {
        buffer.position(0);
        this.map = buffer.readBytes(256);
    }
}
