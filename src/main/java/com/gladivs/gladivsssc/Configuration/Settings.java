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

import java.util.List;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class Settings {
    
    protected String separator = ",";
    
    protected List<Settings> updatableSettings;
    
    /**
     * Es un mètode que deu ser sobreescrit en cada classe filla
     * per a permetre una actualització de les configuracions de forma
     * generalitzada i global amb només una acció.
     */
    public void updateSettings(){}
    
    protected void addUpdatableSettings(Settings e)
    {
        updatableSettings.add(e);
    }
   
}
