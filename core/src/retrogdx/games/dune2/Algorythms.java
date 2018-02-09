package retrogdx.games.dune2;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class Algorythms {
    public static byte[] decompress(byte[] compressed80) {
        List<Byte> decompressed = new ArrayList<>();
        SmartByteBuffer buffer = SmartByteBuffer.wrap(compressed80);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        while (true) {
            int command = buffer.readUByte();

            if ((command >> 6) == 0b10) {
                // cmd 1: src => dst
                int count = command & 0b00111111;

                if (count == 0) {
                    break;
                }

                for (int i = 0; i < count; i++) {
                    decompressed.add(buffer.readByte());
                }

            } else if (command == 0b11111110) {
                // cmd 4: fill
                int count = buffer.readUShort();
                byte value = buffer.readByte();

                for (int i = 0; i < count; i++) {
                    decompressed.add(value);
                }
            } else {
                int count;
                int position;

                if ((command >> 7) == 0b0) {
                    // cmd 2: dst => dst
                    count = (command >> 4) + 3;
                    position = decompressed.size() - (((command & 0b00001111) << 8) + buffer.readUByte());
                } else {
                    if (command == 0b11111111) {
                        // cmd 5: dst => dst
                        count = buffer.readUShort();
                    } else {
                        // cmd 3: dst => dst
                        count = (command & 0b00111111) + 3;
                    }

                    position = buffer.readUShort();
                }

                for (int i = 0; i < count; i++) {
                    decompressed.add(decompressed.get(position + i));
                }
            }
        }

        byte[] result = new byte[decompressed.size()];

        for (int i = 0; i < decompressed.size(); i++) {
            result[i] = decompressed.get(i);
        }

        return result;
    }

    public static byte[] xor(byte[] original, byte[] changeset) {
        List<Byte> decompressed = new ArrayList<>();
        SmartByteBuffer buffer = SmartByteBuffer.wrap(changeset);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        while (buffer.position() < buffer.capacity()) {
            int command = buffer.readUByte();

            if (command == 0) {
                // cmd 6: xor with value
                int count = buffer.readUByte();
                byte value = buffer.readByte();

                for (int i = 0; i < count; i++) {
                    decompressed.add((byte) (original[decompressed.size()] ^ value));
                }

            } else if ((command >> 7) == 0b0) {
                // cmd 5: xor with changeset
                int count = command & 0b01111111;

                for (int i = 0; i < count; i++) {
                    decompressed.add((byte) (original[decompressed.size()] ^ buffer.readByte()));
                }

            } else if (command == 0b10000000) {
                int command2 = buffer.readUShort();

                if ((command2 >> 14) == 0b11) {
                    // cmd 4: xor with value
                    int count = command2 & 0b0011111111111111;
                    byte value = buffer.readByte();

                    for (int i = 0; i < count; i++) {
                        decompressed.add((byte) (original[decompressed.size()] ^ value));
                    }

                } else if ((command2 >> 14) == 0b10) {
                    // cmd 3: xor with changeset
                    int count = command2 & 0b0011111111111111;

                    for (int i = 0; i < count; i++) {
                        decompressed.add((byte) (original[decompressed.size()] ^ buffer.readByte()));
                    }
                } else {
                    // cmd 2: unchanged
                    int count = command2 & 0b0111111111111111;

                    for (int i = 0; i < count; i++) {
                        decompressed.add(original[decompressed.size()]);
                    }
                }

            } else {
                // cmd 1: unchanged
                int count = command & 0b01111111;

                for (int i = 0; i < count; i++) {
                    decompressed.add(original[decompressed.size()]);
                }
            }
        }

        byte[] result = new byte[decompressed.size()];

        for (int i = 0; i < decompressed.size(); i++) {
            result[i] = decompressed.get(i);
        }

        return result;
    }

    public static byte[] zeroFill(byte[] data) {
        List<Byte> decompressed = new ArrayList<>();
        SmartByteBuffer buffer = SmartByteBuffer.wrap(data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        while (buffer.position() < buffer.capacity()) {
            byte command = buffer.readByte();

            if (command == 0x00) {
                int count = buffer.readUByte();

                for (int i = 0; i < count; i++) {
                    decompressed.add((byte) 0x00);
                }
            } else {
                decompressed.add(command);
            }
        }

        byte[] result = new byte[decompressed.size()];

        for (int i = 0; i < decompressed.size(); i++) {
            result[i] = decompressed.get(i);
        }

        return result;
    }
}
