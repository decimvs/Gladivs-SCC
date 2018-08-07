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
package org.gespert.gladivs.GUI.Stages;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.gespert.gladivs.GUI.Controllers.KeysInputDialogController;
import org.gespert.gladivs.Instances.Windows;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class KeysInputDialogWindowCreator {
    
    private Stage kidStage;
    private KeysInputDialogController kidController;
    private ResourceBundle rs;
    
    public void createNewWindow()
    {
        Parent root;
        rs = ResourceBundle.getBundle("bundles.Main");

        try {
            final FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/KeysInputDialog.fxml")
            );

            //Load FXML file
            loader.setResources(rs);
            root = (Parent) loader.load();
            
            KeysInputDialogController kidController = loader.getController();
            kidController.setSettingsDialogController(Windows.getSettingsDialog().getController());
            this.kidController = kidController;

            //Configure and show window
            kidStage = new Stage();
            kidStage.setTitle(rs.getString("keys_selection_window_title"));
            kidStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));
            kidStage.setScene(new Scene(root));
            kidStage.initModality(Modality.WINDOW_MODAL);
            kidStage.initOwner(Windows.getSettingsDialog().getStage());
            
            

        } catch (IOException ex) {
            System.out.println("Error durant la creació de la finestra de diàleg per a la definició de tecles.");
        }
    }
    
    public Stage getStage()
    {
        return kidStage;
    }
    
    public KeysInputDialogController getController()
    {
        return kidController;
    }
}
