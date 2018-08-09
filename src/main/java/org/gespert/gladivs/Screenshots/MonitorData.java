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

import org.jitsi.impl.neomedia.imgstreaming.ScreenCapture;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;

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

    /**
     * Esta funció es la encarregada d'obtenir la captura de la pantalla.
     * Utilitza una classe envoltori en JNI per a fer la captura a nivell
     * natiu amb unes llibreries especifiques per a cada sistema.
     * @return
     */
    public BufferedImage getScreenImage()
    {
        int x, y, width, height;

        if(areaRectangle == null)
        {
            x = 0;
            y = 0;
            width = monitorRectangle.width;
            height = monitorRectangle.height;
        } else {
            x = ((Double) areaRectangle.getX()).intValue();
            y = ((Double) areaRectangle.getY()).intValue();
            width = ((Double) areaRectangle.getWidth()).intValue();
            height = ((Double) areaRectangle.getHeight()).intValue();
        }

        byte[] output = new byte[width*height*4];

        if (ScreenCapture.grabScreen(monitorIndex,x, y, width, height, output)) {
            System.out.println("OK");
        } else {
            System.out.println("Error");
        }

        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        int[] nBits = {8, 8, 8, 8};
        int[] bOffs = {1, 2, 3, 0};
        ColorModel colorModel = new ComponentColorModel(cs, nBits, true, true, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);
        DataBuffer buffer = new DataBufferByte(output, output.length);
        WritableRaster raster = Raster.createInterleavedRaster(buffer, width, height, width*4, 4, bOffs, null);

        return new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);
    }
}
