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

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.gespert.gladivs.GUI.Controllers.MainWindowController;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class MainWindowCreator {
    
    private Stage mwStage;
    private MainWindowController mwController;
    
    public void createNewWindow()
    {
        mwStage = new Stage();
        
        final FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/MainScene.fxml")
        );
        
        Parent root;
        
        try {
            loader.setResources(ResourceBundle.getBundle("bundles.Main"));
            root = (Parent) loader.load();
            
            Font.loadFont(getClass().getResourceAsStream("/fonts/OpenSans-Bold.ttf"), 16);

            mwController = loader.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            mwStage.setTitle("Gladivs Screen Capture 0.7 BETA");
            mwStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));
            mwStage.setScene(scene);
            mwStage.setResizable(false);

            //Perform post initialization actions in controller
            mwController.postInicilizationActions();

            mwStage.show();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            
            return;
        }
    }
    
    public Stage getStage()
    {
        return mwStage;
    }
    
    public MainWindowController getController()
    {
        return mwController;
    }
}
