package net.troja.iot.mictrack.parser;

import net.troja.iot.mictrack.model.ReportData;

public interface ReportDataParser<S extends ReportData> {
    S parse(String data);
}
