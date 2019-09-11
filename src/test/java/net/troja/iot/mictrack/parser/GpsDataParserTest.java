package net.troja.iot.mictrack.parser;

import net.troja.iot.mictrack.ProtocolParseException;
import net.troja.iot.mictrack.model.EventType;
import net.troja.iot.mictrack.model.GpsData;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GpsDataParserTest {
    private static final int SATELLITES = 10;
    private static final String UTC_TIME = "190109091803";
    private static final BigDecimal LATITUDE = BigDecimal.valueOf(22.63827);
    private static final BigDecimal LONGITUDE = BigDecimal.valueOf(114.02922);
    private static final BigDecimal SPEED = BigDecimal.valueOf(2.14);
    private static final int HEADING = 69;
    private static final EventType EVENT = EventType.GPS_REPORT;
    private static final String VOLTAGE = "3744";
    private static final int SEQUENCE = 113;

    private GpsDataParser classToTest = new GpsDataParser();

    @Test
    public void parse() {
        String data = SATELLITES + AbstractReportDataParser.SEPARATOR +
                UTC_TIME + AbstractReportDataParser.SEPARATOR +
                LATITUDE + AbstractReportDataParser.SEPARATOR +
                LONGITUDE + AbstractReportDataParser.SEPARATOR +
                SPEED + AbstractReportDataParser.SEPARATOR +
                HEADING + AbstractReportDataParser.SEPARATOR +
                EVENT.ordinal() + AbstractReportDataParser.SEPARATOR +
                VOLTAGE + AbstractReportDataParser.SEPARATOR + SEQUENCE;

        GpsData result = classToTest.parse(data);

        assertEquals(SATELLITES, result.getNumberOfSatellites());
        assertEquals(AbstractReportDataParser.parseUtcTime(UTC_TIME), result.getTime());
        assertEquals(LATITUDE, result.getLatitude());
        assertEquals(LONGITUDE, result.getLongitude());
        assertEquals(SPEED, result.getSpeed());
        assertEquals(HEADING, result.getHeading());
        assertEquals(EVENT, result.getEvent());
        assertEquals(AbstractReportDataParser.parseVoltage(VOLTAGE), result.getVoltage());
        assertEquals(SEQUENCE, result.getSequenceNumber());
    }

    @Test
    public void parseNull() {
        assertThrows(ProtocolParseException.class, () -> classToTest.splitAndCheck(null));
    }

    @Test
    public void parseEmpty() {
        assertThrows(ProtocolParseException.class, () -> classToTest.splitAndCheck(""));
    }

    @Test
    public void parseWrongContent() {
        String data = SATELLITES + AbstractReportDataParser.SEPARATOR +
                UTC_TIME + AbstractReportDataParser.SEPARATOR +
                LATITUDE + AbstractReportDataParser.SEPARATOR +
                LONGITUDE + AbstractReportDataParser.SEPARATOR +
                SPEED + AbstractReportDataParser.SEPARATOR +
                HEADING + AbstractReportDataParser.SEPARATOR +
                EVENT.ordinal() + AbstractReportDataParser.SEPARATOR +
                VOLTAGE;

        assertThrows(ProtocolParseException.class, () -> classToTest.splitAndCheck(data));
    }
}
