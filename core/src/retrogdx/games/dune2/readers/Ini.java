package retrogdx.games.dune2.readers;

import retrogdx.utils.SmartByteBuffer;

public class Ini {
    private SmartByteBuffer buffer;

    public Ini(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public String getText() {
        this.buffer.position(0);
        return this.buffer.readString(this.buffer.capacity());
    }
}
