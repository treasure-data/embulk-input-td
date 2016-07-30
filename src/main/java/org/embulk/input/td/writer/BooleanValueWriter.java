package org.embulk.input.td.writer;

import org.embulk.spi.Column;
import org.embulk.spi.PageBuilder;
import org.msgpack.value.Value;

public class BooleanValueWriter
        extends AbstractValueWriter
{
    public BooleanValueWriter(Column column)
    {
        super(column);
    }

    @Override
    public void writeNotNull(Value v, PageBuilder to)
    {
        to.setBoolean(index, v.asBooleanValue().getBoolean());
    }
}
