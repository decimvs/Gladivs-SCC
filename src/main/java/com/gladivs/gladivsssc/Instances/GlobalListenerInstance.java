/*
 * Copyright (C) 2017 Guillermo Espert Carrasquer
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
package com.gladivs.gladivsssc.Instances;

import com.gladivs.gladivsssc.Events.EventsManager.GlobalListener;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class GlobalListenerInstance {
    
    private static GlobalListener glInstance;
    
    /**
     * Crea, si no existeix, i retorna una instància de GlobalListener.
     * 
     * GlobalListener es la classe on deguem suscriure els listeners finals
     * per als events globals.
     * 
     * @return 
     */
    public static GlobalListener getInstance()
    {
        if(glInstance == null)
        {
            glInstance = new GlobalListener();
        }
        
        return glInstance;
    }
}
