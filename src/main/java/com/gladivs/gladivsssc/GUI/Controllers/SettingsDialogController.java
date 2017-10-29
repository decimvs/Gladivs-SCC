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

import com.gladivs.gladivsssc.Instances.WindowsInstances;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * FXML Controller class
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class SettingsDialogController implements Initializable {

    @FXML
    private TextField txtKeys;
    
    @FXML
    private Label lblImprPantalla;
    
    @FXML
    private Button btnModificarImprPant;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnModificarImprPant.addEventHandler(ActionEvent.ACTION, onBtnModificarImprPantAction);
    }
    
    public void setCombinationKeys(ArrayList<Integer> keysPressed)
    {
        if(keysPressed.size() > 0)
        {
            int iteration = 1;
            String keys = "";
            
            for(Integer i : keysPressed)
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
                
                iteration++;
            }
            
            keysPressed.clear();
            
            lblImprPantalla.setText(keys);
        }
    }
    
    private EventHandler<ActionEvent> onBtnModificarImprPantAction = (evt) -> {
        Parent root;
        
        try {
            final FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/KeysInputDialog.fxml")
            );

            //Load FXML file
            root = (Parent) loader.load();
            
            KeysInputDialogController kidController = loader.getController();
            kidController.setSettingsDialogController(this);

            //Configure and show window
            Stage stage = new Stage();
            stage.setTitle("Introduïr combinació de tecles");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(WindowsInstances.getMainWindow().getStage());
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    };

}
