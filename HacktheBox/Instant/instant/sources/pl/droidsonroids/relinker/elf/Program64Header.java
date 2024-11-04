package pl.droidsonroids.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import pl.droidsonroids.relinker.elf.Elf;

/* loaded from: classes.dex */
public class Program64Header extends Elf.ProgramHeader {
    public Program64Header(ElfParser elfParser, Elf.Header header, long j) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j2 = header.phoff + (j * header.phentsize);
        this.type = elfParser.readWord(allocate, j2);
        this.offset = elfParser.readLong(allocate, 8 + j2);
        this.vaddr = elfParser.readLong(allocate, 16 + j2);
        this.memsz = elfParser.readLong(allocate, j2 + 40);
    }
}
