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

@Builder
@Data
public class WifiFields {
    /**
     * First MAC address of some access point (AP)
     */
    @NonNull
    private String mac1;
    /**
     * Received Signal Strength Indication (first AP)
     */
    @NonNull
    private int rssi1;
    /**
     * Second MAC address of some access point (AP)
     */
    @NonNull
    private String mac2;
    /**
     * Received Signal Strength Indication (second AP)
     */
    @NonNull
    private int rssi2;
}
