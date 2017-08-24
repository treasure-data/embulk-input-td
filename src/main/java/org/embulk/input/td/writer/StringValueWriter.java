package org.embulk.input.td.writer;

import org.embulk.spi.Column;
import org.embulk.spi.PageBuilder;
import org.msgpack.value.Value;

public class StringValueWriter
        extends AbstractValueWriter
{
    public StringValueWriter(final Column column)
    {
        super(column);
    }

    @Override
    public void writeNotNull(final Value v, final PageBuilder to)
    {
        to.setString(index, v.asStringValue().asString());
    }
}
