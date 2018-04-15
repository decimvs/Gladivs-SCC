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
import org.jnativehook.keyboard.NativeKeyEvent;

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
        return getKeysValue(TAKE_SCREENSHOT, TAKE_SCREENSHOT_DEF);
    }
    
    /**
     * Retorna la combinacio de tecles per a la captura d'una regió de la pantalla
     * @return 
     */
    public List<Integer> getCaptureRegionKeys()
    {
        return getKeysValue(CAPTURE_REGION, CAPTURE_REGION_DEF);
    }
    
    /**
     * Retorna la combinacio de tecles per a la captura de l'última regió de la pantalla
     * capturada.
     * @return 
     */
    public List<Integer> getCaptureLastRegionKeys()
    {
        return getKeysValue(CAPTURE_LAST_REGION, CAPTURE_LAST_REGION_DEF);
    }
    
    /**
     * Retorna la combinacio de tecles per a seleccionar una regió de la pantalla
     * @return 
     */
    public List<Integer> getSelectRegionKeys()
    {
        return getKeysValue(SELECT_REGION, SELECT_REGION_DEF);
    }
    
    /**
     * Retorna la combinacio de tecles per a capturar una regió de la pantalla
     * preseleccionada.
     * @return 
     */
    public List<Integer> getCaptureSelectedRegionKeys()
    {
        return getKeysValue(CAPTURE_SELECTED_REGION, CAPTURE_SELECTED_REGION_DEF);
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
    
    /**
     * Estableix la combinacio de tecles par a la captura d'una regió de la pantalla
     * capturada anteriorment
     * @param keys 
     */
    public void setCaptureLastRegionKeys(List<Integer> keys)
    {
        setKeysValue(KeyboardSettings.CAPTURE_LAST_REGION, keys, KeyboardSettings.CAPTURE_LAST_REGION_DEF);
    }
    
    /**
     * Estableix la combinacio de tecles par a la selecció d'una regió de la pantalla
     * @param keys 
     */
    public void setSelectRegionKeys(List<Integer> keys)
    {
        setKeysValue(SELECT_REGION, keys, SELECT_REGION_DEF);
    }
    
    /**
     * Estableix la combinacio de tecles per a la captura d'una regió de la pantalla
     * preseleccionada
     * @param keys 
     */
    public void setCaptureSelectedRegionKeys(List<Integer> keys)
    {
        setKeysValue(CAPTURE_SELECTED_REGION, keys, CAPTURE_SELECTED_REGION_DEF);
    }
    
    /*********************************
     *          EMPTY
     ********************************/
    
    /**
     * Estableix la combinacio de tecles per a la captura de pantalla
     * @param keys 
     */
    public void emptyTakeScreenshotKeys()
    {
        emptySettingValue(KeyboardSettings.TAKE_SCREENSHOT);
    }
  
    /**
     * Estableix la combinacio de tecles par a la captura d'una regió de la pantalla
     * @param keys 
     */
    public void emptyCaptureRegionKeys()
    {
        emptySettingValue(KeyboardSettings.CAPTURE_REGION);
    }
    
    /**
     * Estableix la combinacio de tecles par a la captura d'una regió de la pantalla
     * capturada anteriorment
     * @param keys 
     */
    public void emptyCaptureLastRegionKeys()
    {
        emptySettingValue(KeyboardSettings.CAPTURE_LAST_REGION);
    }
    
    /**
     * Estableix la combinacio de tecles par a la selecció d'una regió de la pantalla
     * @param keys 
     */
    public void emptySelectRegionKeys()
    {
        emptySettingValue(SELECT_REGION);
    }
    
    /**
     * Estableix la combinacio de tecles per a la captura d'una regió de la pantalla
     * preseleccionada
     * @param keys 
     */
    public void emptyCaptureSelectedRegionKeys()
    {
        emptySettingValue(CAPTURE_SELECTED_REGION);
    }
    
    /*********************************
     *        SETTING NAMES
     ********************************/
    
    //Nom per al setting de les tecles de captura de pantalla.
    protected static final String TAKE_SCREENSHOT = "keyboard.keys.take-screenshot";
    
    //Nom per al setting de les tecles de captura de regió de la pantalla.
    protected static final String CAPTURE_REGION = "keyboard.keys.capture-region";
    
    //Nom per al setting de les tecles de captura de la ùltima regió capturada de la pantalla.
    protected static final String CAPTURE_LAST_REGION = "keyboard.keys.capture-last-region";
    
    //Nom per al setting de les tecles de selecció de regió de la pantalla
    protected static final String SELECT_REGION = "keyboard.keys.select-region";
    
    //Nom per al setting de les tecles de captura de regió preseleccionada de la pantalla
    protected static final String CAPTURE_SELECTED_REGION = "keyboard.keys.capture-selected-region";
    
    /*********************************************************
     *          Valors per defecte de les preferències
     *********************************************************/
    
    //Tecla per defecte per a la captura de pantalla: Impr Pant -> 3639
    protected static final List<Integer> TAKE_SCREENSHOT_DEF = Arrays.asList(NativeKeyEvent.VC_PRINTSCREEN);
    
    //Combinacio de tecles per a la captura d'una regió de la pantalla: CTRL + Impr Pant -> 29 + 3639
    protected static final List<Integer> CAPTURE_REGION_DEF = Arrays.asList(NativeKeyEvent.VC_CONTROL_L, NativeKeyEvent.VC_PRINTSCREEN);
    
    //Combinacio de tecles per a la captura d'una regió capturada anteriorment: CTRL + Shift + Impr Pant -> 29 + 42 + 3639
    protected static final List<Integer> CAPTURE_LAST_REGION_DEF = Arrays.asList(NativeKeyEvent.VC_CONTROL_L, NativeKeyEvent.VC_SHIFT_L, NativeKeyEvent.VC_PRINTSCREEN);
    
    //Combinacio de tecles per a la definició d'una regió de la pantalla: ALT + Impr Pant -> 56 + 3639
    protected static final List<Integer> SELECT_REGION_DEF = Arrays.asList(NativeKeyEvent.VC_ALT_L, NativeKeyEvent.VC_PRINTSCREEN);
    
    //Combinacio de tecles per a la captura d'una regió preseleccionada: ALT + Shift + Impr Pant -> 56 + 42 + 3639
    protected static final List<Integer> CAPTURE_SELECTED_REGION_DEF = Arrays.asList(NativeKeyEvent.VC_ALT_L, NativeKeyEvent.VC_SHIFT_L, NativeKeyEvent.VC_PRINTSCREEN);
    
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
