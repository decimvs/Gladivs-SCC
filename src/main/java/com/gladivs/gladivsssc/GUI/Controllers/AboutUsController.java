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

package com.gladivs.gladivsssc.GUI.Controllers;

import com.gladivs.gladivsssc.Configuration.Constants;
import com.gladivs.gladivsssc.MainApp;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author gespe
 */
public class AboutUsController implements Initializable {

    private MainApp mApp;
    private ResourceBundle rb;
    
    @FXML
    private Label lblAppVersion, lblAuthor, lblVersion, lblLicense;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rbs) {
        rb = ResourceBundle.getBundle("bundles.Main");
        
        lblAuthor.setText(rb.getString("label_author"));
        lblVersion.setText(rb.getString("label_version"));
        lblLicense.setText(rb.getString("label_lisence"));
    }    
    
    public void setApplicationVersionText(){
        lblAppVersion.setText(Constants.APP_VERSION);
    }
    
    public void setMainAppInstance(MainApp mApp){ this.mApp = mApp; } 
    
}
