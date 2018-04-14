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
package org.gespert.gladivs.Instances;

import org.gespert.gladivs.GUI.Stages.AboutUsWindowCreator;
import org.gespert.gladivs.GUI.Stages.KeysInputDialogWindowCreator;
import org.gespert.gladivs.GUI.Stages.SettingsWindowCreator;



/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class Windows {
    
    private static SettingsWindowCreator sdCreator;
    private static KeysInputDialogWindowCreator kidCreator;
    private static AboutUsWindowCreator auCreator;
      
    /**
     * Retorna una instància de SettingsWindowCreator, que conté tant
     * el Stage com el Controller de la finestra.
     * @return 
     */
    public static SettingsWindowCreator getSettingsDialog()
    {
        if(sdCreator == null)
        {
            sdCreator = new SettingsWindowCreator();
            sdCreator.createNewWindow();
        }
        
        return sdCreator;
    }
    
    /**
     * Retorna una instància de KeysInputDialogWindowCreator, que conté tant
     * el Stage com el Controller de la finestra.
     * @return 
     */
    public static KeysInputDialogWindowCreator getKeyInputDialog()
    {
        if(kidCreator == null)
        {
            kidCreator = new KeysInputDialogWindowCreator();
            kidCreator.createNewWindow();
        }
        
        return kidCreator;
    }
    
    /**
     * Retorna una instància de AboutUsWindowCreator, que conté tant
     * el Stage com el Controller de la finestra.
     * @return 
     */
    public static AboutUsWindowCreator getAboutUsDialog()
    {
        if(auCreator == null)
        {
            auCreator = new AboutUsWindowCreator();
            auCreator.createNewWindow();
        }
        
        return auCreator;
    }
    
    /*=============================================
                    DESTROYERS
    ===============================================*/
    
    /**
     * Desintància SettingsWindowCreator
     */
    public static void destroySettingsWindowCreator()
    {
        sdCreator = null;
    }
    
     /**
     * Desistància KeysInputDialogWindowCreator
     */
    public static void destroyKeysInputDialogWindowCreator()
    {
        kidCreator = null;
    }
    
    /**
     * Desistància AboutUsDialogCreator
     */
    public static void destroyAboutUsDialogCreator()
    {
        auCreator = null;
    }
}
