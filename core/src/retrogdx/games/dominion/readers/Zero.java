package retrogdx.games.dominion.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Zero {
    private SmartByteBuffer dataBuffer;
    private SmartByteBuffer indexBuffer;

    public Zero(SmartByteBuffer dataBuffer, SmartByteBuffer indexBuffer) {
        this.dataBuffer = dataBuffer;
        this.indexBuffer = indexBuffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
        this.indexBuffer.order(ByteOrder.LITTLE_ENDIAN);
        this.indexBuffer.position(0);

        Map<String, SmartByteBuffer> files = new LinkedHashMap<>();

        // TODO find out how to read the header part.
        this.indexBuffer.position(0x164);

        int[] offsets = new int[15];
        int[] lengths = new int[15];

        for (int i = 0; i < 15; i++) {
            offsets[i] = this.indexBuffer.readInt();
            lengths[i] = this.indexBuffer.readInt();
        }

        // 0: file offsets
        this.indexBuffer.position(offsets[0]);
        for (int i = 0; i < lengths[0] / 10; i++) {
            int type = this.indexBuffer.readShort();
            int offset = this.indexBuffer.readInt();
            int length = this.indexBuffer.readInt();

            switch (type) {
                case 1:
                    files.put(i + ".spr", this.dataBuffer.slice(offset, length));
                    break;

                case 11:
                    files.put(i + ".UNK", this.dataBuffer.slice(offset, length));
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
        this.indexBuffer.position(offsets[3]);
        // TODO identify me (171 entries)
        for (int i = 0; i < lengths[3] / 8; i++) {
            int unk1 = this.indexBuffer.readInt(); // ascending - offset?
            int unk2 = this.indexBuffer.readInt(); // may be -1
        }

        // 4: game core messages strings
        this.indexBuffer.position(offsets[4]);
        while (this.indexBuffer.position() < offsets[4] + lengths[4]) {
            this.indexBuffer.readString(); // gameMessage
        }

        // 5: unknown - maybe unit or animation tables?
        // TODO read me
        this.indexBuffer.position(offsets[5]);
        this.indexBuffer.readBytes(lengths[5]);

        // 6: palette
        this.indexBuffer.position(offsets[6]);

        // TODO 20 reserved colors - possibly faction, shadow, ... ?
        for (int i = 0; i < 236; i++) {
            // TODO this seems to be a palette?
            this.indexBuffer.readInt();
        }

        // 7: unused

        // 8: unused

        // 9: unused

        // 10: plugin files strings
        this.indexBuffer.position(offsets[10]);
        while (this.indexBuffer.position() < offsets[10] + lengths[10]) {
            this.indexBuffer.readString(); // pluginFile
        }

        // 11: plugin function names strings
        this.indexBuffer.position(offsets[11]);
        while (this.indexBuffer.position() < offsets[11] + lengths[11]) {
            this.indexBuffer.readString(); // pluginFunction
        }

        // 12: unknown - looks like flags 0,1,2,3,4
        this.indexBuffer.position(offsets[12]);
        // TODO identify me! (423 entries)
        for (int i = 0; i < lengths[12] / 4; i++) {
            int unk1 = this.indexBuffer.readInt();
        }

        // 13: unused

        // 14: game sources path string
        this.indexBuffer.position(offsets[14]);
        this.indexBuffer.readString(lengths[14]); // sourcePath

        return files;
    }
}
