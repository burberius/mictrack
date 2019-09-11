package net.troja.iot.mictrack.parser;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractReportDataParserTest {
    @Test
    public void parseUtcTime() {
        LocalDateTime expected = LocalDateTime.of(2019, 9, 7, 11, 12, 42);
        String timestamp = "190907111242";

        assertEquals(expected, AbstractReportDataParser.parseUtcTime(timestamp));
    }

    @Test
    public void parseDouble() {
        String number = "123.456";

        assertEquals(BigDecimal.valueOf(123.456d), AbstractReportDataParser.parseDouble(number));
    }

    @Test
    public void parseVoltage() {
        String voltage = "3744";

        assertEquals(BigDecimal.valueOf(3.744), AbstractReportDataParser.parseVoltage(voltage));
    }
}
