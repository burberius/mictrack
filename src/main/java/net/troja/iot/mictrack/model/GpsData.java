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

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representation of some GPS data
 */
@Builder
@Data
public class GpsData implements ReportData {
    /**
     * The number of available satellites
     */
    @NonNull
    private int numberOfSatellites;
    /**
     * UTC Time of last GPS location
     */
    @NonNull
    private LocalDateTime time;
    /**
     * The device's latitude
     */
    @NonNull
    private BigDecimal latitude;
    /**
     * The device's longitude
     */
    @NonNull
    private BigDecimal longitude;
    /**
     * Current speed with the unit km/h
     */
    @NonNull
    private BigDecimal speed;
    /**
     * Track angle in degrees (0 - 359)
     */
    @NonNull
    private int heading;
    /**
     * Type of the event
     */
    @NonNull
    private EventType event;
    /**
     * Backup battery voltage
     */
    @NonNull
    private BigDecimal voltage;
    /**
     * Plus 1 for every message from 0 to 255, then reset to 0.
     */
    @NonNull
    private short sequenceNumber;
}
