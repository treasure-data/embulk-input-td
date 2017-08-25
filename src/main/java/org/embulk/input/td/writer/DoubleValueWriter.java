package org.embulk.input.td.writer;

import org.embulk.spi.Column;
import org.embulk.spi.PageBuilder;
import org.msgpack.value.Value;

public class DoubleValueWriter
        extends AbstractValueWriter {

    public DoubleValueWriter(final Column column) {
        super(column);
    }

    @Override
    public void writeNotNull(final Value v, final PageBuilder to) {
        to.setDouble(index, v.asFloatValue().toDouble());
    }
}
