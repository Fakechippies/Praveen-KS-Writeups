package pl.droidsonroids.relinker.elf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import pl.droidsonroids.relinker.elf.Elf;

/* loaded from: classes.dex */
public class Dynamic32Structure extends Elf.DynamicStructure {
    public Dynamic32Structure(ElfParser elfParser, Elf.Header header, long j, int i) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.order(header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j2 = j + (i * 8);
        this.tag = elfParser.readWord(allocate, j2);
        this.val = elfParser.readWord(allocate, j2 + 4);
    }
}
