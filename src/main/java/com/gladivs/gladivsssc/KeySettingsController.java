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

import com.gladivs.gladivsssc.Helpers.Dialogs;
import com.gladivs.gladivsssc.KeyBoardConfig.KeyMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gladivs.gladivsssc.Configuration.Configuration;
import com.gladivs.gladivsssc.Configuration.Values;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author gespe
 */
public class KeySettingsController implements Initializable {
    
    @FXML
    private ComboBox<KeyMapper> cbFirstKey, cbSecondKey, cbFinalKey;
    @FXML
    private Button btnSetKeys, btnCancel;
    @FXML
    private Label lblFirstKey, lblSecondKey, lblFinalKey;
    
    private MainApp mApp;
    private ObservableList<KeyMapper> normalKeys;
    private ObservableList<KeyMapper> alterKeys;
    private Stage myStage;
    private boolean isStarted = false;
    private ObjectMapper mapper;
    private ResourceBundle rb;
    
    //Parent
    private CombinationKeysSettingsController cksController;
    private Configuration configuration;
    private int ckscAction;
    private ResourceBundle lang;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Get the resource bundle for language set by user or in JVM
        //This language is setted in MainController
        this.rb = ResourceBundle.getBundle("bundles.KeyMapKeys");
        
        //Initialize object mapper
        mapper = new ObjectMapper();
        
        //Initialize and define alter keys
        defineAlterKeys();
        defineNormalKeys();
        
        //Put alter keys inside first combo box
        setFirstKeysComboBox();
        setSecondKeysComboBox();
        setThirdKeysComboBox();
        
