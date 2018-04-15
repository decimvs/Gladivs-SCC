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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class CaptureMonitorOptionButtons extends OptionButtonsBase implements Initializable {

    @FXML
    private Button btnSave, btnSaveAs, btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.addEventHandler(ActionEvent.ACTION, onBtnSaveActionPerformed);
        btnSaveAs.addEventHandler(ActionEvent.ACTION, onBtnSaveAsActionPerformed);
        btnExit.addEventHandler(ActionEvent.ACTION, onBtnExitActionPerformed);
    }
    
    private EventHandler<ActionEvent> onBtnSaveActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            saveScreenshot();
        }
    };
    
    private EventHandler<ActionEvent> onBtnSaveAsActionPerformed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) 
        {
            saveScreenshotAs();
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
    
}
