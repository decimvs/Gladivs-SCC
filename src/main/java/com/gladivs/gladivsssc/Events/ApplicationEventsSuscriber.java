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
package com.gladivs.gladivsssc.Events;

import com.gladivs.gladivsssc.Configuration.Configuration;
import com.gladivs.gladivsssc.Configuration.Values;
import com.gladivs.gladivsssc.Events.EventsManager.GlobalKeysListener;
import com.gladivs.gladivsssc.Instances.ConfigurationInstance;
import com.gladivs.gladivsssc.Instances.GlobalListenerInstance;
import com.gladivs.gladivsssc.Instances.LoggerInstance;
import com.gladivs.gladivsssc.Instances.WindowsInstances;
import java.util.ArrayList;
import java.util.logging.Logger;
import javafx.application.Platform;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * Esta classe implementa les accions a realitzar a nivell d'aplicació en resposta
 * als events que es generen en la capa del SO i que siguen utilitzables en la 
 * aplicació.
 * 
 * Esta classe se registra en la instància de GlobalListener, que rebrá els events
 * i els transmetrà a esta implementació. Alguns criteris de control de flux i 
 * prioritats es podràn aplicar en GlobalListener.
 * 
 * Este listener es registra amb prioritat automática.
 * 
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class ApplicationEventsSuscriber implements GlobalKeysListener {
    
    private boolean isCTRLKeyPressed = false;
    private ArrayList<Integer> keysPressed;
    private Configuration configuration;
    private static final Logger LOGGER = LoggerInstance.getLogger();
    
    //ps: printscreen, rm: refreshMonitors
    private int psikey1 = -2;
    private int psikey2 = -2;
    private int psikey3 = -2;
    private int rmikey1 = -2;
    private int rmikey2 = -2;
    private int rmikey3 = -2;
    
    public ApplicationEventsSuscriber()
    {
        GlobalListenerInstance.getInstance().addListener(this);
        keysPressed = new ArrayList<>();
        configuration = ConfigurationInstance.getConfiguration();
    }
    
    @Override
    public void onGlobalKeyPressed(NativeKeyEvent nke) 
    {
        //Insert all keys pressed only one time
        if(!keysPressed.contains(nke.getKeyCode()))
        {
            keysPressed.add(nke.getKeyCode());
        }
        
        LOGGER.info("Key pressed App\n");
    }

    @Override
    public void onGlobalKeyReleased(NativeKeyEvent nke) 
    {
        //Check if released key and stored keys matches any configured combination
        //of keys and clear all keys added to arraylist
        checkKeysCombination();
        
        LOGGER.info("Key released App\n");
    }

    @Override
    public void onGlobalKeyTyped(NativeKeyEvent nke) {
        LOGGER.info("Key typed");
    }
    
    /**
     * Checks the pressed keys to match any configured combination of keys to perform 
     * an action.
     */
    private void checkKeysCombination()
    {
        //Get configured keys
        for(Values v : configuration.getUserValues().values())
        {
            switch(v.getParameterId())
            {
                case 1:
                    psikey1 = v.getIntValue();
                    break;
                case 2:
                    psikey2 = v.getIntValue();
                    break;
                case 3:
                    psikey3 = v.getIntValue();
                    break;
                case 4:
                    rmikey1 = v.getIntValue();
                    break;
                case 5:
                    rmikey2 = v.getIntValue();
                    break;
                case 6:
                    rmikey3 = v.getIntValue();
                    break;
            }
        }
        
        //Check for 1 key combination
        if(keysPressed.size() == 1)
        {
            //Check for take Screenshot
            if(psikey1 == -1 && psikey2 == -1  &&  checkKeys(psikey3, keysPressed.get(0)))
            {
                takeScreenShot();
            }
            //Check for moniatures refresh
            else if(rmikey1 == -1 && rmikey2 == -1  &&  checkKeys(rmikey3, keysPressed.get(0)))
            {
                refreshMonitorMiniatures();
            }
        }
        //Check for 2 keys combination
        else if(keysPressed.size() == 2)
        {
            //Check for take Screenshot
            if(checkKeys(psikey1, keysPressed.get(0)) && psikey2 == -1 && checkKeys(psikey3, keysPressed.get(1)))
            {
                takeScreenShot();
            }
            //Check for moniatures refresh
            else if(checkKeys(rmikey1, keysPressed.get(0)) && rmikey2 == -1 && checkKeys(rmikey3, keysPressed.get(1)))
            {
                refreshMonitorMiniatures();
            }
        }
        //Check for 3 keys combination
        else if(keysPressed.size() == 3)
        {
            //Check for take Screenshot
            if(checkKeys(psikey1, keysPressed.get(0)) && checkKeys(psikey2, keysPressed.get(1)) && checkKeys(psikey3, keysPressed.get(2)))
            {
                takeScreenShot();
            }
            //Check for moniatures refresh
            else if(checkKeys(rmikey1, keysPressed.get(0)) && checkKeys(rmikey2, keysPressed.get(1)) && checkKeys(rmikey3, keysPressed.get(2)))
            {
                refreshMonitorMiniatures();
            }
        }
        
        //Empty the key codes pressed array
        keysPressed.clear();
    }
    
    private void takeScreenShot()
    {
        Platform.runLater(()-> { 
            WindowsInstances.getMainWindow().getController().takeScreenshotButtonActionPerformed();
        });
    }
    
    private void refreshMonitorMiniatures()
    {
        Platform.runLater(()-> { 
            WindowsInstances.getMainWindow().getController().refreshAllMonitorMiniatures();
        });
    }
    
    /**
     * Check if the keys codes passed are valid configured keys.
     * That is useful for detect duplicate keys like Control, Shift, that can be pressed
     * on the left or right side.
     * @param keyConfigured
     * @param keyPressed
     * @return 
     */
    private boolean checkKeys(int keyConfigured, int keyPressed)
    {
        //For CTRL keys
        if(keyConfigured == 99250 && (keyPressed == 29 || keyPressed == 3613))
        {
            return true;
        }
        //For ALT keys
        else if(keyConfigured == 99251 && (keyPressed == 56 || keyPressed == 3640))
        {
            return true;
        }
        //For Shift keys
        else if(keyConfigured == 99252 && (keyPressed == 42 || keyPressed == 54))
        {
            return true;
        }
        //For Command/Windows keys
        else if(keyConfigured == 99253 && (keyPressed == 3675 || keyPressed == 3676))
        {
            return true;
        }
        //For all other keys
        else if(keyConfigured == keyPressed)
        {
            return true;
        }
        //Nothing before
        else
        {
            return false;
        }
    }
}
