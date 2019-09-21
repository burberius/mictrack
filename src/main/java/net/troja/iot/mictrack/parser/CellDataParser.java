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
import net.troja.iot.mictrack.model.EventType;

public class CellDataParser extends AbstractReportDataParser<CellData> {
    public CellDataParser() {
        super(9, 5, 4);
    }

    @Override
    public CellData parse(String data) {
        String[] fields = splitAndCheck(data);
        CellData.CellDataBuilder builder = CellData.builder()
                .time(parseUtcTime(fields[0]))
                .mnc(Integer.parseInt(fields[1]))
                .cellId(Integer.parseInt(fields[2]))
                .lac(Integer.parseInt(fields[3]))
                .mcc(Integer.parseInt(fields[4]));
        if(fields[5] != null)
            builder.pcid(Integer.parseInt(fields[5]));
        builder.event(EventType.values[Integer.parseInt(fields[6])])
                .voltage(parseVoltage(fields[7]))
                .sequenceNumber(Short.parseShort(fields[8]));
        return builder.build();
    }
}
