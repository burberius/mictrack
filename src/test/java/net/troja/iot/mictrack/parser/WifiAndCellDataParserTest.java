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
import net.troja.iot.mictrack.model.CellFields;
import net.troja.iot.mictrack.model.EventType;
import net.troja.iot.mictrack.model.WifiAndCellData;
import net.troja.iot.mictrack.model.WifiFields;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WifiAndCellDataParserTest {
    private WifiAndCellDataParser classToTest = new WifiAndCellDataParser();
    public static final String VOLTAGE = "3831";
    private static final EventType EVENT = EventType.WIFI_REPORT;
    public static final int SEQUENCE = 0;

    @Test
    public void parseLTE() {
        int pcid = 406;

        String data = GpsDataParserTest.UTC_TIME + AbstractReportDataParser.SEPARATOR +
                WifiDataParserTest.MAC1 + AbstractReportDataParser.SUB_SEPARATOR +
                WifiDataParserTest.RSSI1 + AbstractReportDataParser.SUB_SEPARATOR +
                WifiDataParserTest.MAC2 + AbstractReportDataParser.SUB_SEPARATOR +
                WifiDataParserTest.RSSI2 + AbstractReportDataParser.SEPARATOR +
                CellDataParserTest.MNC + AbstractReportDataParser.SUB_SEPARATOR +
                CellDataParserTest.CELL_ID_LTE + AbstractReportDataParser.SUB_SEPARATOR +
                CellDataParserTest.CELL_ID_LTE  + AbstractReportDataParser.SUB_SEPARATOR +
                CellDataParserTest.CELL_ID_LTE  + AbstractReportDataParser.SUB_SEPARATOR +
                pcid + AbstractReportDataParser.SEPARATOR +
                EVENT.ordinal() + AbstractReportDataParser.SEPARATOR +
                VOLTAGE + AbstractReportDataParser.SEPARATOR + SEQUENCE;

        WifiAndCellData result = classToTest.parse(data);

        assertEquals(AbstractReportDataParser.parseUtcTime(GpsDataParserTest.UTC_TIME), result.getTime());
        @NonNull WifiFields wifiFields = result.getWifiFields();
        assertEquals(WifiDataParserTest.MAC1, wifiFields.getMac1());
        assertEquals(WifiDataParserTest.RSSI1, wifiFields.getRssi1());
        assertEquals(WifiDataParserTest.MAC2, wifiFields.getMac2());
        assertEquals(WifiDataParserTest.RSSI2, wifiFields.getRssi2());
        @NonNull CellFields cellFields = result.getCellFields();
        assertEquals(CellDataParserTest.MNC, cellFields.getMnc());
        assertEquals(CellDataParserTest.CELL_ID_LTE , cellFields.getCellId());
        assertEquals(CellDataParserTest.CELL_ID_LTE , cellFields.getLac());
        assertEquals(CellDataParserTest.CELL_ID_LTE , cellFields.getMcc());
        assertEquals(pcid, cellFields.getPcid());
        assertEquals(EVENT, result.getEvent());
        assertEquals(AbstractReportDataParser.parseVoltage(VOLTAGE), result.getVoltage());
        assertEquals(SEQUENCE, result.getSequenceNumber());
    }

    @Test
    public void parseGSM() {
        final int mnc = 0;
        final int cellId = 21681;
        final int lac = 20616;
        final int mcc = 460;

        String data = GpsDataParserTest.UTC_TIME + AbstractReportDataParser.SEPARATOR +
                WifiDataParserTest.MAC1 + AbstractReportDataParser.SUB_SEPARATOR +
                WifiDataParserTest.RSSI1 + AbstractReportDataParser.SUB_SEPARATOR +
                WifiDataParserTest.MAC2 + AbstractReportDataParser.SUB_SEPARATOR +
                WifiDataParserTest.RSSI2 + AbstractReportDataParser.SEPARATOR +
                CellDataParserTest.MNC + AbstractReportDataParser.SUB_SEPARATOR +
                CellDataParserTest.CELL_ID_GSM + AbstractReportDataParser.SUB_SEPARATOR +
                CellDataParserTest.CELL_ID_GSM  + AbstractReportDataParser.SUB_SEPARATOR +
                CellDataParserTest.CELL_ID_GSM  + AbstractReportDataParser.SEPARATOR +
                EVENT.ordinal() + AbstractReportDataParser.SEPARATOR +
                VOLTAGE + AbstractReportDataParser.SEPARATOR + SEQUENCE;

        WifiAndCellData result = classToTest.parse(data);

        assertEquals(AbstractReportDataParser.parseUtcTime(GpsDataParserTest.UTC_TIME), result.getTime());
        @NonNull WifiFields wifiFields = result.getWifiFields();
        assertEquals(WifiDataParserTest.MAC1, wifiFields.getMac1());
        assertEquals(WifiDataParserTest.RSSI1, wifiFields.getRssi1());
        assertEquals(WifiDataParserTest.MAC2, wifiFields.getMac2());
        assertEquals(WifiDataParserTest.RSSI2, wifiFields.getRssi2());
        @NonNull CellFields cellFields = result.getCellFields();
        assertEquals(CellDataParserTest.MNC, cellFields.getMnc());
        assertEquals(CellDataParserTest.CELL_ID_GSM , cellFields.getCellId());
        assertEquals(CellDataParserTest.CELL_ID_GSM , cellFields.getLac());
        assertEquals(CellDataParserTest.CELL_ID_GSM , cellFields.getMcc());
        assertEquals(EVENT, result.getEvent());
        assertEquals(AbstractReportDataParser.parseVoltage(VOLTAGE), result.getVoltage());
        assertEquals(SEQUENCE, result.getSequenceNumber());
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
        String data = "190108024848+6a:db:54:5a:79:6d,-91,00:9a:cd:a2:e6:\n" +
                "21,-94+0,1030,168267587,5,460,406+3+3831+0";

        assertThrows(ProtocolParseException.class, () -> classToTest.parse(data));
    }
}
