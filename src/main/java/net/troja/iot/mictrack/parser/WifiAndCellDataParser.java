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

import net.troja.iot.mictrack.model.CellData;
import net.troja.iot.mictrack.model.CellFields;
import net.troja.iot.mictrack.model.EventType;
import net.troja.iot.mictrack.model.WifiAndCellData;
import net.troja.iot.mictrack.model.WifiFields;

public class WifiAndCellDataParser extends AbstractReportDataParser<WifiAndCellData> {
    @Override
    public WifiAndCellData parse(String data) {
        String[] baseFields = splitAndCheck(data, SEPARATOR, 6, 0);
        String[] wifiFields = splitAndCheck(baseFields[1], SUB_SEPARATOR, 4, 0);
        String[] cellFields = splitAndCheck(baseFields[2], SUB_SEPARATOR, CellFields.CELL_FIELDS_LENGTH_LTE,
                CellFields.CELL_FIELDS_LENGTH_GSM);
        return WifiAndCellData.builder()
                .time(parseUtcTime(baseFields[0]))
                .wifiFields(buildWifiFields(wifiFields))
                .cellFields(buildCellFields(cellFields))
                .event(EventType.values[Integer.parseInt(baseFields[3])])
                .voltage(parseVoltage(baseFields[4]))
                .sequenceNumber(Short.parseShort(baseFields[5])).build();
    }
}
