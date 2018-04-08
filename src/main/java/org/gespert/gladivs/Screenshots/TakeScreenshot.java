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

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import org.gespert.gladivs.GUI.ScreenshotOptionsPopup;
import org.gespert.gladivs.GUI.Stages.ScreenshotOptionsPopupCreator;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class TakeScreenshot extends Monitors implements ScreenshotOptionsPopup {
    
    private static TakeScreenshot takeScreenshot;
    private BufferedImage screenShot;
    private ScreenshotOptionsPopupCreator sopPopup;
    
    public static void Do()
    {
        takeScreenshot = new TakeScreenshot();
        takeScreenshot.takeScreenshot();
    }
    
    private void takeScreenshot()
    {
        try {
            Point pt = getMousePosition();
            GraphicsDevice gDevice = getMonitorFromProsition(pt);
            screenShot = new Robot().createScreenCapture(gDevice.getConfigurations()[0].getBounds());
            
            sopPopup = new ScreenshotOptionsPopupCreator(this);
            sopPopup.createNewPopup();
            
        } catch (AWTException ex) {
            Logger.getLogger(TakeScreenshot.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
}