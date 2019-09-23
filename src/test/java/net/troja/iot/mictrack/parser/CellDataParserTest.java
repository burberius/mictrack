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

import lombok.NonNull;
import net.troja.iot.mictrack.ProtocolParseException;
import net.troja.iot.mictrack.model.CellData;
import net.troja.iot.mictrack.model.CellFields;
import net.troja.iot.mictrack.model.EventType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CellDataParserTest {
    public static final int MNC = 0;
    public static final int CELL_ID_LTE = 167910723;
    public static final int LAC_LTE = 14924;
    public static final int MCC_LTE = 460;
    public static final int CELL_ID_GSM = 21681;
    public static final int LAC_GSM = 20616;
    public static final int MCC_GSM = 460;
    private static final EventType EVENT = EventType.CELL_REPORT;

    private CellDataParser classToTest = new CellDataParser();

    @Test
    public void parseLTE() {
        int pcid = 176;
        String voltage = "3587";
        int sequence = 11;

        String data = GpsDataParserTest.UTC_TIME + AbstractReportDataParser.SEPARATOR +
                MNC + AbstractReportDataParser.SUB_SEPARATOR +
                CELL_ID_LTE + AbstractReportDataParser.SUB_SEPARATOR +
                LAC_LTE + AbstractReportDataParser.SUB_SEPARATOR +
                MCC_LTE + AbstractReportDataParser.SUB_SEPARATOR +
                pcid + AbstractReportDataParser.SEPARATOR +
                EVENT.ordinal() + AbstractReportDataParser.SEPARATOR +
                voltage + AbstractReportDataParser.SEPARATOR + sequence;

        CellData result = classToTest.parse(data);

        assertEquals(AbstractReportDataParser.parseUtcTime(GpsDataParserTest.UTC_TIME), result.getTime());
        @NonNull CellFields cellFields = result.getCellFields();
        assertEquals(MNC, cellFields.getMnc());
        assertEquals(CELL_ID_LTE, cellFields.getCellId());
        assertEquals(LAC_LTE, cellFields.getLac());
        assertEquals(MCC_LTE, cellFields.getMcc());
        assertEquals(pcid, cellFields.getPcid());
        assertEquals(EVENT, result.getEvent());
        assertEquals(AbstractReportDataParser.parseVoltage(voltage), result.getVoltage());
        assertEquals(sequence, result.getSequenceNumber());
    }

    @Test
    public void parseGSM() {
        String voltage = "3976";
        int sequence = 0;

        String data = GpsDataParserTest.UTC_TIME + AbstractReportDataParser.SEPARATOR +
                MNC + AbstractReportDataParser.SUB_SEPARATOR +
                CELL_ID_GSM + AbstractReportDataParser.SUB_SEPARATOR +
                LAC_GSM + AbstractReportDataParser.SUB_SEPARATOR +
                MCC_GSM + AbstractReportDataParser.SEPARATOR +
                EVENT.ordinal() + AbstractReportDataParser.SEPARATOR +
                voltage + AbstractReportDataParser.SEPARATOR + sequence;

        CellData result = classToTest.parse(data);

        assertEquals(AbstractReportDataParser.parseUtcTime(GpsDataParserTest.UTC_TIME), result.getTime());
        @NonNull CellFields cellFields = result.getCellFields();
        assertEquals(MNC, cellFields.getMnc());
        assertEquals(CELL_ID_GSM, cellFields.getCellId());
        assertEquals(LAC_GSM, cellFields.getLac());
        assertEquals(MCC_GSM, cellFields.getMcc());
        assertEquals(0, cellFields.getPcid());
        assertEquals(EVENT, result.getEvent());
        assertEquals(AbstractReportDataParser.parseVoltage(voltage), result.getVoltage());
        assertEquals(sequence, result.getSequenceNumber());
    }

    @Test
    public void parseNull() {
        assertThrows(ProtocolParseException.class, () -> classToTest.parse(null));
    }

    @Test
    public void parseEmpty() {
        assertThrows(ProtocolParseException.class, () -> classToTest.parse(""));
    }

    @Test
    public void parseWrongContent() {
        String data = "181129081017+0,21681,20616,460,6,3+4+3976+0";

        assertThrows(ProtocolParseException.class, () -> classToTest.parse(data));
    }
}
