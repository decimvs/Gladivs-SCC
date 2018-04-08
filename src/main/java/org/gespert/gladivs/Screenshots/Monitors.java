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

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import javafx.scene.image.Image;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class Monitors {
    
    /**
     * Retorna la posició actual del ratolí
     * @return 
     */
    public Point getMousePosition()
    {
        return MouseInfo.getPointerInfo().getLocation();
    }
    
    /**
     * Retorna un objecte GraphicsDevice representant el monitor que corresponga
     * amb el punt passat al mètode.
     * 
     * @return 
     */
    public GraphicsDevice getMonitorFromProsition(Point pt)
    {
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gDevice = gEnv.getScreenDevices();
        
        System.out.format("Mouse X: %d Y: %d", pt.x, pt.y);
        System.out.println();
        
        for(int i = 0; i < gDevice.length; i++)
        {
            GraphicsConfiguration gc = gDevice[i].getDefaultConfiguration();
            Rectangle rec = gc.getBounds();
            
            System.out.format("Rectangle X: %d Y: %d Width: %d Height: %d", rec.x, rec.y, rec.width, rec.height);
            System.out.println();
            
            if(rec.x <= pt.x && rec.y <= pt.y && (rec.width + rec.x) >= pt.x && (rec.height + rec.y) >= pt.y)
            {
                System.out.println("Monitor: " + i);
                return gc.getDevice();
            }
        }
        
        return null;
    }
    
    /**
     * Retorna un objecte GraphicsDevice representant el monitor on es trova
     * el punter del ratolí actualment.
     * 
     * @return 
     */
    public GraphicsDevice getMonitorFromProsition()
    {
        return getMonitorFromProsition(getMousePosition());
    }
    
    protected void saveScreenShot(Image im, String path)
    {
        
    }
}
