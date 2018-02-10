package retrogdx.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SmartByteBuffer {

    private ByteBuffer byteBuffer;
    private int[] debug;
    private SmartByteBuffer parent;
    private int parentOffset;

    public static SmartByteBuffer wrap(byte[] bytes) {
        SmartByteBuffer instance = new SmartByteBuffer();
        instance.byteBuffer = ByteBuffer.wrap(bytes);
        return instance;
    }

    public void debug() {
        this.debug = new int[this.byteBuffer.capacity()];
    }

    private void debug(int position, int length, ByteType type) {
        if (this.parent != null) {
            this.parent.debug(this.parentOffset + position, length, type);
        }

        if (this.debug != null) {
            for (int i = 0; i < length; i++) {
                this.debug[position + i] = type.getColor().toIntBits();
            }
        }
    }

    // TODO refactor renderDebug to generate libgdx compatible texture.

    public byte readByte() {
        this.debug(this.byteBuffer.position(), 1, ByteType.BYTE);

        return this.byteBuffer.get();
    }

    public short readUByte() {
        this.debug(this.byteBuffer.position(), 1, ByteType.UBYTE);

        return (short) (this.byteBuffer.get() & 0xff);
    }

    public short readShort() {
        this.debug(this.byteBuffer.position(), 2, ByteType.SHORT);

        return this.byteBuffer.getShort();
    }

    public int readUShort() {
        this.debug(this.byteBuffer.position(), 2, ByteType.USHORT);

        return this.byteBuffer.getShort() & 0xffff;
    }

    public int readInt() {
        this.debug(this.byteBuffer.position(), 4, ByteType.INT);

        return this.byteBuffer.getInt();
    }

    public long readUInt() {
        this.debug(this.byteBuffer.position(), 4, ByteType.UINT);

        return this.byteBuffer.getInt() & 0xffffffffL;
    }

    public byte[] readBytes(int length) {
        this.debug(this.byteBuffer.position(), length, ByteType.BYTES);

        byte[] target = new byte[length];
        this.byteBuffer.get(target);
        return target;
    }

    public String readString() {
        StringBuilder string = new StringBuilder();

        while (true) {
            this.debug(this.byteBuffer.position(), 1, ByteType.STRING);

            byte readByte = this.byteBuffer.get();

            if (readByte == 0x00) {
                return string.toString();
            }

            string.append(new String(new byte[]{readByte}));
        }
    }

    public String readString(int length) {
        this.debug(this.byteBuffer.position(), length, ByteType.STRING);

        byte[] string = new byte[length];
        this.byteBuffer.get(string);

        while (length > 0 && string[length - 1] == 0x00) {
            length--;
        }

        return new String(string).substring(0, length);
    }

    public int capacity() {
        return this.byteBuffer.capacity();
    }

    public int position() {
        return this.byteBuffer.position();
    }

    public void position(int position) {
        this.byteBuffer.position(position);
    }

    public ByteOrder order() {
        return this.byteBuffer.order();
    }

    public void order(ByteOrder order) {
        this.byteBuffer.order(order);
    }

    public SmartByteBuffer slice(int length) {
        // TODO implement slice position, so we do not need to clone bytes!
        int offset = this.byteBuffer.position();
        byte[] data = new byte[length];
        this.byteBuffer.get(data);
        SmartByteBuffer instance = SmartByteBuffer.wrap(data);
        instance.parent = this;
        instance.parentOffset = offset;
        instance.order(this.order());
        return instance;
    }

    public SliceInfo getSliceInfo(int offset, int length) {
        return new SliceInfo(this, offset, length);
    }
}
