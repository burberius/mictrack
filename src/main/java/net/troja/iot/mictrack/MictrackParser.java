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

package net.troja.iot.mictrack;

import net.troja.iot.mictrack.model.ReportMessage;
import net.troja.iot.mictrack.parser.ReportMessageParser;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Parser for the mictrack communication protocol.
 * <p>
 * The current version is not scalable, as it uses only one parser and is synchronous!
 */
public class MictrackParser {
    public static final Charset CHARSET_ASCII = StandardCharsets.US_ASCII;
    private ReportMessageParser parser = new ReportMessageParser();

    public ReportMessage parse(ByteBuffer data) {
        return parser.parse(data);
    }
}
