package net.troja.iot.mictrack.parser;

import net.troja.iot.mictrack.model.DataType;
import net.troja.iot.mictrack.ProtocolParseException;
import net.troja.iot.mictrack.model.ReportData;
import net.troja.iot.mictrack.model.ReportMessage;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ReportMessageParser {
    public static final String SEPARATOR = ";";

    protected static final String MICTRACK_MARKER = "MT";
    private static final int NUMBER_OF_PARTS = 5;

    private ReportDataParserFactory parserFactory = new ReportDataParserFactory();

    public ReportMessage parse(ByteBuffer buffer) {
        if(buffer == null || buffer.capacity() == 0) {
            throw new ProtocolParseException("Nothing to parse");
        }
        String message = new String(buffer.array(), Charset.forName("ASCII"));
        String[] parts = message.split(SEPARATOR);
        if(parts.length != NUMBER_OF_PARTS) {
            throw new ProtocolParseException("Wrong message length");
        }
        if(!MICTRACK_MARKER.equals(parts[0])) {
            throw new ProtocolParseException("Not a valid mictrack message");
        }
        return parseValidMessage(parts);
    }

    private ReportMessage parseValidMessage(String[] parts) {
        DataType dataType = DataType.parse(parts[3]);
        ReportData data = parserFactory.getFactoryFor(dataType).parse(parts[4]);
        return ReportMessage.builder()
                .mode(Integer.parseInt(parts[1]))
                .imei(parts[2])
                .dataType(dataType)
                .data(data)
                .build();
    }

    protected void setParserFactory(ReportDataParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }
}
