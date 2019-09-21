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

import net.troja.iot.mictrack.ProtocolParseException;
import net.troja.iot.mictrack.model.CellData;
import net.troja.iot.mictrack.model.EventType;
import net.troja.iot.mictrack.model.GpsData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CellDataParserTest {
    private static final EventType EVENT = EventType.CELL_REPORT;

    private CellDataParser classToTest = new CellDataParser();

    @Test
    public void parseLTE() {
        int mnc = 0;
        int cellId = 167910723;
        int lac = 14924;
        int mcc = 460;
        int pcid = 176;
        String voltage = "3587";
        int sequence = 11;

        String data = GpsDataParserTest.UTC_TIME + AbstractReportDataParser.SEPARATOR +
                mnc + AbstractReportDataParser.SUB_SEPARATOR +
                cellId + AbstractReportDataParser.SUB_SEPARATOR +
                lac + AbstractReportDataParser.SUB_SEPARATOR +
                mcc + AbstractReportDataParser.SUB_SEPARATOR +
                pcid + AbstractReportDataParser.SEPARATOR +
                EVENT.ordinal() + AbstractReportDataParser.SEPARATOR +
                voltage + AbstractReportDataParser.SEPARATOR + sequence;

        CellData result = classToTest.parse(data);

        assertEquals(AbstractReportDataParser.parseUtcTime(GpsDataParserTest.UTC_TIME), result.getTime());
        assertEquals(mnc, result.getMnc());
        assertEquals(cellId, result.getCellId());
        assertEquals(lac, result.getLac());
        assertEquals(mcc, result.getMcc());
        assertEquals(pcid, result.getPcid());
        assertEquals(EVENT, result.getEvent());
        assertEquals(AbstractReportDataParser.parseVoltage(voltage), result.getVoltage());
        assertEquals(sequence, result.getSequenceNumber());
    }

    @Test
    public void parseGSM() {
        final int mnc = 0;
        final int cellId = 21681;
        final int lac = 20616;
        final int mcc = 460;
        String voltage = "3976";
        int sequence = 0;

        String data = GpsDataParserTest.UTC_TIME + AbstractReportDataParser.SEPARATOR +
                mnc + AbstractReportDataParser.SUB_SEPARATOR +
                cellId + AbstractReportDataParser.SUB_SEPARATOR +
                lac + AbstractReportDataParser.SUB_SEPARATOR +
                mcc + AbstractReportDataParser.SEPARATOR +
                EVENT.ordinal() + AbstractReportDataParser.SEPARATOR +
                voltage + AbstractReportDataParser.SEPARATOR + sequence;

        CellData result = classToTest.parse(data);

        assertEquals(AbstractReportDataParser.parseUtcTime(GpsDataParserTest.UTC_TIME), result.getTime());
        assertEquals(mnc, result.getMnc());
        assertEquals(cellId, result.getCellId());
        assertEquals(lac, result.getLac());
        assertEquals(mcc, result.getMcc());
        assertEquals(0, result.getPcid());
        assertEquals(EVENT, result.getEvent());
        assertEquals(AbstractReportDataParser.parseVoltage(voltage), result.getVoltage());
        assertEquals(sequence, result.getSequenceNumber());
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
        String data = "181129081017+0,21681,20616,460,6,3+4+3976+0";

        assertThrows(ProtocolParseException.class, () -> classToTest.splitAndCheck(data));
    }
}
