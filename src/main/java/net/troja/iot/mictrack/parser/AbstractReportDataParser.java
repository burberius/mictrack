package net.troja.iot.mictrack.parser;

import net.troja.iot.mictrack.ProtocolParseException;
import net.troja.iot.mictrack.model.ReportData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class AbstractReportDataParser<S extends ReportData> implements ReportDataParser<S> {
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss", Locale.ENGLISH);
    public static final String SEPARATOR = "+";

    private int expectedLength;

    public AbstractReportDataParser(int expectedLength) {
        this.expectedLength = expectedLength;
    }

    protected String[] splitAndCheck(String data) {
        if(data == null || data.isEmpty()) {
            throw new ProtocolParseException("Nothing to parse");
        }
        String[] split = data.split("\\" + SEPARATOR);
        if(split.length != expectedLength) {
            throw new ProtocolParseException("Data length does not match");
        }
        return split;
    }

    public static LocalDateTime parseUtcTime(String time) {
        return LocalDateTime.parse(time, FORMATTER);
    }

    public static BigDecimal parseDouble(String value) {
        return new BigDecimal(value);
    }

    public static BigDecimal parseVoltage(String voltage) {
        return new BigDecimal(voltage.charAt(0) + "." + voltage.substring(1));
    }
}
