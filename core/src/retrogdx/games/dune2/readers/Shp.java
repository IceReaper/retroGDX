package retrogdx.games.dune2.readers;

import retrogdx.games.dune2.Algorythms;
import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class Shp {
    public class ShpFrame {
        public byte[] pixels;
        public int width;
        public int height;
    }

    public ShpFrame[] frames;

    public Shp(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        int numFrames = buffer.readUShort() + 1;

        boolean useInt = (buffer.readInt() >> 16) == 0x00;
        buffer.position(buffer.position() - 4);

        List<ShpFrame> frames = new ArrayList<>();

        for (int i = 0; i < numFrames; i++) {
            int offset = (useInt ? buffer.readInt() + 2 : buffer.readShort());

            if (offset == buffer.capacity()) {
                continue;
            }

            buffer.block(offset, (blockBuffer) -> {
                frames.add(this.readImage(blockBuffer));
            });
        }

        this.frames = new ShpFrame[frames.size()];

        for (int i = 0; i < frames.size(); i++) {
            this.frames[i] = frames.get(i);
        }
    }

    private ShpFrame readImage(SmartByteBuffer buffer) {
        ShpFrame image = new ShpFrame();

        int flags = buffer.readShort();
        buffer.readUByte(); // slices
        image.width = buffer.readUShort();
        image.height = buffer.readUByte();
        int dataLeft = buffer.readUShort() - 10;
        buffer.readUShort(); // dataSize

        byte[] table;

        if ((flags & 0b001) != 0) {
            int n = (flags & 0b100) != 0 ? buffer.readUByte() : 16;
            table = new byte[n];

            for (int i = 0; i < n; i++) {
                table[i] = buffer.readByte();
            }

            dataLeft -= n;
        } else {
            table = new byte[256];

            for (int i = 0; i < 256; i++) {
                table[i] = (byte) i;
            }

            table[1] = 0x7f;
            table[2] = 0x7e;
            table[3] = 0x7d;
            table[4] = 0x7c;
        }

        byte[] data = buffer.readBytes(dataLeft);

        if ((flags & 0b010) == 0) {
            data = Algorythms.decompress(data);
        }

        data = Algorythms.zeroFill(data);

        for (int j = 0; j < data.length; j++) {
            data[j] = table[data[j] & 0xff];
        }

        image.pixels = data;

        return image;
    }
}
