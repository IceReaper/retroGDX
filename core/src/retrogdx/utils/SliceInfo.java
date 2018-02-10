package retrogdx.utils;

public class SliceInfo {
    private SmartByteBuffer buffer;
    private int offset;
    private int length;

    public SliceInfo(SmartByteBuffer buffer, int offset, int length) {
        this.buffer = buffer;
        this.offset = offset;
        this.length = length;
    }

    public SmartByteBuffer slice() {
        int position = this.buffer.position();
        this.buffer.position(this.offset);
        SmartByteBuffer slice = this.buffer.slice(this.length);
        this.buffer.position(position);
        return slice;
    }
}
