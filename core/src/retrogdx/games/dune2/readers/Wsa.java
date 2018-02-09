package retrogdx.games.dune2.readers;

import retrogdx.games.dune2.Algorythms;
import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class Wsa {
    public byte[][] frames;
    public int width;
    public int height;
    public int animationSpeed;

    public Wsa(SmartByteBuffer buffer) {
        this(buffer, null);
    }

    public Wsa(SmartByteBuffer buffer, byte[] prevFrame) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        int numFrames = buffer.readUShort() + 1;
        this.width = buffer.readUShort();
        this.height = buffer.readUShort();
        this.animationSpeed = buffer.readInt();

        List<byte[]> frames = new ArrayList<>();

        if (prevFrame == null) {
            prevFrame = new byte[this.width * this.height];
        }

        for (int i = 0; i < numFrames; i++) {
            int offset = buffer.readInt();

            if (offset == 0) {
                // Not a frame, next frame has to xor on last frame of previous animation.
                continue;
            }

            if (offset == buffer.capacity()) {
                // Not a frame, pointer to next animation.
                continue;
            }

            int position = buffer.position();
            buffer.position(offset);
            prevFrame = this.readFrame(buffer.readBytes(buffer.capacity() - offset), prevFrame);
            frames.add(prevFrame);
            buffer.position(position);
        }

        this.frames = new byte[frames.size()][this.width * this.height];

        for (int i = 0; i < frames.size(); i++) {
            this.frames[i] = frames.get(i);
        }
    }

    private byte[] readFrame(byte[] compressed80, byte[] prevFrame) {
        byte[] compressed40 = Algorythms.decompress(compressed80);
        return Algorythms.xor(prevFrame, compressed40);
    }
}
