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
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class MonitorData {
    private java.awt.Rectangle monitorRectangle;
    private javafx.scene.shape.Rectangle areaRectangle;
    private Point mousePointer;
    private int monitorIndex;

    public MonitorData()
    {
    }
    
    public MonitorData(Rectangle rectangle, Point point) {
        this.monitorRectangle = rectangle;
        this.mousePointer = point;
    }
    
    public java.awt.Rectangle getMonitorRectangle() {
        return monitorRectangle;
    }

    public void setMonitorRectangle(java.awt.Rectangle rectangle) {
        this.monitorRectangle = rectangle;
    }

    public Point getMousePointer() {
        return mousePointer;
    }

    public void setMousePointer(Point point) {
        this.mousePointer = point;
    }

    public javafx.scene.shape.Rectangle getAreaRectangle() {
        return areaRectangle;
    }

    public void setAreaRectangle(javafx.scene.shape.Rectangle areaRectangle) {
        this.areaRectangle = areaRectangle;
    }
    
    public boolean isDefinedMonitorArea()
    {
        return monitorRectangle != null && mousePointer != null;
    }
    
    public boolean isDefinedSelectionArea()
    {
        return isDefinedMonitorArea() && areaRectangle != null;
    }
    
    public int getMonitorIndex()
    {
        return monitorIndex;
    }
    
    public void setMonitorIndex(int index)
    {
        monitorIndex = index;
    }
    
    public BufferedImage getScreenImage()
    {
        try {
            return new Robot().createScreenCapture(getMonitorRectangle());
        } catch (AWTException ex) {
            Logger.getLogger(TakeScreenshot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
