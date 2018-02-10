package retrogdx.generic.readers;

import retrogdx.utils.SmartByteBuffer;

public class PlainText {
    public String text;

    public PlainText(SmartByteBuffer buffer) {
        buffer.position(0);
        this.text = buffer.readString(buffer.capacity());
    }
}
