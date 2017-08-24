package org.embulk.input.td.writer;

import org.embulk.spi.Column;
import org.embulk.spi.PageBuilder;
import org.msgpack.value.Value;

public class JsonValueWriter
        extends AbstractValueWriter
{
    public JsonValueWriter(final Column column)
    {
        super(column);
    }

    @Override
    public void writeNotNull(final Value v, final PageBuilder to)
    {
        to.setJson(index, v);
    }
}
