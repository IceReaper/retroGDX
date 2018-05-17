package retrogdx.games.futurecop.readers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;
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
        public int unk;

        public Vertex(short x, short y, short z, short unk) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.unk = unk;
        }
    }

    private class Normal {
        public int x;
        public int y;
        public int z;
        public int unk;

        public Normal(short x, short y, short z, short unk) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.unk = unk;
        }
    }

    private class Quad {
        public short unk1; // TODO
        public short vertices;
        public short texture;
        public short v1;
        public short v2;
        public short v3;
        public short v4;
        public short n1;
        public short n2;
        public short n3;
        public short n4;

        public Quad(short unk1, short vertices, short texture, short v1, short v2, short v3, short v4, short n1, short n2, short n3, short n4) {
            this.unk1 = unk1;
            this.vertices = vertices;
            this.texture = texture;
            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
            this.v4 = v4;
            this.n1 = n1;
            this.n2 = n2;
            this.n3 = n3;
            this.n4 = n4;
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

        public Texture(short unk1, short unk2, short unk3, short unk4, short u1, short v1, short u2, short v2, short u3, short v3, short u4, short v4, short bitmap, short unk6, short unk7, short unk8) {
            this.unk1 = unk1;
            this.unk2 = unk2;
            this.unk3 = unk3;
            this.unk4 = unk4;
            this.u1 = u1;
            this.v1 = v1;
            this.u2 = u2;
            this.v2 = v2;
            this.u3 = u3;
            this.v3 = v3;
            this.u4 = u4;
            this.v4 = v4;
            this.bitmap = bitmap;
            this.unk6 = unk6;
            this.unk7 = unk7;
            this.unk8 = unk8;
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
            int chunkSize = buffer.readInt() - 8;

            switch (chunkType) {
                case "4DGI": // General Information
                    // TODO
                    buffer.readBytes(chunkSize);
                    break;

                case "4DVL": // Vertex List
                    buffer.readInt(); // TODO 0x01
                    int numVertices = buffer.readInt();

                    for (int i = 0; i < numVertices; i++) {
                        vertices.add(new Vertex(
                                buffer.readShort(), buffer.readShort(), buffer.readShort(),
                                buffer.readShort()
                        ));
                    }

                    break;

                case "4DNL": // Normal List
                    buffer.readInt(); // TODO 0x01
                    int numNormals = buffer.readInt();

                    for (int i = 0; i < numNormals; i++) {
                        normals.add(new Normal(
                                buffer.readShort(), buffer.readShort(), buffer.readShort(),
                                buffer.readShort()
                        ));
                    }

                    break;

                case "3DQL": // Quad List
                    buffer.readInt(); // TODO 0x01
                    int numQuads = buffer.readInt();

                    for (int i = 0; i < numQuads; i++) {
                        quads.add(new Quad(
                                buffer.readUByte(),
                                buffer.readUByte(),
                                (short) (buffer.readUShort() >> 4),
                                buffer.readUByte(), buffer.readUByte(), buffer.readUByte(), buffer.readUByte(),
                                buffer.readUByte(), buffer.readUByte(), buffer.readUByte(), buffer.readUByte()
                        ));
                    }

                    break;

                case "3DTL": // Texture list
                    buffer.readInt(); // TODO 0x01 - maybe texture index?

                    for (int i = 0; i < chunkSize / 16; i++) {
                        textures.add(new Texture(
                                buffer.readUByte(), buffer.readUByte(), buffer.readUByte(), buffer.readUByte(),
                                buffer.readUByte(), buffer.readUByte(),
                                buffer.readUByte(), buffer.readUByte(),
                                buffer.readUByte(), buffer.readUByte(),
                                buffer.readUByte(), buffer.readUByte(),
                                buffer.readUByte(), buffer.readUByte(), buffer.readUByte(), buffer.readUByte()
                        ));
                    }

                    break;

                case "3DRF":
                    // TODO
                    buffer.readBytes(chunkSize);
                    break;

                case "3DRL":
                    // TODO
                    buffer.readBytes(chunkSize);
                    break;

                case "3DBB":
                    // TODO
                    buffer.readBytes(chunkSize);
                    break;

                case "3DHS":
                    // TODO
                    buffer.readBytes(chunkSize);
                    break;

                case "3DTA":
                    // TODO
                    buffer.readBytes(chunkSize);
                    break;

                case "3DHY":
                    // TODO
                    buffer.readBytes(chunkSize);
                    break;

                case "3DMI":
                    // TODO
                    buffer.readBytes(chunkSize);
                    break;

                case "3DAL":
                    // TODO
                    buffer.readBytes(chunkSize);
                    break;

                case "AnmD":
                    // TODO likely Animation Data
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
                        normals.get(quad.n1).x, normals.get(quad.n1).y, normals.get(quad.n1).z,
                        textures.get(quad.texture).u1 / 255f, textures.get(quad.texture).v1 / 255f
                );

                meshPartBuilder.vertex(
                        vertices.get(quad.v2).x, vertices.get(quad.v2).y, vertices.get(quad.v2).z,
                        normals.get(quad.n2).x, normals.get(quad.n2).y, normals.get(quad.n2).z,
                        textures.get(quad.texture).u2 / 255f, textures.get(quad.texture).v2 / 255f
                );

                meshPartBuilder.vertex(
                        vertices.get(quad.v3).x, vertices.get(quad.v3).y, vertices.get(quad.v3).z,
                        normals.get(quad.n3).x, normals.get(quad.n3).y, normals.get(quad.n3).z,
                        textures.get(quad.texture).u3 / 255f, textures.get(quad.texture).v3 / 255f
                );

                meshPartBuilder.index((short) vertexIndex, (short) (vertexIndex + 1), (short) (vertexIndex + 2));

                if (quad.vertices == 4) {
                    meshPartBuilder.vertex(
                            vertices.get(quad.v4).x, vertices.get(quad.v4).y, vertices.get(quad.v4).z,
                            normals.get(quad.n4).x, normals.get(quad.n4).y, normals.get(quad.n4).z,
                            textures.get(quad.texture).u4 / 255f, textures.get(quad.texture).v4 / 255f
                    );

                    meshPartBuilder.index((short) vertexIndex, (short) (vertexIndex + 2), (short) (vertexIndex + 3));
                }

                vertexIndices.put(bitmap, vertexIndex + quad.vertices);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.model = modelBuilder.end();
    }
}
