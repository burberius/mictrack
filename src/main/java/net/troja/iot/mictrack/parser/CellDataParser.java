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

public class CellDataParser extends AbstractReportDataParser<CellData> {
    @Override
    public CellData parse(String data) {
        String[] baseFields = splitAndCheck(data, SEPARATOR, 5, 0);
        String[] cellFields = splitAndCheck(baseFields[1], SUB_SEPARATOR, CellFields.CELL_FIELDS_LENGTH_LTE,
                CellFields.CELL_FIELDS_LENGTH_GSM);
        CellData.CellDataBuilder builder = CellData.builder()
                .time(parseUtcTime(baseFields[0]))
                .cellFields(buildCellFields(cellFields))
                .event(EventType.values[Integer.parseInt(baseFields[2])])
                .voltage(parseVoltage(baseFields[3]))
                .sequenceNumber(Short.parseShort(baseFields[4]));
        return builder.build();
    }
}
