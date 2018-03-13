package retrogdx.games.warcraft.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Spr {
    public class SprFrame {
        public byte[] pixels;
        public int width;
        public int height;
        public int originX;
        public int originY;
    }

    public SprFrame[] frames;
    public int width;
    public int height;

    public Spr(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        this.frames = new SprFrame[buffer.readShort()];
        this.width = buffer.readUByte();
        this.height = buffer.readUByte();

        for (int i = 0; i < this.frames.length; i++) {
            this.frames[i] = this.readFrame(buffer);
        }
    }

    private SprFrame readFrame(SmartByteBuffer buffer) {
        SprFrame frame = new SprFrame();

        frame.originX = -buffer.readUByte();
        frame.originY = -buffer.readUByte();
        frame.width = buffer.readUByte();
        frame.height = buffer.readUByte();

        buffer.block(buffer.readInt(), (blockBuffer) -> {
            frame.pixels = blockBuffer.readBytes(frame.width * frame.height);
        });

        return frame;
    }
}
