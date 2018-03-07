package retrogdx.games.earth2140.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class DatMap {
    private String name;

    public DatMap(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        // TODO we are still missing meta data: map size, starting money, objectives?, etc...

        this.name = buffer.readString(31).trim();

        buffer.readBytes(128 * 128 * 2); // TODO most likely resources
        buffer.readBytes(128 * 128); // TODO most likely tiles

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 256; j++) { // Most likely entities.
                int index = buffer.readUByte();
                byte unk1 = buffer.readByte();
                int x = buffer.readUShort(); // unverified, but very likely
                int y = buffer.readUShort(); // unverified, but very likely
                int type = buffer.readUShort(); // unverified, but very likely
                int unk2 = buffer.readUShort(); // 0 or 1
                int unk3 = buffer.readUShort();
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 256; j++) { // Most likely map objects.
                int index = buffer.readUByte();
                byte unk1 = buffer.readByte();
                int x = buffer.readUShort(); // unverified, but very likely
                int y = buffer.readUShort(); // unverified, but very likely
                int type = buffer.readUShort(); // unverified, but very likely
                int unk2 = buffer.readUShort();
            }
        }

        buffer.readBytes(15516); // TODO unk
        buffer.readBytes(128 * 128); // TODO most likely attributes (cliff, water, etc)

        for (int j = 0; j < 256; j++) {
            buffer.readInt(); // TODO unk
        }

        buffer.readBytes(992); // TODO unk
        buffer.readString(); // class CTerrain
    }
}
