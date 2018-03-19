package retrogdx.games.warcraft2;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Algorythms {
    private static int[][] cryptTable = new int[5][256];

    static {
        int seed = 0x00100001;

        for (int i = 0; i < Algorythms.cryptTable[0].length; i++) {
            for (int j = 0; j < Algorythms.cryptTable.length; j++) {
                seed = (seed * 125 + 3) % 0x2AAAAB;
                int temp1 = (seed & 0xFFFF);

                seed = (seed * 125 + 3) % 0x2AAAAB;
                int temp2 = (seed & 0xFFFF);

                Algorythms.cryptTable[j][i] = ((temp1 << 16) | temp2);
            }
        }
    }

    public static SmartByteBuffer decrypt(SmartByteBuffer encrypted, String key) {
        SmartByteBuffer decrypted = SmartByteBuffer.allocate(encrypted.capacity());

        encrypted.order(ByteOrder.LITTLE_ENDIAN);
        decrypted.order(ByteOrder.LITTLE_ENDIAN);

        int hashKey = Algorythms.hash(key, 3);
        int seed = 0xEEEEEEEE;

        for (int i = 0; i < encrypted.capacity() / 4; i++) {
            seed += Algorythms.cryptTable[4][hashKey & 0xff];
            int block = hashKey + seed;

            int in = encrypted.readInt();
            int out = in ^ block;
            decrypted.writeInt(out);

            seed += out + (seed << 5) + 3;
            hashKey = (~hashKey << 21) + 0x11111111 | hashKey >>> 11;
        }

        decrypted.position(0);

        return decrypted;
    }

    private static int hash(String key, int table) {
        byte[] keyBytes = key.toUpperCase().getBytes();
        int seed1 = 0x7FED7FED;
        int seed2 = 0xEEEEEEEE;

        for (byte keyByte : keyBytes) {
            seed1 = Algorythms.cryptTable[table][keyByte] ^ (seed1 + seed2);
            seed2 = Byte.toUnsignedInt(keyByte) + seed1 + seed2 + (seed2 << 5) + 3;
        }

        return seed1;
    }
}
