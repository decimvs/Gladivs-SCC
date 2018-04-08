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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.gespert.gladivs.GUI.GlobalKeyListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class KeysListener {
    
    private static GlobalKeyListener gkListener;
    
    public static void registerHook()
    {
        try {
            GlobalScreen.registerNativeHook();
            
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.WARNING);
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
        }
    }
    
    public static void unregisterHook()
    {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            Logger.getLogger(KeysListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static GlobalKeyListener getGKListener()
    {
        if(gkListener == null)
        {
            gkListener = new GlobalKeyListener();
        }
        
        return gkListener;
    }
    
    public static void addKeyListener(NativeKeyListener nl)
    {
        GlobalScreen.addNativeKeyListener(nl);
    }
    
    public static void addGlobalKeyListener()
    {
        addKeyListener(getGKListener());
    }
    
    public static void removeKeyListener(NativeKeyListener nl)
    {
        GlobalScreen.removeNativeKeyListener(nl);
    }
    
    public static void removeGlobalKeyListener()
    {
        removeKeyListener(getGKListener());
    }
}
