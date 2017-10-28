/*
 * Copyright (C) 2016 Guillermo Espert Carrasquer
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
package com.gladivs.gladivsssc.Helpers;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class Dialogs {
    
    public Dialogs()
    {
        //Empty constructor
    }
    
    /**
     * Shows an alert dialog with a title, header, and text. Header is optional: nullify to make optional.
     * @param title
     * @param header
     * @param text 
     */
    public static void showErrorDialog(String title, String header, String text)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        
        if(title != null)
            alert.setTitle(title);
        else
            alert.setTitle("Error");

        if(header != null)
            alert.setHeaderText(header);
        
        if(text != null)
            alert.setContentText(text);
        else
            alert.setContentText("Not set");

        Dialogs d = new Dialogs();
        Stage aStage = (Stage) alert.getDialogPane().getScene().getWindow();
        aStage.getIcons().add(new Image(d.getClass().getResourceAsStream("/icons/icona_sense_sombra.png")));

        alert.showAndWait();
    }
}
