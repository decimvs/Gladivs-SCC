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
package org.gespert.gladivs.GUI.Controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.gespert.gladivs.Instances.SettingsInstance;
import org.gespert.gladivs.Instances.Windows;
import org.gespert.gladivs.Settings.KeyboardSettings;
import org.gespert.gladivs.Settings.Settings;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class SettingsDialogController implements Initializable {
    
    @FXML
    private Label lblCaptureMonitor, lblRegionCapture,lblLastRegionCapture, lblSelectRegionKeys, lblCaptureSelectedRegion;
    
    @FXML
    private Button btnSetMonitorCaptureKeys, btnSetRegionCaptureKeys, btnSetLastRegionCaptureKeys, btnSetSelectRegionKeys, btnSetCaptureSelectedRegionKeys;
    
    @FXML
    private Button btnCancel, btnApply, btnAccept, btnSelectDefaultDirectory;
    
    @FXML
    private TextField txtDefaultDirectory;
    
    private List<Settings> updatableChanges;
    private KeyboardSettings kbSettings = SettingsInstance.getKeyboardSettings();
    private String oldDefaultDirectoryValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updatableChanges = new ArrayList<>();
        
        btnSetMonitorCaptureKeys.addEventHandler(ActionEvent.ACTION, onBtnSetCaptureMonitorKeysAction);
        btnSetRegionCaptureKeys.addEventHandler(ActionEvent.ACTION, onBtnSetCaptureRegionKeysAction);
        btnSetLastRegionCaptureKeys.addEventHandler(ActionEvent.ACTION, onBtnSetCaptureLastRegionKeysAction);
        btnSetSelectRegionKeys.addEventHandler(ActionEvent.ACTION, onBtnSetSelectRegionKeysAction);
        btnSetCaptureSelectedRegionKeys.addEventHandler(ActionEvent.ACTION, onBtnSetCaptureSelectedRegionKeysAction);
        
        btnApply.addEventHandler(ActionEvent.ACTION, onBtnApplyAction);
        btnCancel.addEventHandler(ActionEvent.ACTION, onBtnCancelAction);
        btnAccept.addEventHandler(ActionEvent.ACTION, onBtnAcceptAction);
        btnSelectDefaultDirectory.addEventHandler(ActionEvent.ACTION, onBtnSelectDefaultDirectory);
        
        loadCurrentSettingsValues();
    }
    
    /**
     * Obté els paràmetres i carrega els valors en els camps corresponents.
     */
    private void loadCurrentSettingsValues()
    {
        //Establir les tecles actuals
        setCombinationKeys(kbSettings.getTakeScrenShotKeys(), lblCaptureMonitor);
        setCombinationKeys(kbSettings.getCaptureRegionKeys(), lblRegionCapture);
        setCombinationKeys(kbSettings.getCaptureLastRegionKeys(), lblLastRegionCapture);
        setCombinationKeys(kbSettings.getSelectRegionKeys(), lblSelectRegionKeys);
        setCombinationKeys(kbSettings.getCaptureSelectedRegionKeys(), lblCaptureSelectedRegion);
        
        String directoryValue = SettingsInstance.getGeneralSettings().getUserSelectedImagesSavePath();
        txtDefaultDirectory.setText(directoryValue);
        oldDefaultDirectoryValue = directoryValue;
    }
    
    /**
     * Crea la visualització de les tecles seleccionades.
     * @param keysPressed
     * @param label 
     */
    public void setCombinationKeys(List<Integer> keysPressed, Label label)
    {
        if(keysPressed.size() > 0)
        {
            int iteration = 1;
            String keys = "";
            
            for(Integer i : keysPressed)
            {
                if(i != null)
                {
                    String concat;
                
                    if(iteration != 1)
                    {
                        concat = " + ";
                    }
                    else
                    {
                        concat = "";
                    }
                
                    keys += concat + NativeKeyEvent.getKeyText(i);
                }
                
                iteration++;
            }
            
            label.setText(keys);
        }
    }
    
    /**
     * Afegeix el setting passat a la llista de paràmetres que seràn actulitzats.
     * @param st 
     */
    public void addSettingToUpdatablesList(Settings st)
    {
        if(updatableChanges.contains(st))
        {
            updatableChanges.remove(st);
        }
        
        updatableChanges.add(st);
    }
    
    /**
     * Desa tots el settings continguts dins de la llista de settings canviats.
     * Una volta finalitzada l'operació, buida la llista de settings canviats.
     */
    private void saveSettings()
    {
        //Abans de desar els paràmetres, verifiquem si el directory predeterminat
        //de desat de les imatges ha canviat o si ès vàlid.
        saveDefaultDirectory();
        
        for(Settings s : updatableChanges)
        {
            s.saveSettings();
        }
        
        updatableChanges.clear();
    }
    
    /**
     * Verifica per un costat la validesa del valor introduït al camp de text.
     * Si el valor no és una direcció vàlida, mostrarà el cercador de directoris
     * per a que l'usuari especifique una direcció vàlida.
     * 
     * Per altre costat, es verifica si el valor del camp ha canviat respecte
     * del valor que tenía quant s'ha carregat el formulari. Si el valor ha canviat
     * es desa el nou valor en la configuració.
     */
    private void saveDefaultDirectory()
    {
        File selectedDir = new File(txtDefaultDirectory.getText());
        
        if(txtDefaultDirectory.getText() == null || !verifyDefaultDirectory(selectedDir))
        {
            showErrorAlertDefaultDirectory();
            showDirectoryChooser(null);
        }
        
        if(!txtDefaultDirectory.equals(oldDefaultDirectoryValue))
        {
            SettingsInstance.getGeneralSettings().setUserSelectedImagesSavePath(selectedDir.getAbsolutePath());
            
            addSettingToUpdatablesList(SettingsInstance.getGeneralSettings());
        }
    }
    
    /**
     * Verifica si el directori passat es un directori vàlid.
     * Retorna true si es un directori vàlid o false en cas contrari.
     * @param path
     * @return 
     */
    private boolean verifyDefaultDirectory(File path)
    {  
        if(path.exists() && path.canWrite())  return true;
        
        return false;
    }
    
    private void showErrorAlertDefaultDirectory()
    {
        Alert error = new Alert(AlertType.ERROR);
        error.setTitle("Directory not valid");
        error.setHeaderText(null);
        error.setContentText("The choosen directory is a invalid path. Please select another directory.");
        error.showAndWait();
    }
    
    /**
     * Mostra un seleccionador de directoris. Si es pasa una ruta vàlida, es 
     * mostrarà com a directori d'inici, si no, es mostrarà la carpeta personal
     * de l'usuari.
     * El directori seleccionat serà verificat. Si este és vàlid, finalitza la selecció
     * i es retorna un objecte File. Si el directori no és vàlid, es mostra el
     * seleccionador de nou per a corregir el problema.
     * @param path
     * @return 
     */
    private File showDirectoryChooser(String path)
    {
        DirectoryChooser dch = new DirectoryChooser();
        File userHome = new File(System.getProperties().getProperty("user.home"));
        File selectedPath;
        
        if(path != null)
        {
            File startPath = new File(path);
            
            if(startPath.exists())
            {
                dch.setInitialDirectory(startPath);
            }
            else
            {
                dch.setInitialDirectory(userHome);
            }
        }
        else
        {
            dch.setInitialDirectory(userHome);
        }
        
        selectedPath = dch.showDialog(Windows.getSettingsDialog().getStage());
        
        if(!verifyDefaultDirectory(selectedPath))
        {
            showErrorAlertDefaultDirectory();
            showDirectoryChooser(dch.getInitialDirectory().getAbsolutePath());
        }
        
        txtDefaultDirectory.setText(selectedPath.getAbsolutePath());
        
        return selectedPath;
    }
    
    private EventHandler<ActionEvent> onBtnSelectDefaultDirectory = (evt) -> {
    
        txtDefaultDirectory.setText(showDirectoryChooser(txtDefaultDirectory.getText()).getAbsolutePath());
    };
    
    private EventHandler<ActionEvent> onBtnAcceptAction = (evt) -> {
        saveSettings();
        Windows.getSettingsDialog().getStage().close();
        Windows.destroySettingsWindowCreator();
    };
    
    private EventHandler<ActionEvent> onBtnCancelAction = (evt) -> {
        updatableChanges.clear();
        Windows.getSettingsDialog().getStage().close();
        Windows.destroySettingsWindowCreator();
    };
    
    private EventHandler<ActionEvent> onBtnApplyAction = (evt) -> {
        saveSettings();
    };
    
    private EventHandler<ActionEvent> onBtnSetCaptureMonitorKeysAction = (evt) -> {

        Windows.getKeyInputDialog().getController().setParentKeysLabel(lblCaptureMonitor);
        Windows.getKeyInputDialog().getController().setKeyboardSetting(KeysInputDialogController.TAKE_SCREENSHOT);
        Windows.getKeyInputDialog().getStage().show();
    };


    private EventHandler<ActionEvent> onBtnSetCaptureRegionKeysAction = (evt) -> {

        Windows.getKeyInputDialog().getController().setParentKeysLabel(lblRegionCapture);
        Windows.getKeyInputDialog().getController().setKeyboardSetting(KeysInputDialogController.CAPTURE_REGION);
        Windows.getKeyInputDialog().getStage().show();
    };
    
    private EventHandler<ActionEvent> onBtnSetCaptureLastRegionKeysAction = (evt) -> {

        Windows.getKeyInputDialog().getController().setParentKeysLabel(lblLastRegionCapture);
        Windows.getKeyInputDialog().getController().setKeyboardSetting(KeysInputDialogController.CAPTURE_LAST_REGION);
        Windows.getKeyInputDialog().getStage().show();
    };
    
    private EventHandler<ActionEvent> onBtnSetSelectRegionKeysAction = (evt) -> {
        Windows.getKeyInputDialog().getController().setParentKeysLabel(lblSelectRegionKeys);
        Windows.getKeyInputDialog().getController().setKeyboardSetting(KeysInputDialogController.SELECT_REGION);
        Windows.getKeyInputDialog().getStage().show();
    };
    
    private EventHandler<ActionEvent> onBtnSetCaptureSelectedRegionKeysAction = (evt) -> {
        Windows.getKeyInputDialog().getController().setParentKeysLabel(lblCaptureSelectedRegion);
        Windows.getKeyInputDialog().getController().setKeyboardSetting(KeysInputDialogController.CAPTURE_SELECTED_REGION);
        Windows.getKeyInputDialog().getStage().show();
    };
}
