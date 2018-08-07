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
package org.gespert.gladivs.GUI.OptionsPopup;

import org.gespert.gladivs.GUI.OptionsPopup.Controllers.ScreenshotOptionsPopupController;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ResourceBundle;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.gespert.gladivs.GUI.Controllers.ScreenshotPopupModes;
import org.gespert.gladivs.GUI.OptionsPopup.Controllers.OptionButtonsBase;
import org.gespert.gladivs.GUI.ScreenshotOptionsPopup;
import org.gespert.gladivs.Screenshots.MonitorData;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class ScreenshotOptionsPopupCreator {
    private Parent root;
    private Stage stage;
    private Scene scene;
    private FXMLLoader loader;
    private Stage parentStage;
    private ScreenshotOptionsPopupController sopController;
    private OptionButtonsBase buttonsController;
    private ScreenshotOptionsPopup crController;
    private ScreenshotPopupModes popupMode;
    
    public ScreenshotOptionsPopupCreator(ScreenshotOptionsPopup cr, ScreenshotPopupModes pm)
    {
        crController = cr;
        parentStage = cr.getStage();
        popupMode = pm;
    }
    
    /**
     * Crea una nova finestra segons el context
     */
    public void createNewPopup(MonitorData md)
    {
        try {
            //Preparar el carregador de FXML
            loader = new FXMLLoader(
                    getClass().getResource("/fxml/ScreenshotOptionsPopup.fxml")
            );
            loader.setResources(ResourceBundle.getBundle("bundles.Main"));
            
            //Inicialitzar les variables de finestra
            root = (Parent) loader.load();
            scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(crController.getStage());
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add("/styles/screenshotOptionsPopup.css");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));
            
            //Loader per als botons d'opcions
            FXMLLoader btnLoader = getLoadedFXMLForPopupMode();
            btnLoader.setResources(ResourceBundle.getBundle("bundles.Main"));
         
            //Carregar i inicialitzar la finestra del popup d'opcions
            sopController = (ScreenshotOptionsPopupController) loader.getController();
            sopController.setPopupWindowCreator(this);
            sopController.setPopupMode(popupMode);
            sopController.setCaptureRegionController(crController);
            sopController.loadPopupButtons(btnLoader);
            sopController.postInitialize();
            
            //Obtenir el controlador dels botons d'opcions i carregar valors
            buttonsController = (OptionButtonsBase) btnLoader.getController();
            buttonsController.setPopupWindowCreator(this);
            buttonsController.setPopupMode(popupMode);
            buttonsController.setCaptureRegionController(crController);
            
            //Mostra la finestra
            stage.show();
            
            //Obtenir les dades del monitor actual
            Rectangle rec = md.getMonitorRectangle();
            Point pt;
            
            /**
             * Si el rectangle d'area està definit, provem de calcular la posició 
             * del punt final, en el punt de finalització de dibuixat.
             */
            if(md.getAreaRectangle() != null)
            {
                pt = new Point();
                pt.setLocation(rec.getX() + md.getAreaRectangle().getX() + md.getAreaRectangle().getWidth(), rec.getY() + md.getAreaRectangle().getX() + md.getAreaRectangle().getHeight());
            }
            else
            {
                pt = md.getMousePointer();
            }
            
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
    
    private FXMLLoader getLoadedFXMLForPopupMode()throws IOException
    {
        switch(popupMode)
        {
            case REGION_CAPTURE:
                return new FXMLLoader(
                    getClass().getResource("/fxml/sopRegionCaptureButtons.fxml")
                );
            case SCREEN_CAPTURE:
                return new FXMLLoader(
                    getClass().getResource("/fxml/sopMonitorCaptureButtons.fxml")
                );
            default:
                return null;
        }
    }
    
    /**
     * Tanca la finestra popup
     */
    public void closePopupWindow()
    {
        if(stage != null) stage.close();
    }
    
    /**
     * Retorna el stage
     * @return 
     */
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
