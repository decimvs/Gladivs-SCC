/*
 * Copyright (C) 2017 Guillermo Espert Carrasquer
 *
 * This file is part of Gladivs Simple Screen Capture
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
package com.gladivs.gladivsssc.GUI.WindowCreators;

import com.gladivs.gladivsssc.GUI.Controllers.MainController;
import com.gladivs.gladivsssc.GUI.Controllers.SettingsDialogController;
import com.gladivs.gladivsssc.Instances.WindowsInstances;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class SettingsDialogWindowCreator {
    
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
            sdStage.initModality(Modality.WINDOW_MODAL);
            sdStage.initOwner(WindowsInstances.getMainWindow().getStage());
            
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
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
