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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class SettingsBase extends DefaultSettings {
    
    /**
     * Navega la llista de updatableSettings i aplica tots els canvis
     * que s'hagueren realitzat.
     */
    public void applyChanges()
    {
        for(Settings s : updatableSettings)
        {
            s.updateSettings();
        }
    }
    
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
                setting.append(separator);
            }
            
            setting.append(elm);
            
            iteration++;
        }
        
        return setting.toString();
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
        
        String[] tokens = list.split(separator);
        
        for(String token : tokens)
        {
            elements.add(Integer.parseInt(token));
        }
        
        return elements;
    }
}
