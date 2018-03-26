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
package com.gladivs.gladivsssc.GUI.WindowCreators;

import com.gladivs.gladivsssc.Instances.ConfigurationInstance;
import com.gladivs.gladivsssc.GUI.Controllers.MainController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class MainWindowCreator {
    
    private MainController mController;
    private Stage mainStage;
    
    
    public void createNewWindow()
    {
        if(mController == null || mainStage == null)
        {
            final FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/MainScene.fxml")
            );

            Parent root;

            try {
                root = (Parent) loader.load();

                Font.loadFont(getClass().getResourceAsStream("/fonts/OpenSans-Bold.ttf"), 16);

                mController = loader.getController();

                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/Styles.css");

                Stage stage = new Stage();
                stage.setTitle("Gladivs Simple Screen Capture 0.7 BETA");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));
                stage.setScene(scene);
                stage.setResizable(false);

                mainStage = stage;

                //Set this stage in controller
                mController.setPrimaryStage(stage);

                //Set this object instance in MainController
                //mController.setMainAppInstance(stage);

                //Set Configuration values to MainController
                mController.setConfigurationSettings(ConfigurationInstance.getConfiguration());

                //Perform post initialization actions in controller
                mController.postInicilizationActions();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public Stage getStage()
    {
        return mainStage;
    }
    
    public MainController getController()
    {
        return mController;
    }
}
