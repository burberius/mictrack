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
import net.troja.iot.mictrack.model.ReportData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class AbstractReportDataParser<S extends ReportData> implements ReportDataParser<S> {
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss", Locale.ENGLISH);
    public static final String SEPARATOR = "+";

    private int expectedLength;

    public AbstractReportDataParser(int expectedLength) {
        this.expectedLength = expectedLength;
    }

    protected String[] splitAndCheck(String data) {
        if (data == null || data.isEmpty()) {
            throw new ProtocolParseException("Nothing to parse");
        }
        String[] split = data.split("\\" + SEPARATOR);
        if (split.length != expectedLength) {
            throw new ProtocolParseException("Data length does not match");
        }
        return split;
    }

    public static LocalDateTime parseUtcTime(String time) {
        return LocalDateTime.parse(time, FORMATTER);
    }

    public static BigDecimal parseDouble(String value) {
        return new BigDecimal(value);
    }

    public static BigDecimal parseVoltage(String voltage) {
        return new BigDecimal(voltage.charAt(0) + "." + voltage.substring(1));
    }
}
