package org.embulk.input.td.writer;

import org.embulk.spi.PageBuilder;
import org.msgpack.value.Value;

public interface ValueWriter {

    void write(Value v, PageBuilder to);
}
