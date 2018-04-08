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
package org.gespert.gladivs.Settings;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class GeneralSettings extends Settings {
    
    private HashMap<String, String> settingsChanged = new HashMap<>();
    
    /*********************************
     *          SETTERS
     ********************************/
    
    public void setUserSelectedImagesSavePath(String path)
    {
        setSettingValue(USER_SELECTED_IMAGE_PATH, path);
    }
    
    public void setLastImageSavePath(String path)
    {
        setSettingValue(LAST_IMAGE_PATH, path);
    }
    
    /*********************************
     *          GETTERS
     ********************************/
    
    public String getUserSelectedImagesSavePath()
    {
        return getSettingValue(USER_SELECTED_IMAGE_PATH, USER_SELECTED_IMAGE_PATH_DEF);
    }
    
    public String getLastImageSavePath()
    {
        return getSettingValue(LAST_IMAGE_PATH, LAST_IMAGE_PATH_DEF);
    }
    
    /*********************************
     *        SETTING NAMES
     ********************************/
    
    public static final String LAST_IMAGE_PATH = "images.last-image-save-path";
    
    public static final String USER_SELECTED_IMAGE_PATH = "images.user-selected-image-save-path";
    
    /*********************************************************
     *          Valors per defecte de les preferències
     *********************************************************/
    
    private static File userDir = new File(System.getProperty("user.home") + "/Pictures/GladivsSC");
    public static final String LAST_IMAGE_PATH_DEF = userDir.getAbsolutePath();
    
    public static final String USER_SELECTED_IMAGE_PATH_DEF = userDir.getAbsolutePath();
    
    /*********************************
     *        OTHER METHODS
     ********************************/
    
    private String getSettingValue(String setting, String defaultValue)
    {
        Preferences prefs = getPreferences();
        
        return prefs.get(setting, defaultValue);
    }
    
    private void setSettingValue(String setting, String value)
    {
        settingsChanged.put(setting, value);
    }

    @Override
    public void saveSettings() {
        Preferences prefs = getPreferences();
        
        for(Map.Entry<String, String> e : settingsChanged.entrySet())
        {
            prefs.put(e.getKey(), e.getValue());
        }
        
        settingsChanged.clear();
    }
}
