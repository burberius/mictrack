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

import net.troja.iot.mictrack.model.DataType;

public class ReportDataParserFactory {
    public ReportDataParser getParserFor(DataType type) {
        if(type == null) {
            throw new IllegalArgumentException("No data type given");
        }

        switch (type) {
            case GPS:
                return new GpsDataParser();
            case GSM_CELL:
            case LTE_CELL:
                return new CellDataParser();
            default:
                throw new IllegalArgumentException("There is no parser for " + type + " yet");
        }
    }
}
