package retrogdx.games.futurecop.readers;

import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import retrogdx.utils.SmartByteBuffer;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Obj {
    private class Vertex {
        public int x;
        public int y;
        public int z;
        public int unused;

        public Vertex(SmartByteBuffer buffer) {
            this.x = buffer.readShort();
            this.y = buffer.readShort();
            this.z = buffer.readShort();
            this.unused = buffer.readShort();
        }
    }

    private class Normal {
        public int x;
        public int y;
        public int z;
        public int unused;

        public Normal(SmartByteBuffer buffer) {
            this.x = buffer.readShort();
            this.y = buffer.readShort();
            this.z = buffer.readShort();
            this.unused = buffer.readShort();
        }
    }

    private class Quad {
        public int unk1; // TODO
        public int unk2; // TODO
        public int unk3; // TODO
        public int vertices;
        public short texture;
        public short v1;
        public short v2;
        public short v3;
        public short v4;
        public short n1;
        public short n2;
        public short n3;
        public short n4;

        public Quad(SmartByteBuffer buffer) {
            short tmp1 = buffer.readUByte();
            short tmp2 = buffer.readUByte();
            this.unk1 = tmp1 & 0x0f;
            this.unk2 = tmp1 & 0xff;
            this.vertices = tmp2 & 0x0f;// TODO this seems to work for 3 or 4. But there are variants, where this is 7. Looks like a quad, with ABAB instead of ABCD...?
            this.unk3 = tmp2 & 0xff;
            this.texture = (short) (buffer.readUShort() >> 4);
            this.v1 = buffer.readUByte();
            this.v2 = buffer.readUByte();
            this.v3 = buffer.readUByte();
            this.v4 = buffer.readUByte();
            this.n1 = buffer.readUByte();
            this.n2 = buffer.readUByte();
            this.n3 = buffer.readUByte();
            this.n4 = buffer.readUByte();
        }
    }

    private class Texture {
        short unk1; // TODO
        short unk2; // TODO equal values
        short unk3; // TODO equal values
        short unk4; // TODO equal values
        short u1;
        short v1;
        short u2;
        short v2;
        short u3;
        short v3;
        short u4;
        short v4;
        short bitmap;
        short unk6; // 0x00
        short unk7; // 0x00
        short unk8; // 0x00

        public Texture(SmartByteBuffer buffer) {
            this.unk1 = buffer.readUByte();
            this.unk2 = buffer.readUByte();
            this.unk3 = buffer.readUByte();
            this.unk4 = buffer.readUByte();
            this.u1 = buffer.readUByte();
            this.v1 = buffer.readUByte();
            this.u2 = buffer.readUByte();
            this.v2 = buffer.readUByte();
            this.u3 = buffer.readUByte();
            this.v3 = buffer.readUByte();
            this.u4 = buffer.readUByte();
            this.v4 = buffer.readUByte();
            this.bitmap = buffer.readUByte();
            this.unk6 = buffer.readUByte();
            this.unk7 = buffer.readUByte();
            this.unk8 = buffer.readUByte();
        }
    }

    public Model model;

    public Obj(SmartByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(0);

        List<Vertex> vertices = new ArrayList<>();
        List<Normal> normals = new ArrayList<>();
        List<Quad> quads = new ArrayList<>();
        List<Texture> textures = new ArrayList<>();

        while (buffer.position() < buffer.capacity()) {
            String chunkType = new StringBuilder(buffer.readString(4)).reverse().toString();
            int chunkSize = buffer.readInt() - 12;
            buffer.readInt(); // TODO 0x01 - subMesh?

            System.out.println(chunkType);

            switch (chunkType) {
                case "4DGI": // General Information
                    // TODO has a fixed size of 0x3c
                    // TODO most bytes seem to have a fixed value. Values seem to be shorts.
                    buffer.readBytes(chunkSize);
                    break;

                case "4DVL": // Vertex List
                    int numVertices = buffer.readInt();

                    for (int i = 0; i < numVertices; i++) {
                        vertices.add(new Vertex(buffer));
                    }

                    break;

                case "4DNL": // Normal List
                    int numNormals = buffer.readInt();

                    for (int i = 0; i < numNormals; i++) {
                        normals.add(new Normal(buffer));
                    }

                    break;

                case "3DQL": // Quad List
                    int numQuads = buffer.readInt();

                    for (int i = 0; i < numQuads; i++) {
                        quads.add(new Quad(buffer));
                    }

                    break;

                case "3DTL": // Texture list
                    for (int i = 0; i < chunkSize / 16; i++) {
                        textures.add(new Texture(buffer));
                    }

                    break;

                case "3DRL":
                    // TODO R... List ? Find one which has more than 0 entries, so we can guess what this might be!
                    // TODO possibly this is numRL and a short per RL
                    buffer.readBytes(chunkSize);
                    break;

                case "3DBB": // TODO B... B... frames? Might be some kind of texture animation. B could be related to Bitmap?
                    // TODO Base Bones - initial position of bones? (Later hotspot?)
                    int numFrames = buffer.readInt();

                    for (int i = 0; i < numFrames; i++) {
                        buffer.readShort(); // TODO positive or negative values. Values seem to almost count 1,2,3... -1,-2,-3...
                        buffer.readShort(); // TODO
                        buffer.readShort(); // TODO
                        buffer.readShort(); // TODO
                        buffer.readShort(); // TODO
                        buffer.readShort(); // TODO
                        buffer.readShort(); // TODO
                        buffer.readShort(); // TODO
                    }

                    break;

                case "3DHY": // TODO H... Y... unk? I cannot see a pattern here yet...
                    buffer.readBytes(chunkSize);
                    break;

                case "3DMI": // TODO M... I... Matrix Information? I cannot see a pattern here yet...
                    // TODO
                    buffer.readBytes(chunkSize);
                    break;

                case "3DHS": // TODO H... S... unk? Hot Spot ? Could be some kind of per-bone-per-frame positions.
                    // TODO the above 0x01 int is 0x02 here?
                    // TODO maybe its 8 bytes, not 16... :D

                    for (int i = 0; i < chunkSize / 16; i++) {
                        buffer.readShort(); // x
                        buffer.readShort(); // y
                        buffer.readShort(); // z
                        buffer.readShort(); // 0x00
                        buffer.readShort(); // rx
                        buffer.readShort(); // ry
                        buffer.readShort(); // rz
                        buffer.readShort(); // 0x00
                    }

                    break;

                case "AnmD":
                    // TODO likely Animation Data
                    buffer.readBytes(chunkSize);
                    break;

                case "3DRF": // Removed File
                    buffer.readString(4); // chunkType ?
                    buffer.readInt(); // broken chunkSize: 1 ?
                    buffer.readInt(); // 1
                    break;

                case "3DTA":
                    // TODO when is this used?
                    // TODO this could be Texture Animation!
                    buffer.readBytes(chunkSize);
                    break;

                case "3DAL":
                    // TODO when is this used?
                    // TODO Animation list...?
                    buffer.readBytes(chunkSize);
                    break;
            }
        }

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();

        Map<Integer, MeshPartBuilder> meshPartBuilders = new HashMap<>();
        Map<Integer, Integer> vertexIndices = new HashMap<>();

        try {
            for (int i = 0; i < quads.size(); i++) {
                Quad quad = quads.get(i);
                int bitmap = textures.get(quad.texture).bitmap;

                if (!meshPartBuilders.containsKey(bitmap)) {
                    meshPartBuilders.put(bitmap, modelBuilder.part(String.valueOf(bitmap), GL30.GL_TRIANGLES,
                            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates,
                            new Material(String.valueOf(bitmap))
                    ));
                    vertexIndices.put(bitmap, 0);
                }

                MeshPartBuilder meshPartBuilder = meshPartBuilders.get(bitmap);
                int vertexIndex = vertexIndices.get(bitmap);

                meshPartBuilder.vertex(
                        vertices.get(quad.v1).x, vertices.get(quad.v1).y, vertices.get(quad.v1).z,
                         normals.get(quad.n1).x,  normals.get(quad.n1).y,  normals.get(quad.n1).z,
                        textures.get(quad.texture).u1 / 255f, textures.get(quad.texture).v1 / 255f
                );

                meshPartBuilder.vertex(
                        vertices.get(quad.v2).x, vertices.get(quad.v2).y, vertices.get(quad.v2).z,
                         normals.get(quad.n2).x,  normals.get(quad.n2).y,  normals.get(quad.n2).z,
                        textures.get(quad.texture).u2 / 255f, textures.get(quad.texture).v2 / 255f
                );

                meshPartBuilder.vertex(
                        vertices.get(quad.v3).x, vertices.get(quad.v3).y, vertices.get(quad.v3).z,
                         normals.get(quad.n3).x,  normals.get(quad.n3).y,  normals.get(quad.n3).z,
                        textures.get(quad.texture).u3 / 255f, textures.get(quad.texture).v3 / 255f
                );

                meshPartBuilder.index((short) vertexIndex, (short) (vertexIndex + 1), (short) (vertexIndex + 2));

                if (quad.vertices == 4) {
                    meshPartBuilder.vertex(
                            vertices.get(quad.v4).x, vertices.get(quad.v4).y, vertices.get(quad.v4).z,
                             normals.get(quad.n4).x,  normals.get(quad.n4).y,  normals.get(quad.n4).z,
                            textures.get(quad.texture).u4 / 255f, textures.get(quad.texture).v4 / 255f
                    );

                    meshPartBuilder.index((short) vertexIndex, (short) (vertexIndex + 2), (short) (vertexIndex + 3));
                }

                vertexIndices.put(bitmap, vertexIndex + quad.vertices);
            }
        } catch (Exception exception) {
            // TODO some obj seem to not hold actual models...? A single line for example is also possible, or missing textures or missing normals...
            // TODO what are these things?
            exception.printStackTrace();
        }

        this.model = modelBuilder.end();
    }
}
