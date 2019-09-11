/*
 * Copyright (c) 2019 Jens Oberender
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.troja.iot.mictrack.parser;

import net.troja.iot.mictrack.MictrackParser;
import net.troja.iot.mictrack.ProtocolParseException;
import net.troja.iot.mictrack.model.DataType;
import net.troja.iot.mictrack.model.ReportData;
import net.troja.iot.mictrack.model.ReportMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(MictrackParser.CHARSET_ASCII));
        when(parserFactory.getParserFor(DataType.parse(DATA_TYPE))).thenReturn(reportDataParser);
        when(reportDataParser.parse(DATA)).thenReturn(new ReportData() {
        });

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
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(MictrackParser.CHARSET_ASCII));
        assertThrows(ProtocolParseException.class, () -> classToTest.parse(buffer));
    }

    @Test
    public void parseRubbish() {
        String message = "Nothing here";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(MictrackParser.CHARSET_ASCII));
        assertThrows(ProtocolParseException.class, () -> classToTest.parse(buffer));
    }

    @Test
    public void parseWrongLength() {
        String message = "MT;6;866425031361423;R0";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(MictrackParser.CHARSET_ASCII));
        assertThrows(ProtocolParseException.class, () -> classToTest.parse(buffer));
    }

    @Test
    public void parseNotMictrack() {
        String message = "NT;6;866425031361423;R0;Data";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(MictrackParser.CHARSET_ASCII));
        assertThrows(ProtocolParseException.class, () -> classToTest.parse(buffer));
    }
}
