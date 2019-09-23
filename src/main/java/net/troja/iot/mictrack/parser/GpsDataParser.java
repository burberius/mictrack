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

import net.troja.iot.mictrack.model.EventType;
import net.troja.iot.mictrack.model.GpsData;

public class GpsDataParser extends AbstractReportDataParser<GpsData> {
    @Override
    public GpsData parse(String data) {
        String[] fields = splitAndCheck(data, SEPARATOR, 9, 0);
        return GpsData.builder()
                .numberOfSatellites(Integer.parseInt(fields[0]))
                .time(parseUtcTime(fields[1]))
                .latitude(parseDouble(fields[2]))
                .longitude(parseDouble(fields[3]))
                .speed(parseDouble(fields[4]))
                .heading(Integer.parseInt(fields[5]))
                .event(EventType.values[Integer.parseInt(fields[6])])
                .voltage(parseVoltage(fields[7]))
                .sequenceNumber(Short.parseShort(fields[8]))
                .build();
    }
}
