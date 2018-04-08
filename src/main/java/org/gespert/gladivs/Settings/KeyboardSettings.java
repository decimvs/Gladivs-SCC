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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.prefs.Preferences;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class KeyboardSettings extends Settings{
    
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
     * Retorna la combinacio de tecles per a la captura d'una regió de la pantalla
     * @return 
     */
    public List<Integer> getCaptureRegionKeys()
    {
        return getKeysValue(KeyboardSettings.CAPTURE_REGION, KeyboardSettings.CAPTURE_REGION_DEF);
    }
    
    /*********************************
     *          SETTERS
     ********************************/
    
    /**
     * Estableix la combinacio de tecles per a la captura de pantalla
     * @param keys 
     */
    public void setTakeScreenshotKeys(List<Integer> keys)
    {
        setKeysValue(KeyboardSettings.TAKE_SCREENSHOT, keys, KeyboardSettings.TAKE_SCREENSHOT_DEF);
    }
  
    /**
     * Estableix la combinacio de tecles par a la captura d'una regió de la pantalla
     * @param keys 
     */
    public void setCaptureRegionKeys(List<Integer> keys)
    {
        setKeysValue(KeyboardSettings.CAPTURE_REGION, keys, KeyboardSettings.CAPTURE_REGION_DEF);
    }
    
    /*********************************
     *        SETTING NAMES
     ********************************/
    
    //Nom per al setting de les tecles de captura de pantalla.
    protected static final String TAKE_SCREENSHOT = "keyboard.keys.take-screenshot";
    
    //Nom per al setting de les tecles de captura de regió de la pantalla.
    protected static final String CAPTURE_REGION = "keyboard.keys.capture-region";
    
    /*********************************************************
     *          Valors per defecte de les preferències
     *********************************************************/
    
    //Tecla per defecte per a la captura de pantalla: Impr Pant -> 3639
    protected static final List<Integer> TAKE_SCREENSHOT_DEF = Arrays.asList(3639);
    
    //Combinacio de tecles per a la actualitzacio dels monitors: CTRL + Impr Pant -> 29 + 3639
    protected static final List<Integer> CAPTURE_REGION_DEF = Arrays.asList(29, 3639);
    
    
    /*********************************
     *        OTHER METHODS
     ********************************/
    
    /**
     * Enmagazema en la llista de tecles el setting especificat convertit a un String.
     * Si el valor passat és nul, s'usara el valor per defecte.
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
    
    /**
     * Desa els valors dels paràmetres que hajen cambiat
     */
    public void saveSettings()
    {
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
        
        settingsChanged.clear();
    }
}
