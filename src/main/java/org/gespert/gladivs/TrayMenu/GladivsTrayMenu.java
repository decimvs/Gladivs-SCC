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
package org.gespert.gladivs.TrayMenu;

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
import org.gespert.gladivs.Instances.Windows;
import org.gespert.gladivs.Screenshots.CaptureRegion;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class GladivsTrayMenu {
    
    private SystemTray tray;
    private TrayIcon trayIcon;
   
    public void displayIconInTray()
    {
        //Obtenir la instància SystemTray de l'aplicació
        tray = SystemTray.getSystemTray();
        
        //Crear la imatge que contindrà l'icon de la aplicació
        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/icons/logo_256.png"));
        
        //Creem un TrayIcon amb la imatge del logo i establim l'auto redimensionat de l'imatge a true
        trayIcon = new TrayIcon(image, "Gladivs Screen Tools", getPopupMenu());
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
    }
    
    public void removeIconFromTray()
    {
        tray.remove(trayIcon);
    }
    
    /**
     * Genera el popup menú
     * @return 
     */
    private PopupMenu getPopupMenu()
    {
        PopupMenu popup = new PopupMenu();
     
        //MenuItem Open GladivsSSC Window
        MenuItem settingsWindowItem = new MenuItem("Settings");
        settingsWindowItem.addActionListener(settingsWindowListener);
        
        //MenúItem Exit
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(exitListener);
        
        //Afegir elements al popup
        popup.add(getSelectPredefinedAreaPopupMenu());
        popup.add(getSeparatorItemMenu());
        popup.add(settingsWindowItem);
        popup.add(getHelpMenuItem());
        
        popup.add(getAboutUsMenuItem());
        popup.add(getSeparatorItemMenu());
        popup.add(exitItem);
        
        return popup;
    }
    
    private MenuItem getSeparatorItemMenu()
    {
        return new MenuItem("-");
    }
    
    private MenuItem getAboutUsMenuItem()
    {
        MenuItem aboutUs = new MenuItem("About Us");
        aboutUs.addActionListener(aboutUsDialogListener);
        
        return aboutUs;
    }
    
    private MenuItem getHelpMenuItem()
    {
        MenuItem help = new MenuItem("Help");
        
        return help;
    }
    
    private PopupMenu getSelectPredefinedAreaPopupMenu()
    {
        PopupMenu subMenu = new PopupMenu("Predefined capture area");
        
        MenuItem viewArea = new MenuItem("View selected area");
        MenuItem deleteArea = new MenuItem("Delete selected area");
        
        subMenu.add(viewArea);
        subMenu.add(deleteArea);
        
        return subMenu;
    }
    
    /**
     * Acció per al MenuItem 'Exit'. Finalitza la aplicació
     */
    private ActionListener exitListener = (ActionEvent e) -> {
        Platform.exit();
    };
    
    
    
    /**
     * Acció per al MenuItem 'Open settings window'
     */  
    private ActionListener settingsWindowListener = (ActionEvent e) -> {
        Platform.runLater(new Runnable() {
            public void run(){
                Windows.getSettingsDialog().getStage().show();
            }
        });
    };
    
    private ActionListener aboutUsDialogListener = (ActionEvent e) -> {
        Platform.runLater(new Runnable() {
            public void run(){
                Windows.getAboutUsDialog().getStage().show();
            }
        });
    };
}
