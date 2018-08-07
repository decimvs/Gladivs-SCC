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

import java.awt.TrayIcon;
import java.util.ResourceBundle;

import org.gespert.gladivs.Instances.SystemTray;
import org.gespert.gladivs.VersionChecker.VersionChecker;
import org.gespert.gladivs.VersionChecker.VersionData;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class CheckForUpdates {
    
     public static void checkForUpdates()
     {
         VersionData vd = VersionChecker.checkForUpdates();
         ResourceBundle rb = ResourceBundle.getBundle("bundles.Main");
         
         if(vd != null)
         {
             SystemTray.getInstance().getTrayIcon().displayMessage(rb.getString("check_for_updates_notification_title"), rb.getString("check_for_updates_notification_text"), TrayIcon.MessageType.INFO);
         }
     }
}
