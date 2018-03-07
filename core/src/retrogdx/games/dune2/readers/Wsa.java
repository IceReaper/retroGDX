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
    private byte[] prevFrame;

    public Wsa(SmartByteBuffer buffer) {
        this(buffer, null);
    }

    public Wsa(SmartByteBuffer buffer, byte[] prevFrameParam) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        int numFrames = buffer.readUShort() + 1;
        this.width = buffer.readUShort();
        this.height = buffer.readUShort();
        int unk = buffer.readInt(); // TODO libwestwood states, this is the animation speed?

        List<byte[]> frames = new ArrayList<>();

        if (prevFrameParam == null) {
            this.prevFrame = new byte[this.width * this.height];
        } else {
            this.prevFrame = prevFrameParam;
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

            buffer.block(offset, (blockBuffer) -> {
                prevFrame = Algorythms.xor(prevFrame, Algorythms.decompress(blockBuffer.readBytes(blockBuffer.capacity() - offset)));
                frames.add(prevFrame);
            });
        }

        this.frames = new byte[frames.size()][this.width * this.height];

        for (int i = 0; i < frames.size(); i++) {
            this.frames[i] = frames.get(i);
        }
    }
}
