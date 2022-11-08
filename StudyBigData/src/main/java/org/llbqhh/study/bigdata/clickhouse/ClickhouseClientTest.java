package org.llbqhh.study.bigdata.clickhouse;

import com.clickhouse.client.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@Slf4j
public class ClickhouseClientTest {

    @Test
    public void testShowDatabases() {
//  endpoint: protocol://host[:port][/database][?param[=value][&param[=value]][#tag[,tag]]
        ClickHouseNode endpoint = ClickHouseNode.of("http://localhost:18123"); // http://localhost:8443?ssl=true&sslmode=NONE

        try (ClickHouseClient client = ClickHouseClient.newInstance(ClickHouseProtocol.HTTP);
             ClickHouseResponse response = client.connect(endpoint) // or client.connect(endpoints)
                     // you'll have to parse response manually if using a different format
                     .format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
                     .query("select * from numbers(:limit)")
                     .params(1000).executeAndWait()) {

            // or response.stream() if you prefer stream API
            for (ClickHouseRecord r : response.records()) {
                int num = r.getValue(0).asInteger();
                // type conversion
                String str = r.getValue(0).asString();
                LocalDate date = r.getValue(0).asDate();
                log.info("num {}, str {}, date {}.", num, str, date);
                System.out.println(num);
            }

            ClickHouseResponseSummary summary = response.getSummary();
            long totalRows = summary.getTotalRowsToRead();
            log.info("total Rows {}", totalRows);
        } catch (ClickHouseException e) {
            throw new RuntimeException(e);
        }
    }
}
