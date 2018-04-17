package retrogdx.games.dominion.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Zero {
    private SmartByteBuffer imagesBuffer;
    private SmartByteBuffer buffer;

    public Zero(SmartByteBuffer buffer, SmartByteBuffer imagesBuffer) {
        this.buffer = buffer;
        this.imagesBuffer = imagesBuffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.imagesBuffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        // TODO find out how to read the header part.
        this.buffer.position(0x164);

        int[] offsets = new int[15];
        int[] lengths = new int[15];

        for (int i = 0; i < 15; i++) {
            offsets[i] = this.buffer.readInt();
            lengths[i] = this.buffer.readInt();
        }

        // 0: file offsets
        this.buffer.position(offsets[0]);
        for (int i = 0; i < lengths[0] / 10; i++) {
            int type = this.buffer.readShort();
            int offset = this.buffer.readInt();
            int length = this.buffer.readInt();

            switch (type) {
                case 1:
                    files.put(i + ".spr", this.imagesBuffer.slice(offset, length));
                    break;

                case 11:
                    files.put(i + ".UNK", this.imagesBuffer.slice(offset, length));
                    break;

                case 14:
                    // TODO 7 files, variable length
                    // TODO NOT in data container! - What / where are they?
                    break;

                case 15:
                    // TODO 24023 files, 16 length
                    // TODO NOT in data container! - What / where are they?
                    break;

                case 16:
                    // TODO 24 files, variable length
                    // TODO NOT in data container! - What / where are they?
                    break;

                case 17:
                    // TODO 5 files, 944 length
                    // TODO NOT in data container! - What / where are they?
                    break;

                case 18:
                    // TODO 13 files, 12x36 + 1x28 length
                    // TODO NOT in data container! - What / where are they?
                    break;

                case 19:
                    // TODO 485 files, variable length
                    // TODO NOT in data container! - What / where are they?
                    break;

                case 20:
                    // TODO 1289 files, variable length
                    // TODO NOT in data container! - What / where are they?
                    break;
            }
        }

        // 1: unused

        // 2: unused

        // 3: unknown
        this.buffer.position(offsets[3]);
        // TODO identify me (171 entries)
        for (int i = 0; i < lengths[3] / 8; i++) {
            int unk1 = this.buffer.readInt(); // ascending - offset?
            int unk2 = this.buffer.readInt(); // may be -1
        }

        // 4: game core messages strings
        this.buffer.position(offsets[4]);
        while (this.buffer.position() < offsets[4] + lengths[4]) {
            this.buffer.readString(); // gameMessage
        }

        // 5: unknown - maybe unit or animation tables?
        // TODO read me
        this.buffer.position(offsets[5]);
        this.buffer.readBytes(lengths[5]);

        // 6: unknown - maybe palette?
        this.buffer.position(offsets[6]);
        for (int i = 0; i < 236; i++) {
            this.buffer.readInt(); // TODO
        }

        // 7: unused

        // 8: unused

        // 9: unused

        // 10: plugin files strings
        this.buffer.position(offsets[10]);
        while (this.buffer.position() < offsets[10] + lengths[10]) {
            this.buffer.readString(); // pluginFile
        }

        // 11: plugin function names strings
        this.buffer.position(offsets[11]);
        while (this.buffer.position() < offsets[11] + lengths[11]) {
            this.buffer.readString(); // pluginFunction
        }

        // 12: unknown - looks like flags 0,1,2,3,4
        this.buffer.position(offsets[12]);
        // TODO identify me! (423 entries)
        for (int i = 0; i < lengths[12] / 4; i++) {
            int unk1 = this.buffer.readInt();
        }

        // 13: unused

        // 14: game sources path string
        this.buffer.position(offsets[14]);
        this.buffer.readString(lengths[14]); // sourcePath

        return files;
    }
}
