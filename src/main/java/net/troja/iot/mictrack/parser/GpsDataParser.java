package net.troja.iot.mictrack.parser;

import net.troja.iot.mictrack.model.EventType;
import net.troja.iot.mictrack.model.GpsData;

public class GpsDataParser extends AbstractReportDataParser<GpsData> {
    public GpsDataParser() {
        super(9);
    }

    @Override
    public GpsData parse(String data) {
        String[] fields = splitAndCheck(data);
        return GpsData.builder()
                .numberOfSatellites(Integer.parseInt(fields[0]))
                .time(parseUtcTime(fields[1]))
                .latitude(parseDouble(fields[2]))
                .longitude(parseDouble(fields[3]))
                .speed(parseDouble(fields[4]))
                .heading(Integer.parseInt(fields[5]))
                .event(EventType.values[Integer.parseInt(fields[6])])
                .voltage(parseVoltage(fields[7]))
                .sequenceNumber(Short.parseShort(fields[8]))
                .build();
    }
}
