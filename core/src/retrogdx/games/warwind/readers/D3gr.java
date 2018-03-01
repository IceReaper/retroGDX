package retrogdx.games.warwind.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class D3gr {
    public class Frame {
        public int width;
        public int height;
        public byte[] pixels;
    }

    public Frame[] frames;

    public D3gr(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        String magic = buffer.readString(4);
        int unk1 = buffer.readInt(); // maybe flags
        int framesStart = buffer.readInt();
        int unk3 = buffer.readInt(); // 0, rare 28
        int unk4 = buffer.readInt(); // 0, rare 32, 44
        int unk5 = buffer.readInt(); // 0
        this.frames = new Frame[buffer.readShort()];
        int unk6 = buffer.readShort();

        for (int i = 0; i < this.frames.length; i++) {
            int frameOffset = buffer.readInt();
            int position = buffer.position();
            buffer.position(framesStart + frameOffset);
            this.frames[i] = this.readFrame(buffer);
            buffer.position(position);
        }
    }

    private Frame readFrame(SmartByteBuffer buffer) {
        Frame frame = new Frame();

        int unk1 = buffer.readInt();
        int unk2 = buffer.readInt();
        int unk3 = buffer.readShort();
        int unk4 = buffer.readShort();
        frame.height = buffer.readShort();
        frame.width = buffer.readShort();
        frame.pixels = buffer.readBytes(frame.width * frame.height);

        return frame;
    }
}
