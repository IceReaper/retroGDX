package retrogdx.games.warwind.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Lvl {
    public Lvl(SmartByteBuffer buffer) {
        buffer.position(0);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        String magic = buffer.readString(6); // WARMAP or EMPTY.

        if (magic.equals("EMPTY.")) {
            buffer.readByte(); // 0x0a
            return;
        }

        int version = buffer.readInt(); // 0x01

        // TODO UNPACKED / NETWORK
        /*String title = buffer.readString(64);
        String description = buffer.readString(256);

        // TODO PACKED / SCENARIO
        for (int i = 0; i < 9; i++) {
            int length = buffer.readInt();
            int count = buffer.readInt();
            byte[] data = buffer.readBytes(count * length);
        }*/
    }
}
