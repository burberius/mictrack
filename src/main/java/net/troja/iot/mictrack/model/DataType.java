package net.troja.iot.mictrack.model;

import net.troja.iot.mictrack.ProtocolParseException;

public enum DataType {
    GPS, WIFI, GSM_CELL, LTE_CELL, WIFI_GSM_CELL, WIFI_LTE_CELL, HEART_BEAT,
    CONFIGURATION, DEVICE_BINDING;

    public static DataType parse(String value) {
        if(value == null || value.isEmpty()) {
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
