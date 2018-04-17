package retrogdx.games.warcraft2.readers;

import retrogdx.games.warcraft2.Algorythms;
import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

public class Mpq {
    private SmartByteBuffer buffer;

    public Mpq(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        this.buffer.readString(4); // magic
        this.buffer.readInt(); // headerSize
        this.buffer.readInt(); // archiveSize
        this.buffer.readShort(); // formatVersion
        int sectorSize = 512 * (1 << this.buffer.readShort());
        int hashTableOffset = this.buffer.readInt();
        int blockTableOffset = this.buffer.readInt();
        int hashTableEntries = this.buffer.readInt();
        int blockTableEntries = this.buffer.readInt();

        SmartByteBuffer hashBuffer = Algorythms.decrypt(this.buffer.slice(hashTableOffset, hashTableEntries * 16), "(hash table)");
        hashBuffer.order(ByteOrder.LITTLE_ENDIAN);

        SmartByteBuffer blockBuffer = Algorythms.decrypt(this.buffer.slice(blockTableOffset, blockTableEntries * 16), "(block table)");
        hashBuffer.order(ByteOrder.LITTLE_ENDIAN);

        for (int i = 0; i < hashTableEntries; i++) {
            int filePathHashA = hashBuffer.readInt();
            int filePathHashB = hashBuffer.readInt();
            hashBuffer.readShort(); // language
            hashBuffer.readShort(); // platform
            int fileBlockIndex = hashBuffer.readInt();

            if (fileBlockIndex == -1 || fileBlockIndex == -2) {
                continue;
            }

            // TODO implement usage of list and attribute files (0 and 1).
            String filename = String.format("%08X", filePathHashA) + String.format("%08X", filePathHashB);

            blockBuffer.position(fileBlockIndex * 16);

            int blockOffset = blockBuffer.readInt();
            int blockSize = blockBuffer.readInt();
            int fileSize = blockBuffer.readInt();
            int flags = blockBuffer.readInt();

            boolean isFile = (flags & 0x80000000) != 0;
            boolean isAdjustedKey = (flags & 0x00020000) != 0;
            boolean isEncrypted = (flags & 0x00010000) != 0;
            boolean isCompressed = (flags & 0x00000200) != 0;
            boolean isImploded = (flags & 0x00000100) != 0;

            if (!isFile) {
                continue;
            }

            if (isEncrypted) {
                // TODO
                continue;
            }

            if (isCompressed) {
                // TODO
                continue;
            }

            if (isImploded) {
                // TODO
                continue;
            }

            /*try {
                this.buffer.position(blockOffset);
                byte[] bytes = this.buffer.readBytes(fileSize);
                java.nio.file.Files.write(new java.io.File("_mpq/" + filename).toPath(), bytes);
            } catch (Exception exception) {
                exception.printStackTrace();
            }*/
        }

        return new HashMap<>();
    }
}
