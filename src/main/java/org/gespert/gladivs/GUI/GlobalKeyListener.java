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
package org.gespert.gladivs.GUI;

import java.util.ArrayList;
import javafx.application.Platform;
import org.gespert.gladivs.Instances.SettingsInstance;
import org.gespert.gladivs.Screenshots.CaptureRegion;
import org.gespert.gladivs.Screenshots.TakeScreenshot;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class GlobalKeyListener implements NativeKeyListener {
    
    private ArrayList<Integer> keysPressed;
    private ArrayList<GlobalKeyConsumer> keysConsumers;
    
    public GlobalKeyListener()
    {
        keysPressed = new ArrayList<>();
        keysConsumers = new ArrayList<>();
    }
    
    public void addKeyConsumer(GlobalKeyConsumer gkc)
    {
        keysConsumers.add(gkc);
    }
    
    public ArrayList<GlobalKeyConsumer> getKeyConsumers()
    {
        return keysConsumers;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
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
        checkKeysPressed();
        
        keysPressed.clear();
        
        //Cridar als consumidors d'este event si existeixen
        keysConsumers.forEach(kc -> {
            Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        kc.keyReleasedNofier(nke);
                    }
            });                    
        });
    }
    
    /**
     * Comprova si les tecles pressionades coincideixen amb alguna combinació
     * existent a la configuració.
     * Primer es verifica si al menys la combinació de tecles coincideix en llargària
     * amb les combinacions disponibles a la configuració. Si hi ha coincidència,
     * es realitza la segona comprobació, que és, la de coincidència de tecles i ordre.
     */
    private void checkKeysPressed()
    {
        ArrayList<Integer> takeScreenshot = (ArrayList<Integer>) SettingsInstance.getKeyboardSettings().getTakeScrenShotKeys();
        ArrayList<Integer> captureRegion = (ArrayList<Integer>) SettingsInstance.getKeyboardSettings().getCaptureRegionKeys();
        ArrayList<Integer> selectRegion = (ArrayList<Integer>) SettingsInstance.getKeyboardSettings().getSelectRegionKeys();
        
        //Verifica si la combinació de tecles correspon amb la captura de pantalla.
        if(takeScreenshot.size() == keysPressed.size())
        {
            if(verifyKeysPressed(takeScreenshot))
            {
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        TakeScreenshot.Do();
                    }
                    
                });
          
                System.out.println("Take screenshot!");
            }
        }
        
        //Verifica si la combinació de tecles correspon amb la captura de regió.
        if(captureRegion.size() == keysPressed.size())
        {
            if(verifyKeysPressed(captureRegion))
            {
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        CaptureRegion.Do();
                    }
                    
                });
                
                System.out.println("Capture region!");
            }
        }
        
        //Verifica si la combinació de tecles correspon amb la selecció d'una regió
        if(selectRegion.size() == keysPressed.size())
        {
            if(verifyKeysPressed(selectRegion))
            {
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        CaptureRegion.setRegion();
                    }
                    
                });
                
                System.out.println("Select region!");
            }
        }
    }
    
    /**
     * Verifica si les tecles pressionades coincideixen amb les tecles 
     * que se li passa a la funció.
     * La evaluació es fa en curtcircuit, si una tecla no coincideix, es
     * retorna false i es para la verificació.
     * Si totes les tecles coincideixen, es retorna true.
     * 
     * @param settingsKeys
     * @return 
     */
    private boolean verifyKeysPressed(ArrayList<Integer> settingsKeys)
    {
        for(int i = 0; i < settingsKeys.size(); i++)
        {
            if(settingsKeys.get(i).intValue() != keysPressed.get(i).intValue())
            {
                return false;
            }
        }

        return true;
    }
}
