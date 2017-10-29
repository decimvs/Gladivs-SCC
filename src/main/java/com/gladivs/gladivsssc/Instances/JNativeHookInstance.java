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

import com.gladivs.gladivsssc.Events.EventsManager.JNativeHookImplementation;
import com.gladivs.gladivsssc.MainApp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 * JNativeHookImplementation singleton. 
 * 
 * Conté una sola instància de JNativeHookImplementation.
 * 
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class JNativeHookInstance {
    
    private static JNativeHookImplementation jnhInstance;

    /**
     * Este mètode verifica si ja s'ha creat una instància de JNativeHook.
     * Si existeix, la retorna. Si no, crea una nova instància i la retorna.
     */
    public static JNativeHookImplementation getInstance()
    {
        if(jnhInstance == null)
        {
            jnhInstance = new JNativeHookImplementation();
        }
        
        return jnhInstance;
    }
    
    /**
     * Closes NativeKeyHook thread and finishes application
     */
    public static void stop()
    {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
