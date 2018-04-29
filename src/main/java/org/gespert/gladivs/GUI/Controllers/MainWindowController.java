/*
 * Copyright (C) 2016 - 2018 Guillermo Espert Carrasquer [gespert@yahoo.es]
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

package org.gespert.gladivs.GUI.Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import org.gespert.gladivs.GUI.MonitorMiniature;
import org.gespert.gladivs.Instances.SettingsInstance;
import org.gespert.gladivs.Instances.Windows;
import org.gespert.gladivs.Screenshots.MonitorData;
import org.gespert.gladivs.Screenshots.Monitors;

public class MainWindowController implements Initializable {
    
    @FXML
    private HBox contenedorImatges;
    @FXML
    private Label lblFolder, lblUpdateInfo, txtSelectWhatMonitors;
    @FXML
    private Button btnAboutUs, btnHelp, btnSettings;
    @FXML
    private Hyperlink hlkDownloadUpdate;
            
    private String updateDownloadLink;
    private ResourceBundle rb;
    private String defaultLanguage;    

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //Events
        lblFolder.addEventFilter(MouseEvent.MOUSE_RELEASED, onLblFolderMouseButtonReleased);
        btnAboutUs.addEventFilter(ActionEvent.ACTION, onButtonAboutUsActionEvent);
        btnHelp.addEventHandler(ActionEvent.ACTION, onButtonHelpActionPerformed);
        btnSettings.addEventHandler(ActionEvent.ACTION, onBtnSettingsActionPerformed);
        
        //Set background color for monitor pane
        contenedorImatges.setStyle("-fx-background-color: #000000;");
    }

    /**
     * Este mètode reatlitza accions necessàries després de l'inicialització
     */
    public void postInicilizationActions()
    {
        //Setting default language if users has selected one
        if(defaultLanguage != null)
        {
            Locale.setDefault(new Locale(defaultLanguage));
        }
        
        //Load with default locale (set or by user o JVM default if not is set by user)
        this.rb = ResourceBundle.getBundle("bundles.Main");
        
        /*if(configuration.getUserValues().get(configuration.CHECK_UPDATES).getIntValue() == 1){
            chmnitCheckForUpdates.setSelected(true);
        } else {
            chmnitCheckForUpdates.setSelected(false);
        }*/
        
        /*if(configuration.getUserValues().get(configuration.AUTOHIDE_WINDOW).getIntValue() == 0)
        {
            chmnitAutohideWindow.setSelected(false);
        }
        else
        {
            chmnitAutohideWindow.setSelected(true);
        }*/
        
        //Populate monitor pane
        populateMonitorContainer();
        
        //Translate UI
        translateUI();
        
        //Set the tooltips for some UI elements
        setUiElementsToolTips();
        
        //Establir la ruta de la carpeta seleccionada per a desar les imatges
        lblFolder.setText(SettingsInstance.getGeneralSettings().getUserSelectedImagesSavePath());
    }
    
    /**
     * Translate texts in UI elemnts for the current Locale
     */
    private void translateUI()
    {
        //Set UI strings
        txtSelectWhatMonitors.setText(rb.getString("select_what_monitors_you_want_to_capture"));
        
        //Recalculate UI size to arrange all elements inside
        Windows.getMainWindow().getStage().sizeToScene();
    }
    
    private void setUiElementsToolTips()
    {
        btnAboutUs.setTooltip(new Tooltip(rb.getString("button_about_us_tooltip")));
        btnHelp.setTooltip(new Tooltip(rb.getString("button_help_tooltip")));
    }
    
    private void populateMonitorContainer()
    {
        Monitors monitors = new Monitors();
        ArrayList<MonitorData> alMonitors = monitors.getMonitors();

        for(MonitorData monitor : alMonitors)
        {
            MonitorMiniature mm = new MonitorMiniature(monitor);
            contenedorImatges.getChildren().add(mm.createMiniature());
        }
    }
    
    //#################
    //
    //  EVENTS
    //
    //#################
    
    private EventHandler<MouseEvent> onLblFolderMouseButtonReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getButton() == MouseButton.PRIMARY){
                DirectoryChooser fc = new DirectoryChooser();
                File savePath = new File(SettingsInstance.getGeneralSettings().getUserSelectedImagesSavePath());
                fc.setInitialDirectory(savePath);
                
                fc.setTitle(rb.getString("select_folder_dialog_title"));
                File file = fc.showDialog(Windows.getMainWindow().getStage());
                
                if (file != null && file.isDirectory() && file.canWrite()) {
                    SettingsInstance.getGeneralSettings().setUserSelectedImagesSavePath(file.getAbsolutePath());
                    SettingsInstance.getGeneralSettings().saveSettings();
                    lblFolder.setText(file.getAbsolutePath());
                }
            }
        }
    };
    
    private EventHandler<ActionEvent> onButtonAboutUsActionEvent = (event) -> {
        Windows.getAboutUsDialog().getStage().show();
    };
    
    private EventHandler<ActionEvent> onBtnSettingsActionPerformed = (event) -> {
        Windows.getSettingsDialog().getStage().show();
    };
    
    private EventHandler<ActionEvent> onUpdateDownloadLinkActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Desktop.getDesktop().browse(URI.create(updateDownloadLink));
            } catch (IOException ex) {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    private EventHandler<ActionEvent> onButtonHelpActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String helpLanguage;
            
            if(defaultLanguage != null)
            {
                if(defaultLanguage.equals("es") || defaultLanguage.equals("en"))
                {
                    helpLanguage = defaultLanguage;
                }
                else
                {
                    helpLanguage = "en";
                }
            }
            else
            {
                helpLanguage = "en";
            }
            
            String fullUrl = "https://gladivs.com/" + helpLanguage + "/manual/gladivs-ssc-0-6";
            
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URL(fullUrl).toURI());
                } catch (IOException | URISyntaxException e) {
                    System.out.println("Error quant s'intentava obrir un navegador per mostrar la ajuda.");
                }
            }
        }
    };
}
