package retrogdx.games.swarmassault.readers;

import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Ani {
    private SmartByteBuffer buffer;

    public Ani(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, String[]> getAnimations() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        Map<String, String[]> animations = new LinkedHashMap<>();

        int numImages = this.buffer.readInt();

        for (int i = 0; i < numImages; i++) {
            String name1 = this.buffer.readString(32);
            String name2 = this.buffer.readString(32);
            int unk1 = this.buffer.readInt();
            int unk2 = this.buffer.readInt();
            int unk3 = this.buffer.readInt();
            int unk4 = this.buffer.readInt();
            int unk5 = this.buffer.readInt();

            int numUnkB = this.buffer.readInt();
            int numPoints = this.buffer.readInt();

            for (int j = 0; j < numUnkB; j++) {
                String type1 = this.buffer.readString(32);
                String type2 = this.buffer.readString(32);
                int unkB1 = this.buffer.readInt();
                int unkB2 = this.buffer.readInt();
                int unkB3 = this.buffer.readInt();
                int unkB4 = this.buffer.readInt();
            }

            for (int j = 0; j < numPoints; j++) {
                String type1 = this.buffer.readString(32);
                String type2 = this.buffer.readString(32);
                int unkB1 = this.buffer.readInt();
                int unkB2 = this.buffer.readInt();
            }
        }

        int numAnimations = this.buffer.readInt();

        for (int i = 0; i < numAnimations; i++) {
            String name = this.buffer.readString(32);
            int numFrames = this.buffer.readInt();

            List<String> frames = new ArrayList<>();

            for (int j = 0; j < numFrames; j++) {
                String frameName = this.buffer.readString(32);
                int unk1 = this.buffer.readInt();
                int unk2 = this.buffer.readInt();
                int numScripts = this.buffer.readInt();

                for (int k = 0; k < numScripts; k++) {
                    String script = this.buffer.readString(32);
                }

                frames.add(frameName);
            }

            animations.put(name, frames.toArray(new String[0]));
        }

        return animations;
    }
}
