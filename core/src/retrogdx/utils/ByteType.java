package retrogdx.utils;

import com.badlogic.gdx.graphics.Color;

public enum ByteType {
    UNUSED(new Color(0, 0, 0, 1)),

    BYTE(new Color(1, 0, 0, 1)),
    UBYTE(new Color(1, .5f, .5f, 1)),
    SHORT(new Color(0, 1, 0, 1)),
    USHORT(new Color(.5f, 1, .5f, 1)),
    INT(new Color(0, 0, 1, 1)),
    UINT(new Color(.5f, .5f, 1, 1)),

    STRING(new Color(1, 1, 0, 1)),
    BYTES(new Color(.5f, .5f, .5f, 1));

    private final Color color;

    ByteType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
