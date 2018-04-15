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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javafx.application.Platform;
import org.gespert.gladivs.Instances.SettingsInstance;
import org.gespert.gladivs.Instances.Windows;
import org.gespert.gladivs.Screenshots.CaptureRegion;
import org.gespert.gladivs.Screenshots.MonitorData;
import org.gespert.gladivs.Screenshots.Monitors;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class TrayMenuCreator {
    
    private SystemTray tray;
    private TrayIcon trayIcon;
    private PopupMenu popupMenu;
    
    /*  MenuItem DefineCapureRegion */
    private PopupMenu smDefinedCaptureArea;
    private MenuItem miViewDefinedArea;
    private MenuItem miDeleteDefinedArea;
    private PopupMenu smDefineAreaMonitor;
    
    /* MenuItem Capture Region */
    private PopupMenu smCaptureRegion;
    
    /* MenuItem Capture monitor */
    private PopupMenu smCaptureMonitor;
   
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
        trayIcon.addMouseListener(trayIconClicked);
        
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
        popupMenu = new PopupMenu();
        
        //Menu capturar monitor
        smCaptureMonitor = new PopupMenu("Capture monitor");
        
        //Menu capturar regió
        smCaptureRegion = new PopupMenu("Capture region");
     
        //MenuItem Open GladivsSSC Window
        MenuItem settingsWindowItem = new MenuItem("Settings");
        settingsWindowItem.addActionListener(settingsWindowListener);
        
        //MenúItem Exit
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(exitListener);
        
        //Afegir elements al popup
        popupMenu.add(smCaptureMonitor);
        popupMenu.add(smCaptureRegion);
        popupMenu.add(createSelectPredefinedAreaPopupMenu());
        popupMenu.add(getSeparatorItemMenu());
        popupMenu.add(settingsWindowItem);
        popupMenu.add(getHelpMenuItem());
        
        popupMenu.add(getAboutUsMenuItem());
        popupMenu.add(getSeparatorItemMenu());
        popupMenu.add(exitItem);
        
        return popupMenu;
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
    
    private PopupMenu createSelectPredefinedAreaPopupMenu()
    {
        smDefinedCaptureArea = new PopupMenu("Predefined capture area");
        
        smDefineAreaMonitor = new PopupMenu("Define area in monitor");
        
        miViewDefinedArea = new MenuItem("View selected area");
        miViewDefinedArea.addActionListener(viewSelectedAreaListener);
        
        miDeleteDefinedArea = new MenuItem("Delete selected area");
        miDeleteDefinedArea.addActionListener(deleteSelectedArea);
        
        smDefinedCaptureArea.add(smDefineAreaMonitor);
        smDefinedCaptureArea.add(miViewDefinedArea);
        smDefinedCaptureArea.add(miDeleteDefinedArea);
        
        return smDefinedCaptureArea;
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
    private ActionListener viewSelectedAreaListener = (ActionEvent e) -> {
        Platform.runLater(new Runnable() {
            public void run(){
                CaptureRegion.showSelectedRegion();
            }
        });
    };
    
    private void enableDisableSelectedRegionMenuItems()
    {
        miDeleteDefinedArea.setEnabled(SettingsInstance.getGeneralSettings().getSelectedRegion().isDefinedSelectionArea());
        miViewDefinedArea.setEnabled(SettingsInstance.getGeneralSettings().getSelectedRegion().isDefinedSelectionArea());
    }
    
    private void createMonitorMenuItems(PopupMenu popup, int action)
    {
        popup.removeAll();
        Monitors mn = new Monitors();
        ArrayList<MonitorData> monitors = mn.getMonitors();
        int i = 1;
        
        for(MonitorData monitor : monitors)
        {
            GMenuItem item = new GMenuItem("Monitor " + i, monitor, action);
            item.addActionListener(onMonitorMenuItemClick);
            popup.add(item);
            
            i++;
        }
    }
    
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
    
    private ActionListener deleteSelectedArea = (ActionEvent e) -> {
        SettingsInstance.getGeneralSettings().emptySelectedRegion();
        enableDisableSelectedRegionMenuItems();
    };
    
    private MouseListener trayIconClicked = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent arg0) {
            enableDisableSelectedRegionMenuItems();
            createMonitorMenuItems(smCaptureMonitor, GMenuItem.TAKE_SCREENSHOT);
            createMonitorMenuItems(smCaptureRegion, GMenuItem.CAPTURE_REGION);
            createMonitorMenuItems(smDefineAreaMonitor, GMenuItem.SELECT_REGION);
        }
    };
    
    private ActionListener onMonitorMenuItemClick = (ActionEvent e) -> {
        Platform.runLater(new Runnable() {
            public void run(){
                ((GMenuItem) e.getSource()).doAction();
            }
        });
    };
}
