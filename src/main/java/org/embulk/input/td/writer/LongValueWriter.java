package org.embulk.input.td.writer;

import org.embulk.spi.Column;
import org.embulk.spi.PageBuilder;
import org.msgpack.value.Value;

public class LongValueWriter
        extends AbstractValueWriter
{
    public LongValueWriter(Column column)
    {
        super(column);
    }

    @Override
    public void writeNotNull(Value v, PageBuilder to)
    {
        to.setLong(index, v.asIntegerValue().toLong());
    }
}
