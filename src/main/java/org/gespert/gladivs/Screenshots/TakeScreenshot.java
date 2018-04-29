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
package org.gespert.gladivs.Screenshots;

import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.stage.Stage;
import org.gespert.gladivs.GUI.Controllers.ScreenshotPopupModes;
import org.gespert.gladivs.GUI.ScreenshotOptionsPopup;
import org.gespert.gladivs.GUI.OptionsPopup.ScreenshotOptionsPopupCreator;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class TakeScreenshot extends Monitors implements ScreenshotOptionsPopup {
    
    private static TakeScreenshot takeScreenshot;
    private BufferedImage screenShot;
    private ScreenshotOptionsPopupCreator sopPopup;
    
    /**
     * Pren una captura de pantalla i mostra el popup d'opcions per a
     * que l'usuari puga gestionar l'imatge.
     * Esta opció pren la captura del monitor on es trobe el punter del
     * ratolí.
     */
    public static void Do()
    {
        takeScreenshot = new TakeScreenshot();
        takeScreenshot.takeScreenshot();
    }
    
    /**
     * Pren una captura de pantalla i mostra el popup d'opcions per a
     * que l'usuari puga gestionar l'imatge.
     * Utilitza un objecte MonitorData per a determinar quin monitor
     * realitazarà la captura.
     * @param md 
     */
    public static void Do(MonitorData md)
    {
        takeScreenshot = new TakeScreenshot();
        takeScreenshot.takeScreenshot(md);
    }
    
    /**
     * Realitza una captura de pantalla i la desa directament
     * a la carpeta seleccionada per l'usuari. No mostra la finestra
     * popup d'opcions.
     * 
     * @param md 
     */
    public static void DoAndSave(MonitorData md)
    {
        TakeScreenshot takeScreenshot = new TakeScreenshot();
        takeScreenshot.takeScreenshotAndSave(md);
    }
    
    private void takeScreenshot()
    {
        Point pt = getMousePosition();
        GraphicsDevice gDevice = getMonitorFromProsition(pt);
        MonitorData md = new MonitorData(gDevice.getConfigurations()[0].getBounds(), pt);
        
        takeScreenshot(md);
    }
    
    private void takeScreenshot(MonitorData md)
    {
        screenShot = md.getScreenImage();

        sopPopup = new ScreenshotOptionsPopupCreator(this, ScreenshotPopupModes.SCREEN_CAPTURE);
        sopPopup.createNewPopup(md);
    }
    
    private void takeScreenshotAndSave(MonitorData md)
    {
        screenShot = md.getScreenImage();
        
        saveCaptureToSelectedLocation(ImagesIO.getCompleteImageSavePath());
    }

    @Override
    public void resetSelection() {
        //Do nothing
    }

    @Override
    public boolean saveCaptureToSelectedLocation(File selectedFile) {
        ImagesIO imgIO = new ImagesIO();
        return imgIO.saveImageToDisk(screenShot, selectedFile);
    }

    @Override
    public Stage getStage() {
        return null;
    }

    @Override
    public void setSelectedRegion() {
        //Nothing to do
    }
    
}
