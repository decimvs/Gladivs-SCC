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
package org.gespert.gladivs.TrayMenu;

import java.awt.MenuItem;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.gespert.gladivs.Screenshots.CaptureRegion;
import org.gespert.gladivs.Screenshots.MonitorData;
import org.gespert.gladivs.Screenshots.TakeScreenshot;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class GMenuItem extends MenuItem {
    
    public static final int TAKE_SCREENSHOT = 0;
    public static final int CAPTURE_REGION = 1;
    public static final int SELECT_REGION = 2;
    
    private MonitorData monitorData;
    private int action;
    
    public GMenuItem(String label, MonitorData md, int action)
    {
        super(label);
        
        monitorData = md;
        this.action = action;
    }
    
    public void doAction()
    {
        try {
            Thread.sleep(250);
            
            switch(action)
            {
                case 0:
                    TakeScreenshot.Do(monitorData);
                    break;
                case 1:
                    CaptureRegion.Do(monitorData);
                    break;
                case 2:
                    CaptureRegion.setRegion(monitorData);
                    break;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(GMenuItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
