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

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.gespert.gladivs.GUI.Controllers.CaptureRegionWindowController;
import org.gespert.gladivs.GUI.Controllers.ScreenshotPopupModes;
import org.gespert.gladivs.GUI.OptionsPopup.ScreenshotOptionsPopupCreator;
import org.gespert.gladivs.GUI.ScreenshotOptionsPopup;
import org.gespert.gladivs.Instances.SettingsInstance;
import org.gespert.gladivs.Screenshots.ImagesIO;
import org.gespert.gladivs.Settings.GeneralSettings;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class OptionButtonsBase {
    
    protected ScreenshotOptionsPopupCreator sopCreator;
    protected ScreenshotOptionsPopup crController;
    protected GeneralSettings gnSettings = SettingsInstance.getGeneralSettings();
    protected ScreenshotPopupModes popupMode;
    
    private FileChooser fileChooser;
    
    protected void reSelectArea()
    {
        crController.resetSelection();
        sopCreator.closePopupWindow();
    }
    
    protected void saveScreenshot()
    {
        File userLocation = new File(gnSettings.getUserSelectedImagesSavePath() + "/" + ImagesIO.generateFileName());
        crController.saveCaptureToSelectedLocation(userLocation);

        closeAllWindows();
    }
    
    protected void saveScreenshotAs()
    {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Image files", "*.png")
        );

        File lastDirectory = new File(gnSettings.getLastImageSavePath());
        fileChooser.setInitialDirectory(lastDirectory);
        fileChooser.setInitialFileName(ImagesIO.generateFileName());

        fileChooser.setTitle("Sel·lecciona el fitxer que vols desar");

        File selectedFile = fileChooser.showSaveDialog(sopCreator.getStage());

        if(selectedFile != null)
        {
            crController.saveCaptureToSelectedLocation(selectedFile);
            gnSettings.setLastImageSavePath(selectedFile.getParent());
            gnSettings.saveSettings();

            closeAllWindows();
        }
    }
    
    /**
     * Desa les dades de la regió sel·leccionada per  a que després puga
     * ser recuperada i reutilitzada.
     */
    protected void setSelectedRegion()
    {
        crController.setSelectedRegion();
    }
    
    protected void closeAllWindows()
    {      
        sopCreator.closePopupWindow();
        
        if(crController.getStage() != null)
        {
            ((CaptureRegionWindowController) crController).getStage().close();
        }
    }
    
    public void postInitialize()
    {
            
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
    
    public void setPopupMode(ScreenshotPopupModes pm)
    {
        popupMode = pm;
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
}
