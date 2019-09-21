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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public abstract class AbstractReportDataParser<S extends ReportData> implements ReportDataParser<S> {
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss", Locale.ENGLISH);
    public static final String SEPARATOR = "+";
    public static final String SUB_SEPARATOR = ",";

    private int expectedLength;
    private int subLength1 = 0;
    private int subLength2 = 0;

    public AbstractReportDataParser(int expectedLength) {this.expectedLength = expectedLength;}

    public AbstractReportDataParser(int expectedLength, int subLength1, int subLength2) {
        this.expectedLength = expectedLength;
        this.subLength1 = subLength1;
        this.subLength2 = subLength2;
    }

    protected String[] splitAndCheck(String data) {
        if (data == null || data.isEmpty()) {
            throw new ProtocolParseException("Nothing to parse");
        }
        String[] split = data.split("\\" + SEPARATOR);
        if(subLength1 > 0) {
            if (split.length != 5) {
                throw new ProtocolParseException("Data length does not match");
            }
            String[] subSplit = split[1].split(SUB_SEPARATOR);
            if (subSplit.length != subLength1 && (subLength2 > 0 && subSplit.length != subLength2)) {
                throw new ProtocolParseException("Data length does not match");
            }
            List<String> merge = new ArrayList<>(expectedLength);
            merge.add(split[0]);
            Arrays.stream(subSplit).forEach(merge::add);
            if(subSplit.length != subLength1) {
                for(int count = subLength2; count < subLength1; count++) {
                    merge.add(null);
                }
            }
            merge.add(split[2]);
            merge.add(split[3]);
            merge.add(split[4]);
            split = merge.toArray(new String[0]);
        } else {
            if (split.length != expectedLength) {
                throw new ProtocolParseException("Data length does not match");
            }
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
