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
