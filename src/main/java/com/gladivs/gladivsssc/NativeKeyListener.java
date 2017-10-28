/*
 * Copyright (C) 2016 Guillermo Espert Carrasquer [gespert@yahoo.es]
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

package com.gladivs.gladivsssc;

import com.gladivs.gladivsssc.Configuration.Configuration;
import com.gladivs.gladivsssc.Configuration.Values;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 *
 * @author gespe
 */
public class NativeKeyListener implements org.jnativehook.keyboard.NativeKeyListener, Runnable {

    private MainController mController;
    private String threadName;
    private Thread thread;
    private boolean isCTRLKeyPressed = false;
    private ArrayList<Integer> keysPressed;
    private Configuration configuration;
    
    //ps: printscreen, rm: refreshMonitors
    private int psikey1 = -2;
    private int psikey2 = -2;
    private int psikey3 = -2;
    private int rmikey1 = -2;
    private int rmikey2 = -2;
    private int rmikey3 = -2;
    
    public NativeKeyListener(String tn, MainController mc, Configuration conf)
    {
        threadName = tn;
        mController = mc;
        thread = null;
        keysPressed = new ArrayList<Integer>();
        this.configuration = conf;
    }
    
    @Override
    public void run() 
    {        
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        
        GlobalScreen.addNativeKeyListener(this);
        
        // Get the logger for "org.jnativehook" and set the level to warning.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
    }
    
    public void start()
    {
        if(thread == null)
        {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }
    
    /**
     * Closes NativeKeyHook thread and finishes application
     */
    public void stop()
    {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent nke)
    {
        //Insert all keys pressed only one time
        if(!keysPressed.contains(nke.getKeyCode()))
        {
            keysPressed.add(nke.getKeyCode());
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke)
    {
        //Check if released key and stored keys matches any configured combination
        //of keys and clear all keys added to arraylist
        checkKeysCombination();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        //Not used
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
            mController.takeScreenshotButtonActionPerformed();
        });
    }
    
    private void refreshMonitorMiniatures()
    {
        Platform.runLater(()-> { 
            mController.refreshAllMonitorMiniatures();
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
