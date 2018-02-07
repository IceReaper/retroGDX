package retrogdx.games.dune2.readers;

import com.badlogic.gdx.utils.Array;
import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Wsa {
    public byte[][] frames;
    public int width;
    public int height;
    public int animationSpeed;

    public Wsa(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        int numFrames = buffer.readUShort() + 1;
        this.width = buffer.readUShort();
        this.height = buffer.readUShort();
        this.animationSpeed = buffer.readInt();
        System.out.println(this.animationSpeed);

        this.frames = new byte[numFrames][this.width * this.height];

        for (int i = 0; i < numFrames; i++) {
            int offset = buffer.readInt();
            int position = buffer.position();
            buffer.position(offset);
            try {
                this.readFrame(buffer.readBytes(buffer.capacity() - offset), i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            buffer.position(position);
        }
    }

    private void readFrame(byte[] compressed80, int frameIndex) {
        byte[] compressed40 = this.decompress80(compressed80);
        byte[] prevFrame = frameIndex == 0 ? new byte[this.width * this.height] : this.frames[frameIndex - 1];
        this.frames[frameIndex] = this.decompress40(prevFrame, compressed40);
    }

    private byte[] decompress80(byte[] compressed80) {
        Array<Byte> decompressed = new Array<>();
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
                    position = decompressed.size - (((command & 0b00001111) << 8) + buffer.readUByte());
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

        byte[] result = new byte[decompressed.size];

        for (int i = 0; i < decompressed.size; i++) {
            result[i] = decompressed.get(i);
        }

        return result;
    }

    private byte[] decompress40(byte[] original, byte[] changeset) {
        Array<Byte> decompressed = new Array<>();
        SmartByteBuffer buffer = SmartByteBuffer.wrap(changeset);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        while (buffer.position() < buffer.capacity()) {
            int command = buffer.readUByte();

            if (command == 0) {
                // cmd 6: xor with value
                int count = buffer.readUByte();
                byte value = buffer.readByte();

                for (int i = 0; i < count; i++) {
                    decompressed.add((byte) (original[decompressed.size] ^ value));
                }

            } else if ((command >> 7) == 0b0) {
                // cmd 5: xor with changeset
                int count = command & 0b01111111;

                for (int i = 0; i < count; i++) {
                    decompressed.add((byte) (original[decompressed.size] ^ buffer.readByte()));
                }

            } else if (command == 0b10000000) {
                int command2 = buffer.readUShort();

                if ((command2 >> 14) == 0b11) {
                    // cmd 4: xor with value
                    int count = command2 & 0b0011111111111111;
                    byte value = buffer.readByte();

                    for (int i = 0; i < count; i++) {
                        decompressed.add((byte) (original[decompressed.size] ^ value));
                    }

                } else if ((command2 >> 14) == 0b10) {
                    // cmd 3: xor with changeset
                    int count = command2 & 0b0011111111111111;

                    for (int i = 0; i < count; i++) {
                        decompressed.add((byte) (original[decompressed.size] ^ buffer.readByte()));
                    }
                } else {
                    // cmd 2: unchanged
                    int count = command2 & 0b0111111111111111;

                    for (int i = 0; i < count; i++) {
                        decompressed.add(original[decompressed.size]);
                    }
                }

            } else {
                // cmd 1: unchanged
                int count = command & 0b01111111;

                for (int i = 0; i < count; i++) {
                    decompressed.add(original[decompressed.size]);
                }
            }
        }

        byte[] result = new byte[decompressed.size];

        for (int i = 0; i < decompressed.size; i++) {
            result[i] = decompressed.get(i);
        }

        return result;
    }
}
