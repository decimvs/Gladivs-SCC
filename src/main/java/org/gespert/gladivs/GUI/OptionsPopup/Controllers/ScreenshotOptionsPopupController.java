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
package org.gespert.gladivs.GUI.OptionsPopup.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class ScreenshotOptionsPopupController extends OptionButtonsBase implements Initializable {
       
    @FXML
    private HBox titleBox;
    
    @FXML
    private VBox vboxButtonsContainer;

    private Double origStageX, origStageY;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleBox.addEventHandler(MouseEvent.MOUSE_PRESSED, onTitleBoxMousePressed);
        titleBox.addEventHandler(MouseEvent.MOUSE_DRAGGED, onTitleBoxMouseDragged);
    }

    /**
     * Carrega un document FXML dins del contenidor de la finestra
     * @param fld
     * @throws IOException 
     */
    public void loadPopupButtons(FXMLLoader fld) throws IOException
    {
        Node buttons = fld.load();
        
        vboxButtonsContainer.getChildren().add(buttons);
    }
    
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