        //Events
        cbFirstKey.addEventHandler(ActionEvent.ACTION, onFirstKeyComboBoxItemSelected);
        btnSetKeys.addEventHandler(ActionEvent.ACTION, onButtonSetKeysActionPerformed);
        btnCancel.addEventHandler(ActionEvent.ACTION, onButtonCancelActionPerformed);
    }
    
    public void postInitializationActions()
    {
        translateUI();
    }
    
    private void translateUI()
    {
        //Load with default locale (set or by user o JVM default if not is set by user)
        this.lang = ResourceBundle.getBundle("bundles.Main");
        
        lblFirstKey.setText(lang.getString("ksc_label_first_key"));
        lblSecondKey.setText(lang.getString("ksc_label_second_key"));
        lblFinalKey.setText(lang.getString("ksc_label_final_key"));
        btnSetKeys.setText(lang.getString("ksc_button_ok"));
        btnCancel.setText(lang.getString("ksc_button_cancel"));
        
        //Update window dimensions after UI translation
        myStage.sizeToScene();
    }
    
    /**
     * Get all information for keys alter definition from a JSON config file
     */
    private void defineAlterKeys()
    {
        alterKeys = defineKeys("kmAlterKeys.json");
    }
    
    /**
     * Get all information for normal keys definiton from a JSON config file
     */
    private void defineNormalKeys()
    {
        normalKeys = defineKeys("kmNormalKeys.json");
    }
    
    /**
     * Generates an observablelist with the specified keys from file. 
     * File must a file name with extension.
     * @param file
     * @param list 
     */
    private ObservableList<KeyMapper> defineKeys(String file)
    {
        //Getting keys information
        URL source = getClass().getResource("/config/" + file);
        List<LinkedHashMap> lkm = null;
        
        //Try to serialize all read key params
        try {
            lkm = mapper.readValue(source, List.class);
            ObservableList<KeyMapper> obl = FXCollections.observableArrayList();
            
            for(int i = 0; i < lkm.size(); i++)
            {
                KeyMapper km = new KeyMapper();
                LinkedHashMap lhm = (LinkedHashMap) lkm.get(i);
                
                km.setIdNativeKey((int) lhm.get("idNativeKey"));
                km.setNativeKeyConstantName((String) lhm.get("nativeKeyConstantName"));
                
                String translated = (String) lhm.get("stringLang");
                
                if(rb != null && rb.getString((String) lhm.get("stringLang")) != null)
                {
                    translated = rb.getString((String) lhm.get("stringLang"));
                }
                
                km.setStringLang(translated);
                
                obl.add(km);
            }
            
            return obl;
            
        } catch (IOException ex) {
            Logger.getLogger(KeySettingsController.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        return null;
    }
    
    /**
     * Populates the combo box for selection of the first key 
     */
    private void setFirstKeysComboBox()
    {      
        //Set first option in combobox
        KeyMapper km = new KeyMapper("None");
        km.setIdNativeKey(-1);
        cbFirstKey.getItems().add(km);
        
        //Set all the rest of items
        cbFirstKey.getItems().addAll(alterKeys);
    }
    
    /**
     * Populates the combo box for selection of the second key
     * This occurs when in first combo box a selection is performed
     */
    private void setSecondKeysComboBox()
    {
        cbSecondKey.getItems().clear();
        
        if(cbFirstKey.getSelectionModel().getSelectedItem() != null && cbFirstKey.getSelectionModel().getSelectedIndex() > 0)
        {
            KeyMapper km = new KeyMapper("None");
            km.setIdNativeKey(-1);
            cbSecondKey.getItems().add(km);
        
            alterKeys.forEach((value) -> {
                if(value.getIdNativeKey() != cbFirstKey.getSelectionModel().getSelectedItem().getIdNativeKey())
                {
                    cbSecondKey.getItems().add(value);
                }
            });
            
            cbSecondKey.getSelectionModel().select(0);
        }
        else if (cbFirstKey.getSelectionModel().getSelectedIndex() == 0)
        {
            KeyMapper km = new KeyMapper("None");
            km.setIdNativeKey(-1);
            cbSecondKey.getItems().add(km);
            
            cbSecondKey.getSelectionModel().select(0);
        }
        else
        {
            KeyMapper km = new KeyMapper("Select a first key");
            km.setIdNativeKey(-5);
            cbSecondKey.getItems().add(km);
        }
    }
    
    /**
     * Populates the third combo box with normal keys
     */
    private void setThirdKeysComboBox()
    {
        normalKeys.forEach((value) -> {
            cbFinalKey.getItems().add(value);
        });
    }
    
    /**
     * Selection event for the first combo box
     */
    private EventHandler<ActionEvent> onFirstKeyComboBoxItemSelected = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            setSecondKeysComboBox();
        }
    };
    
    /**
     * Selection event for the second combo box
     */
    private EventHandler<ActionEvent> onButtonCancelActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Stage w = (Stage) btnCancel.getScene().getWindow();
            w.close();
        }
    };
    
    /**
     * Set Keys button event
     * Saves the keys defined by the user in a userConfig.json file.
     */
    private EventHandler<ActionEvent> onButtonSetKeysActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            
            if(cbFirstKey.getSelectionModel().getSelectedItem() != null && cbSecondKey.getSelectionModel().getSelectedItem() != null)
            {
                if(cbFinalKey.getSelectionModel().getSelectedItem() != null)
                {
                    String firstKey = (String) cbFirstKey.getSelectionModel().getSelectedItem().getStringLang();
                    String secondKey = (String) cbSecondKey.getSelectionModel().getSelectedItem().getStringLang();
                    String finalKey = (String) cbFinalKey.getSelectionModel().getSelectedItem().getStringLang();
                    Integer fstKey = cbFirstKey.getSelectionModel().getSelectedItem().getIdNativeKey();
                    Integer sndKey = cbSecondKey.getSelectionModel().getSelectedItem().getIdNativeKey();
                    Integer fnlKey = cbFinalKey.getSelectionModel().getSelectedItem().getIdNativeKey();
                    
                    //Modify screen shot keys
                    if(ckscAction == 0)
                    {
                        Values vk1 = configuration.getUserValues().get(configuration.PSKEY1);
                        Values vk2 = configuration.getUserValues().get(configuration.PSKEY2);
                        Values vk3 = configuration.getUserValues().get(configuration.PSKEY3);

                        //Modify key1
                        vk1.setParameterValue(fstKey);
                        vk1.setParameterString(firstKey);
                        configuration.modifyParameter(configuration.PSKEY1, vk1, false);

                        //Modify key2
                        vk2.setParameterValue(sndKey);
                        vk2.setParameterString(secondKey);
                        configuration.modifyParameter(configuration.PSKEY2, vk2, false);

                        //Modify key3
                        vk3.setParameterValue(fnlKey);
                        vk3.setParameterString(finalKey);
                        configuration.modifyParameter(configuration.PSKEY3, vk3, false);
                    }
                    else if(ckscAction == 1)
                    {
                        Values vk1 = configuration.getUserValues().get(configuration.RMKEY1);
                        Values vk2 = configuration.getUserValues().get(configuration.RMKEY2);
                        Values vk3 = configuration.getUserValues().get(configuration.RMKEY3);

                        //Modify key1
                        vk1.setParameterValue(fstKey);
                        vk1.setParameterString(firstKey);
                        configuration.modifyParameter(configuration.RMKEY1, vk1, false);

                        //Modify key2
                        vk2.setParameterValue(sndKey);
                        vk2.setParameterString(secondKey);
                        configuration.modifyParameter(configuration.RMKEY2, vk2, false);

                        //Modify key3
                        vk3.setParameterValue(fnlKey);
                        vk3.setParameterString(finalKey);
                        configuration.modifyParameter(configuration.RMKEY3, vk3, false);
                    }
                    
                    configuration.writeUserParameters();
                    cksController.reloadKeyLabels();
                    Stage w = (Stage) btnSetKeys.getScene().getWindow();
                    w.close();
                }
                else
                {
                    Dialogs.showErrorDialog("Error", "Final key is missing", "To set the combination of keys is mandatory to set the final key.");
                }
            }
            else
            {
                Dialogs.showErrorDialog("Error", "First or second key are missing", "Please provide a first and/or second key. If you don't want to set one, please select 'None' option in the dropdown menus.");
            }
        }
    };
    
    
    
    /**
     * Set a variable with an instance of MainApp class
     * @param mApp 
     */
    public void setMainAppInstance(MainApp mApp)
    {
        this.mApp = mApp;
    }
    
    public void setMyStage(Stage myStage)
    {
        this.myStage = myStage;
    }    
    
    public void setStartParameters(CombinationKeysSettingsController cs, Configuration conf, int action)
    {
        this.cksController = cs;
        this.configuration = conf;
        this.ckscAction = action;
    }
}
