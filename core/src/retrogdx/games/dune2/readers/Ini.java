package retrogdx.games.dune2.readers;

import retrogdx.utils.SmartByteBuffer;

public class Ini {
    public String text;

    public Ini(SmartByteBuffer buffer) {
        buffer.position(0);
        this.text = buffer.readString(buffer.capacity());
    }
}
