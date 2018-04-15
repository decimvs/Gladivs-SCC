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
package org.gespert.gladivs.GUI;

import java.io.File;
import javafx.stage.Stage;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public interface ScreenshotOptionsPopup {
    
    /**
     * Reseteja la selecció realitzada i permet de realitzar una nova selecció.
     */
    public void resetSelection();
    
    public boolean saveCaptureToSelectedLocation(File selectedFile);
    
    public Stage getStage();
    
    public void setSelectedRegion();
    
}
