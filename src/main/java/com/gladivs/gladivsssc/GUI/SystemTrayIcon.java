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
package com.gladivs.gladivsssc.GUI;

import com.gladivs.gladivsssc.Instances.WindowsInstances;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Platform;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class SystemTrayIcon {
    
   
    public void displayIconInTray()
    {
        //Obtenir la instància SystemTray de l'aplicació
        SystemTray tray = SystemTray.getSystemTray();
        
        //Crear la imatge que contindrà l'icon de la aplicació
        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/icons/logo_256.png"));
        
        //Creem un TrayIcon amb la imatge del logo i establim l'auto redimensionat de l'imatge a true
        TrayIcon trayIcon = new TrayIcon(image, "Gladivs Screen Tools", getPopupMenu());
        trayIcon.setImageAutoSize(true);
        
        //Establim el valor del ToolTip per a l'icon
        trayIcon.setToolTip("Gladivs Screen Tools");
        
        try
        {
            //Afegim el nou TrayIcon al SystemTray
            tray.add(trayIcon);
        }
        catch (AWTException ex)
        {
            System.err.println("Error amb el system tray: " + ex);
        }
        
        //Instanciar la finestra principal i el listener per a les tecles
        Platform.runLater(new Runnable() {
            @Override
            public void run(){                
                WindowsInstances.getMainWindow();
            }
        });
        
    }
    
    private PopupMenu getPopupMenu()
    {
        PopupMenu popup = new PopupMenu();
        
        //MenuItem Separator
        MenuItem separatorItem = new MenuItem("-");
     
        //MenuItem Open GladivsSSC Window
        MenuItem screencaptureWindowItem = new MenuItem("Open Screenshots main window");
        screencaptureWindowItem.addActionListener(openMainWindowListener);
        
        //MenúItem Exit
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(exitListener);
        
        //Afegir elements al popup
        popup.add(screencaptureWindowItem);
        popup.add(separatorItem);
        popup.add(exitItem);
        
        return popup;
    }
    
    /**
     * Acció per al MenuItem 'Exit'. Finalitza la aplicació
     */
    private ActionListener exitListener = (ActionEvent e) -> {
        Platform.exit();
    };
    
    
    /**
     * Acció per al MenuItem 'Open screenshots main window'
     */  
    private ActionListener openMainWindowListener = (ActionEvent e) -> {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WindowsInstances.getMainWindow().getStage().show();
            }
        });
    };
}
