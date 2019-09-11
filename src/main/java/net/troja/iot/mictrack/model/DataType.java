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

package net.troja.iot.mictrack.model;

import net.troja.iot.mictrack.ProtocolParseException;

public enum DataType {
    GPS, WIFI, GSM_CELL, LTE_CELL, WIFI_GSM_CELL, WIFI_LTE_CELL, HEART_BEAT,
    CONFIGURATION, DEVICE_BINDING;

    public static DataType parse(String value) {
        if (value == null || value.isEmpty()) {
            throw new ProtocolParseException("DataType is null");
        }
        switch (value) {
            case "R0":
                return GPS;
            case "R1":
                return WIFI;
            case "R2":
                return GSM_CELL;
            case "R3":
                return LTE_CELL;
            case "R12":
                return WIFI_GSM_CELL;
            case "R13":
                return WIFI_LTE_CELL;
            case "RH":
                return HEART_BEAT;
            case "RC":
                return CONFIGURATION;
            case "B":
                return DEVICE_BINDING;
            default:
                throw new ProtocolParseException("Unknown DataType " + value);
        }
    }
}
