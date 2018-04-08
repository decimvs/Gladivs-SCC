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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public abstract class Settings {
    
    //Adreça arrel per a l'arbre de preferències de la aplicació
    protected static final String APP_ROOT = "org/gespert/gladivs/settings";
    
    //Separador per defecte en les llistes de valors encapsulades en un tipus String
    protected final String SEPARATOR = ",";
    
    /**
     * Este mètode converteix una llista d'elements de tipus Integer en un 
     * String que es podrà desar amb les preferències com a un String.
     * 
     * @param list
     * @return 
     */
    protected String convertIntegerListToStringList(List<Integer> list)
    {
        int iteration = 0;
        StringBuilder setting = new StringBuilder();
        
        for(Integer elm : list)
        {
            if(iteration > 0 && iteration < list.size())
            {
                setting.append(SEPARATOR);
            }
            
            setting.append(elm);
            
            iteration++;
        }
        
        return setting.toString();
    }
    
    /**
     * Retorna una instància de les preferències de la aplicació
     * @return 
     */
    protected Preferences getPreferences()
    {
        return Preferences.userRoot().node(APP_ROOT);
    }
    
    /**
     * Converteix una llista de valors de tipus INT encapsulats dins d'un
     * String a una llista de valor de tipus Integer.
     * 
     * @param list
     * @return 
     */
    protected List<Integer> convertStringListToIntegerList(String list)
    {
        ArrayList<Integer> elements = new ArrayList();
        
        String[] tokens = list.split(SEPARATOR);
        
        for(String token : tokens)
        {
            elements.add(Integer.parseInt(token));
        }
        
        return elements;
    }
    
    /**
     * Desa els valors de tots els paràmetres que hagen sigut modificats
     */
    public abstract void saveSettings();
}