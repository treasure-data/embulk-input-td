package org.embulk.input.td.writer;

import org.embulk.spi.Column;
import org.embulk.spi.PageBuilder;
import org.msgpack.value.Value;

public abstract class AbstractValueWriter
        implements ValueWriter {

    protected final Column column;
    protected final int index;

    protected AbstractValueWriter(Column column) {
        this.column = column;
        this.index = column.getIndex();
    }

    @Override
    public void write(final Value v, final PageBuilder to) {
        if (v.isNilValue()) {
            to.setNull(index);
        } else {
            writeNotNull(v, to);
        }
    }

    protected abstract void writeNotNull(Value v, PageBuilder to);
}