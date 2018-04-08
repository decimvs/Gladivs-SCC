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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.gespert.gladivs.Instances.KeysListener;
import org.gespert.gladivs.Instances.SettingsInstance;
import org.gespert.gladivs.Instances.Windows;
import org.gespert.gladivs.Settings.KeyboardSettings;
import org.gespert.gladivs.Settings.Settings;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class SettingsDialogController implements Initializable {
    
    @FXML
    private Label lblImprPantalla, lblRecarregarMiniatures;
    
    @FXML
    private Button btnModificarImprPant, btnModificarRecarregarMiniatures;
    
    @FXML
    private Button btnCancel, btnApply, btnAccept;
    
    private List<Settings> updatableChanges;
    private KeyboardSettings kbSettings = SettingsInstance.getKeyboardSettings();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updatableChanges = new ArrayList<>();
        
        btnModificarImprPant.addEventHandler(ActionEvent.ACTION, onBtnModificarImprPantAction);
        btnModificarRecarregarMiniatures.addEventHandler(ActionEvent.ACTION, onBtnModificarRecarregarMiniaturesAction);
        btnApply.addEventHandler(ActionEvent.ACTION, onBtnApplyAction);
        
        loadConfiguredCombinationKeys();
    }
    
    /**
     * Carrega les configuracions de tecles en cada label corresponent
     */
    private void loadConfiguredCombinationKeys()
    {        
        setCombinationKeys(kbSettings.getTakeScrenShotKeys(), lblImprPantalla);
        setCombinationKeys(kbSettings.getCaptureRegionKeys(), lblRecarregarMiniatures);
    }
    
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
    
    public void addSettingToUpdatablesList(Settings st)
    {
        if(updatableChanges.contains(st))
        {
            updatableChanges.remove(st);
        }
        
        updatableChanges.add(st);
    }
    
    private EventHandler<ActionEvent> onBtnApplyAction = (evt) -> {
        for(Settings s : updatableChanges)
        {
            s.saveSettings();
        }
    };
    
    private EventHandler<ActionEvent> onBtnModificarImprPantAction = (evt) -> {

        Windows.getKeyInputDialog().getController().setParentKeysLabel(lblImprPantalla);
        Windows.getKeyInputDialog().getController().setKeyboardSetting(KeysInputDialogController.TAKE_SCREENSHOT);
        Windows.getKeyInputDialog().getStage().show();
    };

    private EventHandler<ActionEvent> onBtnModificarRecarregarMiniaturesAction = (evt) -> {

        Windows.getKeyInputDialog().getController().setParentKeysLabel(lblRecarregarMiniatures);
        Windows.getKeyInputDialog().getController().setKeyboardSetting(KeysInputDialogController.CAPTURE_REGION);
        Windows.getKeyInputDialog().getStage().show();
    };
}
