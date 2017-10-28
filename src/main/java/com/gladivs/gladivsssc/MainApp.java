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

import com.gladivs.gladivsssc.Configuration.Configuration;
import javafx.scene.text.Font;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApp extends Application {
    
    MainController mController;
    
    public final String APP_VERSION = "0.6";
    public final String APP_NAME = "Gladivs Simple Screen Capture";
    public final String APP_AUTHOR = "Guillermo Espert Carrasquer";
    
    private boolean isCTRLKeyPressed = false;
    private NativeKeyListener nkl;
    private Stage mainStage;
    private final Configuration configuration;
    
    public MainApp()
    {
        configuration = new Configuration();
    }
    
    @Override
    public void start(Stage stage) throws Exception 
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

            stage.setTitle("Gladivs Simple Screen Capture 0.6 BETA");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));
            stage.setScene(scene);
            stage.setResizable(false);

            nkl = new NativeKeyListener("jNative Hook", mController, configuration);
            nkl.start();

            //Set this stage in controller
            mController.setPrimaryStage(stage);

            //Set this object instance in MainController
            mController.setMainAppInstance(this);
            
            //Set Configuration values to MainController
            mController.setConfigurationSettings(configuration);

            //Perform post initialization actions in controller
            mController.postInicilizationActions();

            stage.show();
            
            mainStage = stage;
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            
            return;
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     *Esta funció definida en Application es invocada quant l'aplicació rep l'ordre de tancar o finalitzar
     * Finalitza tots els fils de la aplicació.
     */
    @Override
    public void stop()
    {
        nkl.stop();
        System.exit(0);
    }
    
    /**
     * Get the main Scene object instance
     * @return 
     */
    public Stage getMainStage(){ return mainStage; }
}
