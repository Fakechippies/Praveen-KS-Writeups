package pl.droidsonroids.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import pl.droidsonroids.relinker.elf.Elf;

/* loaded from: classes.dex */
public class Section64Header extends Elf.SectionHeader {
    public Section64Header(ElfParser elfParser, Elf.Header header, int i) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.info = elfParser.readWord(allocate, header.shoff + (i * header.shentsize) + 44);
    }
}
