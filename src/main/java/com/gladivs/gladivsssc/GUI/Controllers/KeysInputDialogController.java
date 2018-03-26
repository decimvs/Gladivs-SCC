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
package com.gladivs.gladivsssc.GUI.Controllers;

import com.gladivs.gladivsssc.Configuration.KeyboardSettings;
import com.gladivs.gladivsssc.Events.EventsManager.GlobalKeysListener;
import com.gladivs.gladivsssc.Instances.GlobalListenerInstance;
import com.gladivs.gladivsssc.Instances.WindowsInstances;
import com.gladivs.gladivsssc.Keyboard.ManageKeys;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * Proporciona un dialog que permet a l'usuari introduïr una combinació
 * de tecles.
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class KeysInputDialogController implements Initializable, GlobalKeysListener {
    
    private ArrayList<Integer> keysPressed = new ArrayList<>();
    private SettingsDialogController sdController;
    private Label parentKeysLabel;
    private KeyboardSettings kbSetting;
    
    @FXML
    private Label lblTecles;
    
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GlobalListenerInstance.getInstance().addListener(this, 1);
        
        btnCancelar.addEventHandler(ActionEvent.ACTION, onBtnCancelarAction);
    }    

    @Override
    public void onGlobalKeyPressed(NativeKeyEvent nke) 
    {
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
            
            if(!ManageKeys.isModifier(nke.getKeyCode()))
            {
                finalizeInputAndClose();
            }
        }
                
        GlobalListenerInstance.getInstance().consumeEvent();
    }

    @Override
    public void onGlobalKeyReleased(NativeKeyEvent nke) 
    {
        
        GlobalListenerInstance.getInstance().consumeEvent();
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
     * Assingna el Setting que estem modificant o definint en este moment.
     * És necessari per a permetre desar els nous valors.
     * @param kbSetting 
     */
    public void setKeyboardSetting(KeyboardSettings kbSetting)
    {
        this.kbSetting = kbSetting;
    }

    @Override
    public void onGlobalKeyTyped(NativeKeyEvent nke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }  
    
    private void finalizeInputAndClose()
    {
        if(sdController != null)
        {
            kbSetting.storeKeyCombination(keysPressed);
            sdController.addSettingToUpdatablesList(kbSetting);
            sdController.setCombinationKeys(keysPressed, parentKeysLabel);
        }
        
        //Important!!! és necessari cridar per a traure el listener, si no
        //pot seguir actuant encara que no el necessitem.
        GlobalListenerInstance.getInstance().removeListener(this);
        
        //Destruïm l'instància per tal d'obligar a crear-ne una de nova cada volta
        WindowsInstances.destroyKeysInputDialogWindowCreator();
        
        btnCancelar.getScene().getWindow().hide();
    }
    
    public void setSettingsDialogController(SettingsDialogController sdController)
    {
        this.sdController = sdController;
    }
    
    private EventHandler<ActionEvent> onBtnCancelarAction = (evt) -> {
        
        ((Node) evt.getSource()).getScene().getWindow().hide();
    };
}
