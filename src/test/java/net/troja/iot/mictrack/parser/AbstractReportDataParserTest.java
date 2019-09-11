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

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractReportDataParserTest {
    @Test
    public void parseUtcTime() {
        LocalDateTime expected = LocalDateTime.of(2019, 9, 7, 11, 12, 42);
        String timestamp = "190907111242";

        assertEquals(expected, AbstractReportDataParser.parseUtcTime(timestamp));
    }

    @Test
    public void parseDouble() {
        String number = "123.456";

        assertEquals(BigDecimal.valueOf(123.456d), AbstractReportDataParser.parseDouble(number));
    }

    @Test
    public void parseVoltage() {
        String voltage = "3744";

        assertEquals(BigDecimal.valueOf(3.744), AbstractReportDataParser.parseVoltage(voltage));
    }
}
