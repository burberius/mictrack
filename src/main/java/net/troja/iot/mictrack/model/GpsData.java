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
