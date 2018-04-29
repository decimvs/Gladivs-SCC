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
package org.gespert.gladivs.Settings;


import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import javafx.scene.shape.Rectangle;
import org.gespert.gladivs.Screenshots.MonitorData;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class GeneralSettings extends Settings {
    
    private HashMap<String, Object> settingsChanged = new HashMap<>();
    
    /*********************************
     *          SETTERS
     ********************************/
    
    public void setUserSelectedImagesSavePath(String path)
    {
        setSettingValue(USER_SELECTED_IMAGE_PATH, path);
    }
    
    public void setLastImageSavePath(String path)
    {
        setSettingValue(LAST_IMAGE_PATH, path);
    }
    
    public void setSelectedRegion(MonitorData md)
    {
        ArrayList<Double> selectedRegionList = (ArrayList) Arrays.asList(
                md.getAreaRectangle().getX(), 
                md.getAreaRectangle().getY(), 
                md.getAreaRectangle().getWidth(), 
                md.getAreaRectangle().getHeight(),
                md.getMousePointer().getX(),
                md.getMousePointer().getY(),
                md.getMonitorRectangle().getX(),
                md.getMonitorRectangle().getY(),
                md.getMonitorRectangle().getWidth(),
                md.getMonitorRectangle().getHeight()
        );
        String selectedRegionString = convertDoubleListToStringList(selectedRegionList);
        
        setSettingValue(SELECTED_CAPTURE_REGION, selectedRegionString);
    }
    
    public void setSearchForAppUpdates(boolean b)
    {
        setSettingValue(SEARCH_FOR_APP_UPDATES, b);
    }
    
    public void setAutoupdateApplication(boolean b)
    {
        setSettingValue(AUTOUPDATE_APPLICATION, b);
    }
    
    public void setAutocloseMainWindow(boolean b)
    {
        setSettingValue(AUTOHIDE_MAIN_WINDOW, b);
    }
    
    /*********************************
     *          GETTERS
     ********************************/
    
    public String getUserSelectedImagesSavePath()
    {
        return getSettingValue(USER_SELECTED_IMAGE_PATH, USER_SELECTED_IMAGE_PATH_DEF);
    }
    
    public String getLastImageSavePath()
    {
        return getSettingValue(LAST_IMAGE_PATH, LAST_IMAGE_PATH_DEF);
    }
    
    public MonitorData getSelectedRegion()
    {
        String setting = getSettingValue(SELECTED_CAPTURE_REGION, SELECTED_CAPTURE_REGION_DEF);
        
        if(setting != null)
        {
            ArrayList<Double> captureRegion = convertStringListToDoubleList(setting);

            if(captureRegion.size() != 10)
            {
                return null;
            }

            MonitorData md = new MonitorData();
            md.setAreaRectangle(new Rectangle(captureRegion.get(0), captureRegion.get(1), captureRegion.get(2), captureRegion.get(3)));

            Point pt = new Point();
            pt.setLocation(captureRegion.get(4), captureRegion.get(5));
            md.setMousePointer(pt);

            java.awt.Rectangle mrec = new java.awt.Rectangle();
            mrec.setRect(captureRegion.get(6), captureRegion.get(7), captureRegion.get(8), captureRegion.get(9));
            md.setMonitorRectangle(mrec);

            return md;
        }
        else
        {
            return new MonitorData();
        }
    }
    
    public boolean getSearchForAppUpdates()
    {
        return getSettingValue(SEARCH_FOR_APP_UPDATES, SEARCH_FOR_APP_UPDATES_DEF);
    }
    
    public boolean getAutoupdateApplication()
    {
        return getSettingValue(AUTOUPDATE_APPLICATION, AUTOUPDATE_APPLICATION_DEF);
    }
    
    public boolean getAutohideMainWindow()
    {
        return getSettingValue(AUTOHIDE_MAIN_WINDOW, AUTOHIDE_MAIN_WINDOW_DEF);
    }
    
    /*********************************
     *         EMPTY
     *********************************/
    
    public void emptyUserSelectedImagesSavePath()
    {
        emptySettingValue(USER_SELECTED_IMAGE_PATH);
    }
    
    public void emptyLastImageSavePath()
    {
        emptySettingValue(LAST_IMAGE_PATH);
    }
    
    public void emptySelectedRegion()
    {
        emptySettingValue(SELECTED_CAPTURE_REGION);
    }
    
    /*********************************
     *        SETTING NAMES
     ********************************/
    
    public static final String LAST_IMAGE_PATH = "images.last-image-save-path";
    
    public static final String USER_SELECTED_IMAGE_PATH = "images.user-selected-image-save-path";
    
    public static final String SELECTED_CAPTURE_REGION = "screenshots.selected-capture-region";
    
    public static final String SEARCH_FOR_APP_UPDATES = "app.search-for-updates";
    
    public static final String AUTOUPDATE_APPLICATION = "app.autoupdate-application";
    
    public static final String AUTOHIDE_MAIN_WINDOW = "app.autohide-main-window";
    
    /*********************************************************
     *          Valors per defecte de les preferències
     *********************************************************/
    
    private static File userDir = new File(System.getProperty("user.home") + "/Pictures/GladivsSC");
    public static final String LAST_IMAGE_PATH_DEF = userDir.getAbsolutePath();
    
    public static final String USER_SELECTED_IMAGE_PATH_DEF = userDir.getAbsolutePath();
    
    public static final String SELECTED_CAPTURE_REGION_DEF = "0,";
    
    public static final boolean SEARCH_FOR_APP_UPDATES_DEF = true;
    
    public static final boolean AUTOUPDATE_APPLICATION_DEF = true;
    
    public static final boolean AUTOHIDE_MAIN_WINDOW_DEF = true;
    
    /*********************************
     *        OTHER METHODS
     ********************************/
    
    protected void setSettingValue(String setting, String value)
    {
        settingsChanged.put(setting, value);
    }
    
    protected void setSettingValue(String setting, boolean b)
    {
        settingsChanged.put(setting, b);
    }
    
    @Override
    public void saveSettings() {
        Preferences prefs = getPreferences();

        for(Map.Entry<String, Object> e : settingsChanged.entrySet())
        {
            if(e.getValue() instanceof String)
            {
                prefs.put(e.getKey(), (String) e.getValue());
            }
            else
            {
                prefs.putBoolean(e.getKey(), (boolean) e.getValue());
            }
        }
        
        settingsChanged.clear();
    }
}
