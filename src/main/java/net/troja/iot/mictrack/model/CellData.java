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

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Representation of cell data
 */
@SuperBuilder
@Data
public class CellData extends BaseReportData {
    /**
     * Mobile network code
     */
    private int mnc;
    /**
     * The 16-bit (GSM) or 28-bit (LTE) cell ID. Range: 0-0xFFFFFFF
     */
    private long cellId;
    /**
     * Location area code
     */
    private int lac;
    /**
     * Mobile country code
     */
    private int mcc;
    /**
     * Physical Cell ID (only for LTE)
     */
    private int pcid;
}
