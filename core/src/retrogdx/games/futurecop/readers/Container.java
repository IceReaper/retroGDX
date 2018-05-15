package retrogdx.games.futurecop.readers;

import retrogdx.utils.SmartByteBuffer;

import java.io.ByteArrayOutputStream;
import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Container {
    private SmartByteBuffer buffer;
    private File currentFile;
    private Map<String, SmartByteBuffer> files;
    private ByteArrayOutputStream music;

    public Container(SmartByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<String, SmartByteBuffer> getFiles() {
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.buffer.position(0);

        this.files = new LinkedHashMap<>();

        while (this.buffer.position() < this.buffer.capacity()) {
            String chunkType = new StringBuilder(this.buffer.readString(4)).reverse().toString();

            switch (chunkType) {
                case "CTRL":
                    this.readContainerHeader();
                    break;
                case "SHOC":
                case "SWVR":
                case "MSIC":
                    this.readChunk();
                    break;
                case "FILL":
                    if (this.buffer.position() % 0x1000 != 0) {
                        this.buffer.position(this.buffer.readInt() + this.buffer.position() - 0x08);
                    }

                    break;
                default:
                    System.out.println(chunkType + " " + Integer.toHexString(this.buffer.position()));
                    break;
            }
        }

        if (this.music != null) {
            this.files.put(this.currentFile.name + ".msx", SmartByteBuffer.wrap(this.music.toByteArray()));
        }
        return this.files;
    }

    private void readContainerHeader() {
        int chunkSize = this.buffer.readInt();
        this.buffer.readBytes(chunkSize - 8); // TODO There may be a SYNC chunk in here, or unknown data...
    }

    private void readChunk() {
        int chunkSize = this.buffer.readInt();
        this.buffer.readInt(); // TODO always 0?
        this.buffer.readInt(); // TODO always 0?

        String chunkType = new StringBuilder(this.buffer.readString(4)).reverse().toString();

        switch (chunkType) {
            case "SHDR":
                this.readFileHeader(chunkSize - 0x3c);
                break;
            case "SDAT":
                this.readFileData(chunkSize);
                break;
            case "MSIC":
                this.readMusicData(chunkSize);
                break;
            case "FILE":
                this.readFileName();
                break;
            default:
                System.out.println(chunkType + " " + Integer.toHexString(this.buffer.position()));
                break;
        }
    }

    private void readFileHeader(int additionalBytes) {
        if (this.currentFile == null) {
            this.currentFile = new File();
            this.currentFile.name = String.valueOf(this.buffer.position() - 4);
        }

        this.buffer.readInt(); // TODO UNK
        String fileType = new StringBuilder(this.buffer.readString(4)).reverse().toString();
        this.buffer.readInt(); // TODO UNK
        this.currentFile.buffer = SmartByteBuffer.allocate(this.buffer.readInt());
        this.buffer.readInt(); // TODO UNK
        this.buffer.readInt(); // TODO UNK
        this.buffer.readInt(); // TODO UNK
        this.buffer.readInt(); // TODO UNK
        this.buffer.readInt(); // TODO UNK
        this.buffer.readInt(); // TODO UNK

        if (fileType.startsWith("C")) {
            fileType = fileType.substring(1);
        }

        this.currentFile.name += "." + fileType;

        if (additionalBytes > 0) {
            this.buffer.readBytes(additionalBytes); // TODO what is this?
        }
    }

    private void readFileData(int chunkSize) {
        this.currentFile.buffer.writeBytes(this.buffer.readBytes(chunkSize - 0x14));

        if (this.currentFile.buffer.position() == this.currentFile.buffer.capacity()) {
            this.files.put(this.currentFile.name, this.currentFile.buffer);
            this.currentFile = null;
        }
    }

    private void readFileName() {
        this.currentFile = new File();
        this.currentFile.name = this.buffer.readString(16).split("\u0000")[0];
    }

    private void readMusicData(int chunkSize) {
        if (this.music == null) {
            this.music = new ByteArrayOutputStream();
        }

        this.buffer.readShort(); // TODO unk
        this.buffer.readShort(); // TODO unk index?
        this.buffer.readInt(); // TODO unk

        try {
            this.music.write(this.buffer.readBytes(chunkSize - 0x1c));
        } catch (Exception ignored) {
        }
    }

    private class File {
        public String name;
        public SmartByteBuffer buffer;
    }
}
