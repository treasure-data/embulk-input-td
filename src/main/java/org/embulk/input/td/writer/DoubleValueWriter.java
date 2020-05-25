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
        if (v.isFloatValue()) {
            to.setDouble(index, v.asFloatValue().toDouble());
        } else if (v.isStringValue()) {
            // Support for DECIMAL type, which is packed / unpacked as String type in msgpack
            to.setDouble(index, Double.parseDouble(v.asStringValue().toString()));
        } else {
            to.setNull(index);
        }
    }
}
