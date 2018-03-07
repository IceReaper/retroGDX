package retrogdx.games.warwind.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class D3gr {
    public class D3grFrame {
        public byte[] pixels;
        public int width;
        public int height;
    }

    public D3grFrame[] frames;
    private int i;

    public D3gr(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        buffer.readString(4); // D3GR
        int unk1 = buffer.readInt(); // TODO maybe flags
        int framesStart = buffer.readInt();
        int unk2 = buffer.readInt(); // TODO
        int unk3 = buffer.readInt(); // TODO
        int unk4 = buffer.readInt(); // TODO
        this.frames = new D3grFrame[buffer.readShort()];
        int unk5 = buffer.readShort(); // TODO

        for (this.i = 0; this.i < this.frames.length; this.i++) {
            int frameOffset = buffer.readInt();

            buffer.block(framesStart + frameOffset, (blockBuffer) -> {
                this.frames[this.i] = this.readFrame(blockBuffer);
            });
        }
    }

    private D3grFrame readFrame(SmartByteBuffer buffer) {
        D3grFrame frame = new D3grFrame();

        buffer.readInt(); // frameSize
        int unk1 = buffer.readInt(); // TODO paletteId ?
        int unk2 = buffer.readShort(); // TODO origin x?
        int unk3 = buffer.readShort(); // TODO origin y?
        frame.height = buffer.readShort();
        frame.width = buffer.readShort();
        frame.pixels = buffer.readBytes(frame.width * frame.height);

        return frame;
    }
}
