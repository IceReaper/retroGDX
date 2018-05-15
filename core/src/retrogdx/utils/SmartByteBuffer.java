package retrogdx.utils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteOrder;

public class SmartByteBuffer {
    public interface Block {
        void body(SmartByteBuffer buffer);
    }

    private byte[] bytes;
    private int position = 0;
    private int capacity;
    private ByteOrder order;

    private SmartByteBuffer parent;
    private int parentPosition;

    public static SmartByteBuffer allocate(int capacity) {
        SmartByteBuffer instance = new SmartByteBuffer();
        instance.bytes = new byte[capacity];
        instance.capacity = capacity;
        return instance;
    }

    public static SmartByteBuffer wrap(byte[] bytes) {
        SmartByteBuffer instance = new SmartByteBuffer();
        instance.bytes = bytes;
        instance.capacity = bytes.length;
        return instance;
    }

    private byte readByte(int position) {
        if (position < 0 || position >= this.capacity) {
            throw new IndexOutOfBoundsException();
        }

        if (this.parent != null) {
            return this.parent.readByte(this.parentPosition + position);
        } else {
            return this.bytes[position];
        }
    }

    private void writeByte(byte value, int position) {
        if (position < 0 || position >= this.capacity) {
            throw new IndexOutOfBoundsException();
        }

        if (this.parent != null) {
            this.parent.writeByte(value, this.parentPosition + position);
        } else {
            this.bytes[position] = value;
        }
    }

    public byte readByte() {
        return this.readByte(this.position++);
    }

    public short readUByte() {
        return (short) (this.readByte() & 0xff);
    }

    public void writeByte(byte value) {
        this.writeByte(value, this.position++);
    }

    public short readShort() {
        short first = this.readUByte();
        short second = this.readUByte();
        return (short) (this.order == ByteOrder.LITTLE_ENDIAN ? first | (second << 8) : second | (first << 8));
    }

    public int readUShort() {
        return this.readShort() & 0xffff;
    }

    public void writeShort(short value) {
        this.writeByte((byte) (this.order == ByteOrder.LITTLE_ENDIAN ? value & 0xff : value >> 8));
        this.writeByte((byte) (this.order == ByteOrder.LITTLE_ENDIAN ? value >> 8 : value & 0xff));
    }

    public int readInt() {
        int first = this.readUShort();
        int second = this.readUShort();
        return this.order == ByteOrder.LITTLE_ENDIAN ? first | (second << 16) : second | (first << 16);
    }

    public long readUInt() {
        return this.readInt() & 0xffffffffL;
    }

    public void writeInt(int value) {
        this.writeShort((short) (this.order == ByteOrder.LITTLE_ENDIAN ? value & 0xffff : value >> 16));
        this.writeShort((short) (this.order == ByteOrder.LITTLE_ENDIAN ? value >> 16 : value & 0xffff));
    }

    public byte[] readBytes(int length) {
        byte[] target = new byte[length];

        for (int i = 0; i < length; i++) {
            target[i] = this.readByte();
        }

        return target;
    }

    public void writeBytes(byte[] values) {
        for (byte value : values) {
            this.writeByte(value);
        }
    }

    public String readString() {
        return this.readString("UTF-8");
    }

    public String readString(String encoding) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        while (true) {
            byte readByte = this.readByte();

            if (readByte == 0x00) {
                break;
            }

            byteArrayOutputStream.write(readByte);
        }

        try {
            return byteArrayOutputStream.toString(encoding);
        } catch (Exception ignored) {
        }

        return byteArrayOutputStream.toString();
    }

    public String readString(int length) {
        return this.readString(length, "UTF-8");
    }

    public String readString(int length, String encoding) {
        byte[] string = this.readBytes(length);

        while (length > 0 && string[length - 1] == 0x00) {
            length--;
        }

        try {
            return new String(string, encoding).substring(0, length);
        } catch (Exception ignored) {
        }

        return new String(string).substring(0, length);
    }

    public int capacity() {
        return this.capacity;
    }

    public int position() {
        return this.position;
    }

    public void position(int position) {
        this.position = position;
    }

    public ByteOrder order() {
        return this.order;
    }

    public void order(ByteOrder order) {
        this.order = order;
    }

    public SmartByteBuffer slice(int length) {
        SmartByteBuffer slice = this.slice(this.position, length);
        this.position += length;
        return slice;
    }

    public SmartByteBuffer slice(int position, int length) {
        if (position + length > this.capacity || length < 1) {
            throw new IndexOutOfBoundsException();
        }

        SmartByteBuffer slice = new SmartByteBuffer();
        slice.capacity = length;
        slice.order = this.order;
        slice.parent = this;
        slice.parentPosition = position;
        return slice;
    }

    public void block(int position, Block block) {
        SmartByteBuffer buffer = this.slice(0, this.capacity);
        buffer.position(position);
        block.body(buffer);
    }

    public int getParentPosition() {
        return this.parentPosition;
    }

    public byte[] unwrap() {
        return this.slice(0, this.capacity).readBytes(this.capacity);
    }
}
