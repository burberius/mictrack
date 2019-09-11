package net.troja.iot.mictrack.model;

import net.troja.iot.mictrack.ProtocolParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataTypeTest {
    @Test
    public void parseNull() {
        assertThrows(ProtocolParseException.class, () -> DataType.parse(null));
    }

    @Test
    public void parseEmpty() {
        assertThrows(ProtocolParseException.class, () -> DataType.parse(""));
    }

    @Test
    public void parseUnknown() {
        assertThrows(ProtocolParseException.class, () -> DataType.parse("XX"));
    }
}
