/*
 * Copyright (C) 2017 Guillermo Espert Carrasquer
 *
 * This file is part of Gladivs Simple Screen Capture
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
package com.gladivs.gladivsssc.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.prefs.Preferences;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public abstract class KeyboardSettings extends SettingsBase {
    
    private final String APP_ROOT = "/com/gladivsssc/keyboardsettings";
    private HashMap<String, List<Integer>> settingsChanged = new HashMap<>();
    
    /*********************************
     *          GETTERS
     ********************************/
    
    /**
     * Retorna la combinacio de tecles per a la captura de pantalla
     * @return 
     */
    public List<Integer> getTakeScrenShotKeys()
    {
        return getKeysValue(KeyboardSettings.TAKE_SCREENSHOT, KeyboardSettings.TAKE_SCREENSHOT_DEF);
    }
    
    /**
     * Retorna la combinacio de tecles per a recarregar les imatges de les miniatures dels monitors
     * @return 
     */
    public List<Integer> getRefreshMonitorKeys()
    {
        return getKeysValue(KeyboardSettings.REFRESH_MONITORS, KeyboardSettings.REFRESH_MONITORS_DEF);
    }
    
    public abstract List<Integer> getKeyCombinationData();
    
    /*********************************
     *          SETTERS
     ********************************/
    
  
    /**
     * Estableix la combinacio de tecles par a la captura de pantalla
     * @param keys 
     */
    public void setRefreshMonitorKeys(List<Integer> keys)
    {
        setKeysValue(KeyboardSettings.REFRESH_MONITORS, keys, KeyboardSettings.REFRESH_MONITORS_DEF);
    }
    
    public abstract void storeKeyCombination(List<Integer> keys);
    
    /*********************************
     *        SETTING NAMES
     ********************************/
    
    //Nom per al setting de les tecles de captura de pantalla.
    protected static final String TAKE_SCREENSHOT = "TAKE_SCREENSHOT";
    
    //Nom per al setting de les tecles que permeten recarregar les imatges de les miniatures dels monitors.
    protected static final String REFRESH_MONITORS = "REFRESH_MONITORS";
    
    
    /*********************************
     *        OTHER METHODS
     ********************************/
    
    private Preferences getPreferences()
    {
        return Preferences.userRoot().node(APP_ROOT);
    }
    
    /**
     * Enmagazema la llista de tecles en el setting especificat convertit a un String.
     * Si el valor passat en nul, s'usara el valor per defecte.
     * 
     * @param setting
     * @param keys
     * @param defaultValue 
     */
    protected void setKeysValue(String setting, List<Integer> keys, List<Integer> defaultValue)
    {
        List<Integer> lKeys;
        
        if(keys != null)
        {
            lKeys = keys;
        }
        else
        {
            lKeys = defaultValue;
        }
        
        settingsChanged.put(setting, lKeys);
    }
    
    /**
     * Retorna una llista amb les tecles que corresponen al settings passat.
     * Si el setting passat no existeix o no esta disponible, el valor 
     * passat com a valor per defecte sera retornat.
     * 
     * @param settingName
     * @param defaultValue
     * @return 
     */
    protected List<Integer> getKeysValue(String settingName, List<Integer> defaultValue)
    {
        Preferences prefs = getPreferences();
        List<Integer> iKeys;
        String sKeys = prefs.get(settingName, null);
        
        if(sKeys != null)
        {
            iKeys = convertStringListToIntegerList(sKeys);
        }
        else
        {
            iKeys = defaultValue;
        }
        
        return iKeys;
    }
    
    public void updateSettings(){
    
        storeKeysValue();
        
    }
    
    /**
     * Enmagazema la llista de tecles en el setting especificat convertit a un String.
     * Si el valor passat en nul, s'usara el valor per defecte.
     * 
     * @param setting
     * @param keys
     * @param defaultValue 
     */
    public void storeKeysValue()
    {
        Preferences prefs = getPreferences();
        
        for(Entry<String, List<Integer>> e : settingsChanged.entrySet())
        {
            String sKeys = convertIntegerListToStringList(e.getValue());
            
            prefs.put(e.getKey(), sKeys);
        }
    }
}
