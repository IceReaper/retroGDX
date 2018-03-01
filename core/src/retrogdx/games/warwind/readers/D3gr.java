package retrogdx.games.warwind.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;

public class D3gr {
    public class D3grImage {
        public int width;
        public int height;
        public byte[] pixels;
    }

    public D3grImage[] images;

    public D3gr(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        buffer.readString(4); // D3GR
        int unk1 = buffer.readInt(); // maybe flags
        int framesStart = buffer.readInt();
        int unk3 = buffer.readInt(); // 0, rare 28
        int unk4 = buffer.readInt(); // 0, rare 32, 44
        int unk5 = buffer.readInt(); // 0
        this.images = new D3grImage[buffer.readShort()];
        int unk6 = buffer.readShort();

        for (int i = 0; i < this.images.length; i++) {
            int frameOffset = buffer.readInt();
            int position = buffer.position();
            buffer.position(framesStart + frameOffset);
            this.images[i] = this.readFrame(buffer);
            buffer.position(position);
        }
    }

    private D3grImage readFrame(SmartByteBuffer buffer) {
        D3grImage image = new D3grImage();

        int unk1 = buffer.readInt();
        int unk2 = buffer.readInt();
        int unk3 = buffer.readShort();
        int unk4 = buffer.readShort();
        image.height = buffer.readShort();
        image.width = buffer.readShort();
        image.pixels = buffer.readBytes(image.width * image.height);

        return image;
    }
}
