package retrogdx.games.bam.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Sqb {
    public String text = "";

    public Sqb(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        int texts = buffer.readInt();
        int start = buffer.position();

        StringBuilder text = new StringBuilder();

        for (int i = 0; i < texts; i++) {
            int textOffset = buffer.readInt();
            int id = buffer.readInt();

            int position = buffer.position();
            buffer.position(start + texts * 8 + textOffset);

            try {
                text.append(id + " :: " + buffer.readString() + "\n");
            } catch (Exception ignored) {
            }

            buffer.position(position);
        }

        this.text = text.toString();
        this.text = text.substring(0, this.text.length() - 1);
    }
}
