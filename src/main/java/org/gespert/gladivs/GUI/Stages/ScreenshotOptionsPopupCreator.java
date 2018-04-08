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

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.gespert.gladivs.GUI.Controllers.ScreenshotOptionsPopupController;
import org.gespert.gladivs.GUI.ScreenshotOptionsPopup;
import org.gespert.gladivs.Screenshots.Monitors;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class ScreenshotOptionsPopupCreator {
    private Parent root;
    private Stage stage;
    private Scene scene;
    private FXMLLoader loader;
    private GraphicsDevice gDevice;
    private Monitors monitors;
    private Stage parentStage;
    private ScreenshotOptionsPopupController sopControler;
    private ScreenshotOptionsPopup crController;
    
    public ScreenshotOptionsPopupCreator(ScreenshotOptionsPopup cr)
    {
        crController = cr;
        parentStage = cr.getStage();
    }
    
    public void createNewPopup()
    {
        try {
            //Preparar el carregador de FXML
            loader = new FXMLLoader(
                    getClass().getResource("/fxml/ScreenshotOptionsPopup.fxml")
            );
            
            //Inicialitzar les variables de finestra
            root = (Parent) loader.load();
            scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add("/styles/screenshotOptionsPopup.css");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));
            
            stage.show();
            sopControler = (ScreenshotOptionsPopupController) loader.getController();
            sopControler.setPopupWindowCreator(this);
            sopControler.setCaptureRegionController(crController);
            sopControler.postInitialize();
            
            //Obtenir les dades del monitor actual
            monitors = new Monitors();
            Point pt = monitors.getMousePosition();
            gDevice = monitors.getMonitorFromProsition(pt);
            GraphicsConfiguration gConfig = gDevice.getConfigurations()[0];
            Rectangle rec = gConfig.getBounds();
            
            Double scWidth = stage.getWidth();
            Double scHeight = stage.getHeight();
            int deltaX = (pt.x - rec.x) + (scWidth.intValue() + 5);
            int deltaY = (pt.y - rec.y) + (scHeight.intValue() + 5);
            
            //Configurar la posició inicial finestra
            if(deltaX > rec.width)
            {
                stage.setX(pt.x - scWidth.intValue() - 5);
            }
            else
            {
                stage.setX(pt.x + 5);
            }
            
            if(deltaY > rec.height)
            {
                stage.setY(pt.y - scHeight.intValue() - 5);
            }
            else
            {
                stage.setY(pt.y + 5);
            }
            
            //Registrar els events
            stage.addEventHandler(KeyEvent.KEY_RELEASED, onKeyReleased);
            
        } catch (IOException ex) {
            Logger.getLogger(ScreenshotOptionsPopupCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closePopupWindow()
    {
        if(stage != null) stage.close();
    }
    
    public Stage getStage()
    {
        return stage;
    }
    
    private EventHandler<KeyEvent> onKeyReleased = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.ESCAPE)
            {
                stage.close();
                if(parentStage != null) parentStage.close();
            }
        }
    };
}
