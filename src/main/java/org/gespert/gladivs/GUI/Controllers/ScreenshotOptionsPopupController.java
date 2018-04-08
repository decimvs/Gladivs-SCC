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
package org.gespert.gladivs.GUI.Controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.gespert.gladivs.GUI.ScreenshotOptionsPopup;
import org.gespert.gladivs.GUI.Stages.ScreenshotOptionsPopupCreator;
import org.gespert.gladivs.Instances.SettingsInstance;
import org.gespert.gladivs.Screenshots.ImagesIO;
import org.gespert.gladivs.Settings.GeneralSettings;

/**
 * FXML Controller class
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class ScreenshotOptionsPopupController implements Initializable {

    @FXML
    private Button btnSave, btnSaveAs, btnReSelect, btnExit;
    
    @FXML
    private AnchorPane apRootPane;
    
    @FXML
    private HBox titleBox;
    
    private ScreenshotOptionsPopupCreator sopCreator;
    private ScreenshotOptionsPopup crController;
    private GeneralSettings gnSettings = SettingsInstance.getGeneralSettings();
    private Double origStageX, origStageY;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void postInitialize()
    {
        btnReSelect.managedProperty().bind(btnReSelect.visibleProperty());
        apRootPane.managedProperty().bind(btnReSelect.visibleProperty());
        
        if(crController.getStage() != null)
        {
            btnReSelect.addEventHandler(ActionEvent.ACTION, onBtnReSelectActionPerformed);
        }
        else
        {
            btnReSelect.setVisible(false);
            
            btnReSelect.getScene().getWindow().setHeight(btnReSelect.getScene().getWindow().getHeight() - 100);
            apRootPane.setPrefWidth(apRootPane.getWidth() - 100);
        }
        
        btnSave.addEventHandler(ActionEvent.ACTION, onBtnSaveActionPerformed);
        btnSaveAs.addEventHandler(ActionEvent.ACTION, onBtnSaveAsActionPerformed);
        btnExit.addEventHandler(ActionEvent.ACTION, onBtnExitActionPerformed);
        
        titleBox.addEventHandler(MouseEvent.MOUSE_PRESSED, onTitleBoxMousePressed);
        titleBox.addEventHandler(MouseEvent.MOUSE_DRAGGED, onTitleBoxMouseDragged);
    }
    
    /**
     * Enregistra una instància de la clase que controla la creació de la finestra
     * de popup d'opcions.
     * @param sop 
     */
    public void setPopupWindowCreator(ScreenshotOptionsPopupCreator sop)
    {
        sopCreator = sop;
    }
    
    /**
     * Enregistra una instància de la clase que controla la visualització i comportament
     * de la finestra de selecció de regió.
     * @param cr 
     */
    public void setCaptureRegionController(ScreenshotOptionsPopup cr)
    {
        crController = cr;
    }
    
    private void closeAllWindows()
    {
        sopCreator.closePopupWindow();
        
        if(crController.getStage() != null)
        {
            ((CaptureRegionWindowController) crController).getStage().close();
        }
    }
    
    private EventHandler<ActionEvent> onBtnSaveActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            File userLocation = new File(gnSettings.getUserSelectedImagesSavePath() + "/" + ImagesIO.generateFileName());
            crController.saveCaptureToSelectedLocation(userLocation);
            
            closeAllWindows();
        }
    };
    
    private EventHandler<ActionEvent> onBtnSaveAsActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) 
        {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(
                new ExtensionFilter("Image files", "*.png")
            );
            
            File lastDirectory = new File(gnSettings.getLastImageSavePath());
            fc.setInitialDirectory(lastDirectory);
            fc.setInitialFileName(ImagesIO.generateFileName());
            
            fc.setTitle("Sel·lecciona el fitxer que vols desar");
            
            File selectedFile = fc.showSaveDialog(btnExit.getScene().getWindow());
            
            if(selectedFile != null)
            {
                crController.saveCaptureToSelectedLocation(selectedFile);
                gnSettings.setLastImageSavePath(selectedFile.getParent());
                gnSettings.saveSettings();
                
                closeAllWindows();
            }
        }
    };
    
    /**
     * Reinicialitza la selecció. Tanca esta finestra.
     */
    private EventHandler<ActionEvent> onBtnReSelectActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            crController.resetSelection();
            sopCreator.closePopupWindow();
        }
    };
    
    /**
     * Finalitza la selecció i tanca totes les finestres
     */
    private EventHandler<ActionEvent> onBtnExitActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            closeAllWindows();
        }
    };
    
    /**
     * Permet desplaçar la finestra de les opcions per els monitors
     * Gestiona l'inici del possible desplaçament de la finestra. Calcula el 
     * valor de correcció per al desplaçament.
     */
    private EventHandler<MouseEvent> onTitleBoxMousePressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent evt) {
            if(sopCreator != null)
            {
                Stage st = sopCreator.getStage();
                
                origStageX = evt.getScreenX() - st.getX();
                origStageY = evt.getScreenY() - st.getY();
            }
        }
    };
    
    /**
     * Permet desplaçar la finestra de les opcions per els monitors
     * Gestiona l'event del desplaçament de la finestra. Calcula la nova posició
     * de la finestra segons la posició del punter del ratolí i del factor de
     * correcció que es deu aplicar per a que el moviment siga fluid.
     */
    private EventHandler<MouseEvent> onTitleBoxMouseDragged = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(sopCreator != null)
            {
                Stage st = sopCreator.getStage();
                Double xAxis = event.getScreenX() - origStageX;
                Double yAxis = event.getScreenY() - origStageY;
                
                st.setX(xAxis);
                st.setY(yAxis);
            }
        }
    };
}
