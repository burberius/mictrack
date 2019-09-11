package net.troja.iot.mictrack.parser;

import net.troja.iot.mictrack.model.DataType;
import net.troja.iot.mictrack.ProtocolParseException;
import net.troja.iot.mictrack.model.ReportData;
import net.troja.iot.mictrack.model.ReportMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportMessageParserTest {
    private static final int MODE = 6;
    private static final String IMEI = "866428881361423";
    private static final String DATA_TYPE = "R0";
    private static final String DATA = "Data";

    private ReportMessageParser classToTest;

    @Mock
    private ReportDataParserFactory parserFactory;
    @Mock
    private ReportDataParser reportDataParser;

    @BeforeEach
    public void setUp() {
        classToTest = new ReportMessageParser();
        classToTest.setParserFactory(parserFactory);
    }

    @Test
    public void parse() {
        String message = ReportMessageParser.MICTRACK_MARKER + ";" + MODE + ";" + IMEI + ";" + DATA_TYPE + ";" + DATA;
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(Charset.forName("ASCII")));
        when(parserFactory.getFactoryFor(DataType.parse(DATA_TYPE))).thenReturn(reportDataParser);
        when(reportDataParser.parse(DATA)).thenReturn(new ReportData() {});

        ReportMessage result = classToTest.parse(buffer);

        assertEquals(MODE, result.getMode());
        assertEquals(IMEI, result.getImei());
        assertEquals(DataType.parse(DATA_TYPE), result.getDataType());
    }

    @Test
    public void parseNull() {
        ByteBuffer buffer = ByteBuffer.allocate(0);
        assertThrows(ProtocolParseException.class, () -> classToTest.parse(buffer));
    }

    @Test
    public void parseEmpty() {
        String message = "";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(Charset.forName("ASCII")));
        assertThrows(ProtocolParseException.class, () -> classToTest.parse(buffer));
    }

    @Test
    public void parseRubbish() {
        String message = "Nothing here";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(Charset.forName("ASCII")));
        assertThrows(ProtocolParseException.class, () -> classToTest.parse(buffer));
    }

    @Test
    public void parseWrongLength() {
        String message = "MT;6;866425031361423;R0";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(Charset.forName("ASCII")));
        assertThrows(ProtocolParseException.class, () -> classToTest.parse(buffer));
    }

    @Test
    public void parseNotMictrack() {
        String message = "NT;6;866425031361423;R0;Data";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(Charset.forName("ASCII")));
        assertThrows(ProtocolParseException.class, () -> classToTest.parse(buffer));
    }
}
