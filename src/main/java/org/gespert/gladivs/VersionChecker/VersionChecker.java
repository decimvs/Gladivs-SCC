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
import org.gespert.gladivs.MainApp;

import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class VersionChecker {
    
    public static VersionData checkForUpdates()
    {   
        ObjectMapper mapper = new ObjectMapper();

        try {
            VersionData latest = mapper.readValue(new URL("https://updates.gladivs.com/versioncheck/GladivsSC"), VersionData.class);

            if(latest.getMajor_version() > MainApp.APP_MAJOR_VERSION)
            {
                return latest;
            } else {
                if(latest.getMinor_version() > MainApp.APP_MINUS_VERSION && latest.getMajor_version() == MainApp.APP_MAJOR_VERSION)
                {
                    return latest;
                } else {
                    if(latest.getRevision_version() > MainApp.APP_REVISION_VERSION && latest.getMajor_version() == MainApp.APP_MAJOR_VERSION && latest.getMinor_version() == MainApp.APP_MINUS_VERSION)
                    {
                        return latest;
                    } else {
                        return null;
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
