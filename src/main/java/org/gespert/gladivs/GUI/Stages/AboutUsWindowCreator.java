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
import javafx.stage.StageStyle;
import org.gespert.gladivs.GUI.Controllers.AboutUsDialogController;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class AboutUsWindowCreator {
    
    private AboutUsDialogController auController;
    private Stage stage;
    
    /**
     * Crea una nova finestra de AboutUs
     */
    public void createNewWindow()
    {
        Parent root;
        
        try {
            final FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/AboutUs.fxml")
            );

            //Load FXML file
            root = (Parent) loader.load();
            
            //Obtenir el controller per a la finestra about us
            auController = loader.getController();

            //Configure and show window
            stage = new Stage();
            stage.setTitle("About Us");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));
            stage.setScene(new Scene(root, 450, 480));
            stage.initStyle(StageStyle.UTILITY);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Retorna el Stage de la finestra
     * @return 
     */
    public Stage getStage()
    {
        return stage;
    }
    
    /**
     * Retorna la instància del controlador de la finestra
     * @return 
     */
    public AboutUsDialogController getController()
    {
        return auController;
    }
}
