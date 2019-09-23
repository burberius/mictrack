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
public class CellFields {
    public static final int CELL_FIELDS_LENGTH_LTE = 5;
    public static final int CELL_FIELDS_LENGTH_GSM = 4;
    /**
     * Mobile network code
     */
    @NonNull
    private int mnc;
    /**
     * The 16-bit (GSM) or 28-bit (LTE) cell ID. Range: 0-0xFFFFFFF
     */
    @NonNull
    private long cellId;
    /**
     * Location area code
     */
    @NonNull
    private int lac;
    /**
     * Mobile country code
     */
    @NonNull
    private int mcc;
    /**
     * Physical Cell ID (only for LTE)
     */
    private int pcid;
}
