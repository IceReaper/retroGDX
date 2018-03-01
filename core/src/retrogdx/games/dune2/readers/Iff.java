package retrogdx.games.dune2.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public abstract class Iff {
    public Iff(SmartByteBuffer buffer) {
        buffer.position(0);
        buffer.order(ByteOrder.BIG_ENDIAN);

        buffer.readString(4); // FORM
        int dataSize = buffer.readInt();
        this.read(buffer.slice(dataSize));
    }

    protected abstract void read(SmartByteBuffer buffer);
}
