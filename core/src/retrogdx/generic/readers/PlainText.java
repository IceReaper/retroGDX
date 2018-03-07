package retrogdx.generic.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class PlainText {
    public String text;

    public PlainText(SmartByteBuffer buffer, String encoding) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        this.text = buffer.readString(buffer.capacity(), encoding);
    }
}
