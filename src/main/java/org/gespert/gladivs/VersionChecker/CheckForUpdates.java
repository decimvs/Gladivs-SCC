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
package org.gespert.gladivs.VersionChecker;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import org.gespert.gladivs.MainApp;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class CheckForUpdates {
    
    public static VersionCheck checkForUpdates()
    {   
        ObjectMapper mapper = new ObjectMapper();

        try {
            VersionCheck latest = mapper.readValue(new URL("https://updates.gladivs.com/versioncheck/GladivsSSC"), VersionCheck.class);

            if(latest.getLatest_version().equals(MainApp.APP_VERSION))
            {
                return null;
            } else {
                return latest;
            }

        } catch (IOException ex) {
            return null;
        }
    }
    
}
