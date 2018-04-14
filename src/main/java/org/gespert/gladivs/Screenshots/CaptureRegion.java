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
import org.gespert.gladivs.GUI.Controllers.CaptureRegionWindowController;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class CaptureRegion extends Monitors {
    
    private static CaptureRegion captureRegion;
    
    public static void Do()
    {
        captureRegion = new CaptureRegion();
        captureRegion.captureRegion();
    }
    
    public static void setRegion()
    {
        captureRegion = new CaptureRegion();
    }
    
    private void captureRegion()
    {
        Point pt = getMousePosition();
        GraphicsDevice gDevice = getMonitorFromProsition(pt);
        
        CaptureRegionWindowController crw = new CaptureRegionWindowController(this);
        crw.createNewWindow(gDevice.getConfigurations()[0].getBounds());
    }
    
    private void setCaptureRegion()
    {
        
    }
    
    public boolean saveRegion(BufferedImage bf, File selectedFile)
    {
        ImagesIO imgIO = new ImagesIO();
        return imgIO.saveImageToDisk(bf, selectedFile);
    }
}
