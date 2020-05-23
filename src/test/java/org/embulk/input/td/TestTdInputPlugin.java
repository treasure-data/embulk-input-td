package org.embulk.input.td;

import static org.embulk.spi.type.Types.BOOLEAN;
import static org.embulk.spi.type.Types.DOUBLE;
import static org.embulk.spi.type.Types.JSON;
import static org.embulk.spi.type.Types.LONG;
import static org.embulk.spi.type.Types.STRING;
import static org.embulk.spi.type.Types.TIMESTAMP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeNotNull;

import java.util.ArrayList;
import java.util.List;
import org.embulk.EmbulkTestRuntime;
import org.embulk.config.ConfigDiff;
import org.embulk.config.ConfigSource;
import org.embulk.config.TaskReport;
import org.embulk.config.TaskSource;
import org.embulk.input.td.TdInputPlugin;
import org.embulk.spi.InputPlugin;
import org.embulk.spi.PageOutput;
import org.embulk.spi.Schema;
import org.embulk.spi.TestPageBuilderReader.MockPageOutput;
import org.embulk.spi.util.Pages;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;


public class TestTdInputPlugin
{
    private static String EMBULK_TD_TEST_APIKEY;
    private static String EMBULK_TD_TEST_DATABASE;

    /*
     * This test case requires environment variables:
     *   EMBULK_TD_TEST_APIKEY
     *   EMBULK_TD_TEST_DATABASE
     * If the variables not set, the test case is skipped.
     */
    @BeforeClass
    public static void initializeConstantVariables()
    {
        EMBULK_TD_TEST_APIKEY = System.getenv("EMBULK_TD_TEST_APIKEY");
        EMBULK_TD_TEST_DATABASE = System.getenv("EMBULK_TD_TEST_DATABASE");
        assumeNotNull(EMBULK_TD_TEST_APIKEY, EMBULK_TD_TEST_DATABASE);
    }

    @Rule
    public EmbulkTestRuntime runtime = new EmbulkTestRuntime();

    private ConfigSource config;
    private InputPlugin runner;
    private MockPageOutput output;

    @Before
    public void createResources()
    {
        config = runtime.getExec().newConfigSource()
                .set("type", "td")
                .set("apikey", EMBULK_TD_TEST_APIKEY)
                .set("database", EMBULK_TD_TEST_DATABASE)
                .set("query", "");
        runner = new TdInputPlugin();
        output = new MockPageOutput();
    }

    @Test
    public void primitiveTest()
    {
        String primitiveQuery =
                   "SELECT * FROM ( VALUES ( "
                   + "BOOLEAN 'true', "
                   + "INTEGER '0', "
                   + "BIGINT '1', "
                   + "DOUBLE '0.0', "
                   + "DECIMAL '0.5', "
                   + "VARCHAR '0123' "
                   + " ) )";
        Schema schema = Schema.builder()
                   .add("col_boolean",   BOOLEAN)
                   .add("col_integer",   LONG)
                   .add("col_bigint",    LONG)
                   .add("col_double",    DOUBLE)
                   .add("col_decimal",   DOUBLE)
                   .add("col_varchar",   STRING)
                   .build();

        ConfigSource config = this.config.deepCopy().set("query", primitiveQuery);
        ConfigDiff configDiff = runner.transaction(config, new Control(runner, output));

        List<Object[]> records = Pages.toObjects(schema, output.pages);
        assertEquals(1, records.size());
        {
            Object[] record = records.get(0);
            assertEquals(true, record[0]);
            assertEquals(0L, record[1]);
            assertEquals(1L, record[2]);
            assertEquals(0.0, record[3]);
            assertEquals(0.5, record[4]);
            assertEquals("0123", record[5]);
        }
    }

    static class Control
            implements InputPlugin.Control
    {
        private InputPlugin runner;
        private PageOutput output;

        Control(InputPlugin runner, PageOutput output)
        {
            this.runner = runner;
            this.output = output;
        }

        @Override
        public List<TaskReport> run(TaskSource taskSource, Schema schema, int taskCount)
        {
            List<TaskReport> reports = new ArrayList<>();
            for (int i = 0; i < taskCount; i++) {
                reports.add(runner.run(taskSource, schema, i, output));
            }
            return reports;
        }
    }
}
