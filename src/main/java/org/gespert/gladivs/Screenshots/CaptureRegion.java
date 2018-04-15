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
import org.gespert.gladivs.Instances.SettingsInstance;

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
        captureRegion.setCaptureRegion();
    }
    
    public static void showSelectedRegion()
    {
        captureRegion = new CaptureRegion();
        captureRegion.showRegion();
    }
    
    public static void Do(MonitorData md)
    {
        captureRegion = new CaptureRegion();
        captureRegion.captureRegion(md);
    }
    
    public static void setRegion(MonitorData md)
    {
        captureRegion = new CaptureRegion();
        captureRegion.setCaptureRegion(md);
    }
    
    public static void showSelectedRegion(MonitorData md)
    {
        captureRegion = new CaptureRegion();
        captureRegion.showRegion(md);
    }
    
    private void captureRegion()
    {
        Point pt = getMousePosition();
        GraphicsDevice gDevice = getMonitorFromProsition(pt);
        MonitorData md = new MonitorData(gDevice.getConfigurations()[0].getBounds(), pt);
        captureRegion(md);
    }
    
    private void setCaptureRegion()
    {
        Point pt = getMousePosition();
        GraphicsDevice gDevice = getMonitorFromProsition(pt);
        MonitorData md = new MonitorData(gDevice.getConfigurations()[0].getBounds(), pt);
        setCaptureRegion(md);
    }
    
    private void showRegion()
    {
        MonitorData md = SettingsInstance.getGeneralSettings().getSelectedRegion();
        showRegion(md);
    }
    
    private void captureRegion(MonitorData md)
    {
        CaptureRegionWindowController crw = new CaptureRegionWindowController(this);
        crw.captureRegion(md);
    }
    
    private void setCaptureRegion(MonitorData md)
    {
        CaptureRegionWindowController crw = new CaptureRegionWindowController(this);
        crw.setCaptureRegion(md);
    }
    
    private void showRegion(MonitorData md)
    {
        CaptureRegionWindowController crw = new CaptureRegionWindowController(this, md.getAreaRectangle());
        crw.showSelectedArea(md);
    }
    
    public boolean saveRegion(BufferedImage bf, File selectedFile)
    {
        ImagesIO imgIO = new ImagesIO();
        return imgIO.saveImageToDisk(bf, selectedFile);
    }
}
