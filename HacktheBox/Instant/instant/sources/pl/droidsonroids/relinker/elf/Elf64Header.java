package pl.droidsonroids.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import pl.droidsonroids.relinker.elf.Elf;

/* loaded from: classes.dex */
public class Elf64Header extends Elf.Header {
    private final ElfParser parser;

    public Elf64Header(boolean z, ElfParser elfParser) throws IOException {
        this.bigEndian = z;
        this.parser = elfParser;
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(z ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.type = elfParser.readHalf(allocate, 16L);
        this.phoff = elfParser.readLong(allocate, 32L);
        this.shoff = elfParser.readLong(allocate, 40L);
        this.phentsize = elfParser.readHalf(allocate, 54L);
        this.phnum = elfParser.readHalf(allocate, 56L);
        this.shentsize = elfParser.readHalf(allocate, 58L);
        this.shnum = elfParser.readHalf(allocate, 60L);
        this.shstrndx = elfParser.readHalf(allocate, 62L);
    }

    @Override // pl.droidsonroids.relinker.elf.Elf.Header
    public Elf.SectionHeader getSectionHeader(int i) throws IOException {
        return new Section64Header(this.parser, this, i);
    }

    @Override // pl.droidsonroids.relinker.elf.Elf.Header
    public Elf.ProgramHeader getProgramHeader(long j) throws IOException {
        return new Program64Header(this.parser, this, j);
    }

    @Override // pl.droidsonroids.relinker.elf.Elf.Header
    public Elf.DynamicStructure getDynamicStructure(long j, int i) throws IOException {
        return new Dynamic64Structure(this.parser, this, j, i);
    }
}
