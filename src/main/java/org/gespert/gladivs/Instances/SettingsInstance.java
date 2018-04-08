/*
 * Copyright (C) 2018 Guillermo Espert Carrasquer
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
package org.gespert.gladivs.Instances;

import org.gespert.gladivs.Settings.GeneralSettings;
import org.gespert.gladivs.Settings.KeyboardSettings;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class SettingsInstance {
    
    private static KeyboardSettings kbSettings;
    private static GeneralSettings gnSettings;
    
    public static KeyboardSettings getKeyboardSettings()
    {
        if(kbSettings == null)
        {
            kbSettings = new KeyboardSettings();
        }
        
        return kbSettings;
    }
    
    public static GeneralSettings getGeneralSettings()
    {
        if(gnSettings == null)
        {
            gnSettings = new GeneralSettings();
        }
        
        return gnSettings;
    }
}
