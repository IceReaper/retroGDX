package retrogdx.games.dune2.readers;

import retrogdx.games.dune2.Algorythms;
import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class Cps {
    public byte[] pixels;
    public Pal palette;

    public Cps(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        buffer.readShort(); // fileSize
        buffer.readShort(); // compressionFormat
        buffer.readUShort(); // imageSize
        buffer.readShort(); // 0
        short paletteSize = buffer.readShort();

        if (paletteSize == 768) {
            this.palette = new Pal(buffer.slice(paletteSize));
        }

        this.pixels = Algorythms.decompress(buffer.readBytes(buffer.capacity() - buffer.position()));
    }
}
