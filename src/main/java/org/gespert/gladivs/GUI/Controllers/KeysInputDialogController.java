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
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.gespert.gladivs.Instances.KeysListener;
import org.gespert.gladivs.Instances.SettingsInstance;
import org.gespert.gladivs.Instances.Windows;
import org.gespert.gladivs.Settings.KeyboardSettingsHelper;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class KeysInputDialogController implements Initializable, NativeKeyListener {
    
    private ArrayList<Integer> keysPressed = new ArrayList<>();
    private SettingsDialogController sdController;
    private Label parentKeysLabel;
    private int kbSetting;
    
    public static final int TAKE_SCREENSHOT = 0;
    public static final int CAPTURE_REGION  = 1;
    
    @FXML
    private Label lblTecles;
    
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {  
        //Desreferenciar el listener Global
        //GlobalScreen.removeNativeKeyListener(GKListenerInstance.getGKListener());
        KeysListener.removeGlobalKeyListener();
        
        btnCancelar.addEventHandler(ActionEvent.ACTION, onBtnCancelarAction);

        //Referenciar el listener local
        KeysListener.addKeyListener(this);
    }    
    
    private void updateText(String text)
    {
        lblTecles.setText(text);
    }
    
    /**
     * S'usa per a establir una referència al Label que conté 
     * la combinació de tecles. Una volta finalitzada la definició
     * de les tecles per part de l'usuari, s'usa esta variable per a establir
     * el valor que haja definit l'usuari en la finestra de configuració.
     * 
     * @param lb 
     */
    public void setParentKeysLabel(Label lb)
    {
        this.parentKeysLabel = lb;
    }
    
    /**
     * Establix un identificador per a saber quin paràmetre s'està modificant.
     * Els valors corresponen amb els de les constants definides més amunt.
     * @param kbSetting 
     */
    public void setKeyboardSetting(int kbSetting)
    {
        this.kbSetting = kbSetting;
    }
    
    private void finalizeInputAndClose()
    {
        if(sdController != null)
        {
            switch(kbSetting)
            {
                case TAKE_SCREENSHOT:
                    SettingsInstance.getKeyboardSettings().setTakeScreenshotKeys(keysPressed);
                    break;
                case CAPTURE_REGION:
                    SettingsInstance.getKeyboardSettings().setCaptureRegionKeys(keysPressed);
                    break;
            }
            
            sdController.addSettingToUpdatablesList(SettingsInstance.getKeyboardSettings());
            sdController.setCombinationKeys(keysPressed, parentKeysLabel);
        }
        
        closeDialog();
    }
    
    public void setSettingsDialogController(SettingsDialogController sdController)
    {
        this.sdController = sdController;
    }
    
    private void closeDialog()
    {       
        //Desreferenciar el listener local
        KeysListener.removeKeyListener(this);
        
        //Referenciar el listener global
        //GlobalScreen.addNativeKeyListener(GKListenerInstance.getGKListener());
        KeysListener.addGlobalKeyListener();

        //Destruïm l'instància per tal d'obligar a crear-ne una de nova cada volta
        Windows.destroyKeysInputDialogWindowCreator();
        
        btnCancelar.getScene().getWindow().hide();
    }
    
    private EventHandler<ActionEvent> onBtnCancelarAction = (evt) -> {
        
        closeDialog();
    };

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        Platform.runLater(new Runnable() {
            public void run() {
                String concat;
        
                if(!keysPressed.contains(nke.getKeyCode()))
                {
           
                    keysPressed.add(nke.getKeyCode());
            
                    if(lblTecles.getText().length() <= 0)
                    {
                        concat = "";
                    }
                    else
                    {
                        concat = lblTecles.getText() + " + ";
                    }

                    updateText(concat + NativeKeyEvent.getKeyText(nke.getKeyCode()));
            
                    if(!KeyboardSettingsHelper.isModifier(nke.getKeyCode()))
                    {
                        finalizeInputAndClose();
                    }
                }
            }
        });
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
        
    }
}
