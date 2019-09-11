package net.troja.iot.mictrack.model;

public enum EventType {
    // ID 6 + 7 are not described in the documentation, we use UNDEFINED_1 and UNDEFINED_2 here to fill that hole.
    DEVICE_POWER_ON, SERVER_CONNECTED, GPS_REPORT, WIFI_REPORT, CELL_REPORT, SOS_REPORT, UNDEFINED_1, UNDEFINED_2,
    LOW_BATTERY, ENTER_GEOFENCE, LEAVE_GEOFENCE, HEARBEAT, POWER_OFF, CHARGING, CHARGING_FULL,
    POWER_ON_UNDER_POWER_SAVING_MODE, POWER_OFF_UNDER_POWER_SAVING_MODE;

    public static final EventType values[] = values();

    public static EventType forId(int id) {
        if(id < 0 || id > values().length || id == UNDEFINED_1.ordinal() || id == UNDEFINED_2.ordinal()) {
            throw new IllegalArgumentException("Unknown id " + id);
        }
        return values()[id];
    }
}
