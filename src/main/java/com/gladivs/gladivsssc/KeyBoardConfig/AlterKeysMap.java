/*
 * Copyright (C) 2016 Guillermo Espert Carrasquer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.gladivs.gladivsssc.KeyBoardConfig;

import org.jnativehook.keyboard.NativeKeyEvent;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public enum AlterKeysMap {
    
    CTRL_LEFT       (NativeKeyEvent.CTRL_L_MASK, true, 0, "km_left_control"),
    CTRL_RIGHT      (NativeKeyEvent.CTRL_R_MASK, true, 1, "km_right_control"),
    CTRL_BOTH       (NativeKeyEvent.CTRL_MASK, true, 2, "km_control_both"),
    ALT_LEFT        (NativeKeyEvent.ALT_L_MASK, true, 3, "km_left_alt"),
    ALT_RIGHT       (NativeKeyEvent.ALT_R_MASK, true, 4, "km_right_alt"),
    ALT_BOTH        (NativeKeyEvent.ALT_MASK, true, 5, "km_alt_both"),
    SHIFT_LEFT      (NativeKeyEvent.SHIFT_L_MASK, true, 6, "km_left_shift"),
    SHIFT_RIGHT     (NativeKeyEvent.SHIFT_R_MASK, true, 7, "km_right_shift"),
    SHIFT_BOTH      (NativeKeyEvent.SHIFT_MASK, true, 8, "km_shift_both"),
    COMMAND_LEFT    (NativeKeyEvent.META_L_MASK, true, 9, "km_left_windows_command"),
    COMMAND_RIGHT   (NativeKeyEvent.META_R_MASK, true, 10, "km_right_windows_command"),
    COMMAND_BOTH    (NativeKeyEvent.META_MASK, true, 11, "Windows/Command");

    private final Integer nativeKeyCode;
    private final boolean alterKey;
    private final int internId;
    private final String i18lnKey;

    private AlterKeysMap(Integer nativeKeyCode, boolean alterKey, int internId, String i18lnKey)
    {
        this.nativeKeyCode = nativeKeyCode;
        this.alterKey = alterKey;
        this.internId = internId;
        this.i18lnKey = i18lnKey;
    }
    
    public Integer getNativeKeyCode()
    {
        return this.nativeKeyCode;
    }
    
    public boolean isAlterKey()
    {
        return this.alterKey;
    }
    
    public Integer getInternId()
    {
        return this.internId;
    }
    
    public String getI18lnKey()
    {
        return this.i18lnKey;
    }
}
