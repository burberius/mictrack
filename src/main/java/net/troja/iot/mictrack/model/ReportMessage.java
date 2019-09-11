package net.troja.iot.mictrack.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Representation of a report message
 */
@Builder
@Data
public class ReportMessage {
    /**
     * The device working mode
     * @ToDo
     */
    @NonNull
    int mode;
    /**
     * The IMEI number of the device
     */
    @NonNull
    String imei;
    /**
     * The type of the containing data
     * @see DataType
     */
    @NonNull
    DataType dataType;
    /**
     * The data contained in the report, for the type check the field dataType
     */
    @NonNull
    ReportData data;
}
