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
import net.troja.iot.mictrack.model.EventType;
import net.troja.iot.mictrack.model.WifiData;
import net.troja.iot.mictrack.model.WifiFields;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WifiDataParserTest {
    public static final String MAC1 = "6a:db:54:5a:79:6d";
    public static final int RSSI1 = -91;
    public static final String MAC2 = "00:9a:cd:a2:e6:21";
    public static final int RSSI2 = -94;
    public static final EventType  EVENT = EventType.WIFI_REPORT;
    public static final String VOLTAGE  = "3831";
    public static final int SEQUENCE = 0;

    private WifiDataParser classToTest = new WifiDataParser();

    @Test
    public void parse() {
        String data = GpsDataParserTest.UTC_TIME + AbstractReportDataParser.SEPARATOR +
                MAC1 + AbstractReportDataParser.SUB_SEPARATOR +
                RSSI1 + AbstractReportDataParser.SUB_SEPARATOR +
                MAC2 + AbstractReportDataParser.SUB_SEPARATOR +
                RSSI2 + AbstractReportDataParser.SEPARATOR +
                EVENT.ordinal() + AbstractReportDataParser.SEPARATOR +
                VOLTAGE + AbstractReportDataParser.SEPARATOR + SEQUENCE;

        WifiData result = classToTest.parse(data);

        assertEquals(AbstractReportDataParser.parseUtcTime(GpsDataParserTest.UTC_TIME), result.getTime());
        @NonNull WifiFields wifiFields = result.getWifiFields();
        assertEquals(MAC1, wifiFields.getMac1());
        assertEquals(RSSI1, wifiFields.getRssi1());
        assertEquals(MAC2, wifiFields.getMac2());
        assertEquals(RSSI2, wifiFields.getRssi2());
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
        String data = "190108024848+6a:db:54:5a:79:6d,-91,00:9a:cd:a2:e6:21,-94+3+3831+0+6";

        assertThrows(ProtocolParseException.class, () -> classToTest.parse(data));
    }
}
