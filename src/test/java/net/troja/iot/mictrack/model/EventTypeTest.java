package net.troja.iot.mictrack.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventTypeTest {
    @Test
    public void checkOrder() {
        assertEquals(EventType.DEVICE_POWER_ON, EventType.forId(0));
        assertEquals(EventType.POWER_OFF, EventType.forId(12));
        assertEquals(EventType.POWER_OFF_UNDER_POWER_SAVING_MODE, EventType.forId(16));
    }

    @Test
    public void negativId() {
        assertThrows(IllegalArgumentException.class, () -> EventType.forId(-1));
    }

    @Test
    public void unknownId() {
        assertThrows(IllegalArgumentException.class, () -> EventType.forId(20));
    }

    @Test
    public void holeIds() {
        assertThrows(IllegalArgumentException.class, () -> EventType.forId(6));
        assertThrows(IllegalArgumentException.class, () -> EventType.forId(7));
    }
}
