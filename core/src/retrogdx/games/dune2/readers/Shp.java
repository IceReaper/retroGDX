package retrogdx.games.dune2.readers;

import com.badlogic.gdx.utils.Array;
import retrogdx.games.dune2.Algorythms;
import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Shp {
    public class ShpImage {
        public int width;
        public int height;
        public byte[] pixels;
    }

    public ShpImage[] images;

    public Shp(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        int numFrames = buffer.readUShort() + 1;

        boolean useInt = (buffer.readInt() >> 16) == 0x00;
        buffer.position(buffer.position() - 4);

        Array<ShpImage> images = new Array<>();

        for (int i = 0; i < numFrames; i++) {
            int offset = (useInt ? buffer.readInt() + 2 : buffer.readShort());

            if (offset == buffer.capacity()) {
                // Not a frame, pointer to file end
                continue;
            }

            int position = buffer.position();
            buffer.position(offset);
            images.add(this.readImage(buffer));
            buffer.position(position);
        }

        this.images = new ShpImage[images.size];

        for (int i = 0; i < images.size; i++) {
            this.images[i] = images.get(i);
        }
    }

    private ShpImage readImage(SmartByteBuffer buffer) {
        ShpImage image = new ShpImage();

        int flags = buffer.readShort();
        int slices = buffer.readUByte();
        image.width = buffer.readUShort();
        image.height = buffer.readUByte();
        int dataLeft = buffer.readUShort() - 10;
        int dataSize = buffer.readUShort();

        byte[] table;

        // TODO what is this table?!
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
