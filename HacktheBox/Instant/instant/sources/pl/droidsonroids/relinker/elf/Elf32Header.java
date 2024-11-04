package pl.droidsonroids.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import pl.droidsonroids.relinker.elf.Elf;

/* loaded from: classes.dex */
public class Elf32Header extends Elf.Header {
    private final ElfParser parser;

    public Elf32Header(boolean z, ElfParser elfParser) throws IOException {
        this.bigEndian = z;
        this.parser = elfParser;
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.order(z ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.type = elfParser.readHalf(allocate, 16L);
        this.phoff = elfParser.readWord(allocate, 28L);
        this.shoff = elfParser.readWord(allocate, 32L);
        this.phentsize = elfParser.readHalf(allocate, 42L);
        this.phnum = elfParser.readHalf(allocate, 44L);
        this.shentsize = elfParser.readHalf(allocate, 46L);
        this.shnum = elfParser.readHalf(allocate, 48L);
        this.shstrndx = elfParser.readHalf(allocate, 50L);
    }

    @Override // pl.droidsonroids.relinker.elf.Elf.Header
    public Elf.SectionHeader getSectionHeader(int i) throws IOException {
        return new Section32Header(this.parser, this, i);
    }

    @Override // pl.droidsonroids.relinker.elf.Elf.Header
    public Elf.ProgramHeader getProgramHeader(long j) throws IOException {
        return new Program32Header(this.parser, this, j);
    }

    @Override // pl.droidsonroids.relinker.elf.Elf.Header
    public Elf.DynamicStructure getDynamicStructure(long j, int i) throws IOException {
        return new Dynamic32Structure(this.parser, this, j, i);
    }
}
