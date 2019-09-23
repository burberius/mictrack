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
import net.troja.iot.mictrack.model.CellFields;
import net.troja.iot.mictrack.model.ReportData;
import net.troja.iot.mictrack.model.WifiFields;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class AbstractReportDataParser<S extends ReportData> implements ReportDataParser<S> {
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss", Locale.ENGLISH);
    public static final String SEPARATOR = "+";
    public static final String SUB_SEPARATOR = ",";

    protected String[] splitAndCheck(String data, String separator, int length, int alternativeLength) {
        if (data == null || data.isEmpty()) {
            throw new ProtocolParseException("Nothing to parse");
        }
        String[] split = data.split(separator == "+" ? "\\+" : separator);
        if (split.length != length && split.length != alternativeLength) {
            throw new ProtocolParseException("Data length does not match");
        }
        return split;
    }

    protected WifiFields buildWifiFields(String[] wifiFields) {
        return WifiFields.builder()
                .mac1(wifiFields[0])
                .rssi1(Integer.parseInt(wifiFields[1]))
                .mac2(wifiFields[2])
                .rssi2(Integer.parseInt(wifiFields[3]))
                .build();
    }

    protected CellFields buildCellFields(String[] cellFields) {
        CellFields.CellFieldsBuilder cellFieldsBuilder = CellFields.builder()
                .mnc(Integer.parseInt(cellFields[0]))
                .cellId(Integer.parseInt(cellFields[1]))
                .lac(Integer.parseInt(cellFields[2]))
                .mcc(Integer.parseInt(cellFields[3]));
        if(cellFields.length == CellFields.CELL_FIELDS_LENGTH_LTE) {
            cellFieldsBuilder.pcid(Integer.parseInt(cellFields[4]));
        }
        return cellFieldsBuilder.build();
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
