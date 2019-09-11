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

public enum EventType {
    // ID 6 + 7 are not described in the documentation, we use UNDEFINED_1 and UNDEFINED_2 here to fill that hole.
    DEVICE_POWER_ON, SERVER_CONNECTED, GPS_REPORT, WIFI_REPORT, CELL_REPORT, SOS_REPORT, UNDEFINED_1, UNDEFINED_2,
    LOW_BATTERY, ENTER_GEOFENCE, LEAVE_GEOFENCE, HEARBEAT, POWER_OFF, CHARGING, CHARGING_FULL,
    POWER_ON_UNDER_POWER_SAVING_MODE, POWER_OFF_UNDER_POWER_SAVING_MODE;

    public static final EventType[] values = values();

    public static EventType forId(int id) {
        if (id < 0 || id > values().length || id == UNDEFINED_1.ordinal() || id == UNDEFINED_2.ordinal()) {
            throw new IllegalArgumentException("Unknown id " + id);
        }
        return values()[id];
    }
}
