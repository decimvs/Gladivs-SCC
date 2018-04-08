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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.gespert.gladivs.GUI.Controllers.SettingsDialogController;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class SettingsWindowCreator {
    
    private Stage sdStage;
    private SettingsDialogController sdController;
    
    public void createNewWindow()
    {
        Parent root;
        
        try {
            final FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/SettingsDialog.fxml")
            );

            //Load FXML file
            root = (Parent) loader.load();
            
            this.sdController = loader.getController();

            //Configure and show window
            sdStage = new Stage();
            sdStage.setTitle("Settings");
            sdStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));
            sdStage.setScene(new Scene(root));
            
        } catch (IOException ex) {
            System.out.println("Error durant la creació de la finestra de les preferències: " + ex.getMessage());
        }
    }
    
    public Stage getStage()
    {
        return sdStage;
    }
    
    public SettingsDialogController getController()
    {
        return sdController;
    }
}
