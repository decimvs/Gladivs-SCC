/*
 * Copyright (C) 2016 Guillermo Espert Carrasquer
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

import com.gladivs.gladivsssc.GUI.Controllers.MainController;
import com.gladivs.gladivsssc.Configuration.Configuration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Guillermo Espert Carrasquer
 */
public class CombinationKeysSettingsController implements Initializable {
    
    private Configuration configuration;
    private Stage myStage;
    private ResourceBundle lang;
    
    @FXML
    Label takeSKey1, takeSKey2, takeSKey3, refreshMKey1, refreshMKey2, refreshMKey3;
    @FXML
    Label lbTsK12, lbTsK23, lbRmK12, lbRmK23, lblTakeScreenshot, lblRefreshMonitorMiniatures;
    @FXML
    Button btnModifySSK, btnModifyRMK, btnClose;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }    
    
    public void postInitializationActions()
    {
        setKeysOnLabels();
        
        translateUI();
    }
    
    private void translateUI()
    {
        lang = ResourceBundle.getBundle("bundles.Main");
        lblTakeScreenshot.setText(lang.getString("ckc_label_take_screenshot"));
        lblRefreshMonitorMiniatures.setText(lang.getString("ckc_label_refresh_monitor_miniatures"));
        btnModifySSK.setText(lang.getString("ckc_button_modify"));
        btnModifyRMK.setText(lang.getString("ckc_button_modify"));
        btnClose.setText(lang.getString("ckc_button_close"));
        
        //Update window dimensions after UI translation
        myStage.sizeToScene();
    }
    
    public void reloadKeyLabels()
    {
        setKeysOnLabels();
    }
    
    private void setKeysOnLabels()
    {
        //Screenshot key 2
        if(configuration.getUserValues().get(configuration.PSKEY2).getIntValue() > -1)
        {
            takeSKey2.setText(configuration.getUserValues().get(configuration.PSKEY2).getParameterString());
            takeSKey2.setVisible(true);
            lbTsK23.setText("+");
        }
        else
        {
            takeSKey2.setText("");
            takeSKey2.setVisible(false);
            lbTsK23.setText("");
        }
        
        //Screenshot key 1
        if(configuration.getUserValues().get(configuration.PSKEY1).getIntValue() > -1 && configuration.getUserValues().get(configuration.PSKEY2).getIntValue() <= -1)
        {
            takeSKey2.setText(configuration.getUserValues().get(configuration.PSKEY1).getParameterString());
            takeSKey2.setVisible(true);
            lbTsK23.setText("+");
            
            takeSKey1.setText("");
            takeSKey1.setVisible(false);
            lbTsK12.setText("");
        }
        else if(configuration.getUserValues().get(configuration.PSKEY1).getIntValue() > -1 && configuration.getUserValues().get(configuration.PSKEY2).getIntValue() > -1)
        {
            takeSKey1.setText(configuration.getUserValues().get(configuration.PSKEY1).getParameterString());
            takeSKey1.setVisible(true);
            lbTsK12.setText("+");
        }
        else
        {
            takeSKey1.setText("");
            takeSKey1.setVisible(false);
            lbTsK12.setText("");
        }
        
        //Screenshot key 3
        if(configuration.getUserValues().get(configuration.PSKEY3).getIntValue() > -1)
        {
            takeSKey3.setText(configuration.getUserValues().get(configuration.PSKEY3).getParameterString());
        }
        else
        {
            throw new RuntimeException("Error la tercera clau del take screenshot no pot estar buida");
        }
        
        //------- Refresh Monitor -------//
        
        //Refresh monitor key2
        if(configuration.getUserValues().get(configuration.RMKEY2).getIntValue() > -1)
        {
            refreshMKey2.setText(configuration.getUserValues().get(configuration.RMKEY2).getParameterString());
            refreshMKey2.setVisible(true);
            lbRmK23.setText("+");
        }
        else
        {
            refreshMKey2.setText("");
            refreshMKey2.setVisible(false);
            lbRmK23.setText("");
        }
        
        //Refresh monitor key1
        if(configuration.getUserValues().get(configuration.RMKEY1).getIntValue() > -1  && configuration.getUserValues().get(configuration.RMKEY2).getIntValue() <= -1)
        {
            refreshMKey2.setText(configuration.getUserValues().get(configuration.RMKEY1).getParameterString());
            refreshMKey2.setVisible(true);
            lbRmK23.setText("+");
            
            refreshMKey1.setText("");
            refreshMKey1.setVisible(false);
            lbRmK12.setText("");
        }
        else if(configuration.getUserValues().get(configuration.RMKEY1).getIntValue() > -1  && configuration.getUserValues().get(configuration.RMKEY2).getIntValue() > -1)
        {
            refreshMKey1.setText(configuration.getUserValues().get(configuration.RMKEY1).getParameterString());
            refreshMKey1.setVisible(true);
            lbRmK12.setText("+");
        }
        else
        {
            refreshMKey1.setText("");
            refreshMKey1.setVisible(false);
            lbRmK12.setText("");
        }
        
        //Refresh monitor key3
        if(configuration.getUserValues().get(configuration.RMKEY3).getIntValue() > -1)
        {
            refreshMKey3.setText(configuration.getUserValues().get(configuration.RMKEY3).getParameterString());
        }
        else
        {
            throw new RuntimeException("Error la tercera clau del take screenshot no pot estar buida");
        }
        
        myStage.sizeToScene();
        myStage.centerOnScreen();
    }
    
    private void openKeySelectorWindow(int action)
    {
        try
            {
                //Open the keyboard settings window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/KeyboardSettings.fxml"));
                Parent parent = (Parent) loader.load();
                Stage stage = new Stage();
                
                KeySettingsController ksController = loader.getController();
                ksController.setStartParameters(this, configuration, action);
                ksController.setMyStage(stage);
                ksController.postInitializationActions();
                
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.setTitle(lang.getString("ksc_window_title"));
                stage.setScene(new Scene(parent));
                stage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    //-------- Events --------//
    @FXML
    private void onModifyTakeScreenshotKeys(ActionEvent ev)
    {
        openKeySelectorWindow(0);
    }
    
    @FXML
    private void onModifyRefreshMonitorsKeys(ActionEvent ev)
    {
        openKeySelectorWindow(1);
    }
    
    @FXML
    private void closeWindow(ActionEvent ev)
    {
        Button bt = (Button) ev.getSource();
        Stage w = (Stage) bt.getScene().getWindow();
        w.close();
    }
    
    //-------- Getters and Setters --------//
    /**
     * Sets global configuration key settings
     * @param conf 
     */
    public void setConfigurationSettings(Configuration conf) { this.configuration = conf; }
    
    public void setMyStage(Stage st) { this.myStage = st; }
}
