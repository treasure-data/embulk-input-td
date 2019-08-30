package org.embulk.input.td.writer;

import org.embulk.spi.Column;
import org.embulk.spi.PageBuilder;
import org.embulk.spi.time.Timestamp;
import org.msgpack.value.Value;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TimestampValueWriter
        extends AbstractValueWriter {

    public TimestampValueWriter(final Column column) {
        super(column);
    }

    @Override
    public void writeNotNull(final Value v, final PageBuilder to) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date formatedDate = format.parse(v.asStringValue().toString());
            long miliseconds = formatedDate.getTime();
            to.setTimestamp(index, Timestamp.ofEpochMilli(miliseconds));
        } catch (Exception e) {
            
        }
    }
}
