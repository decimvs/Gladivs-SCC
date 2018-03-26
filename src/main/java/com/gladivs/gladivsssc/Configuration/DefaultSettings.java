/*
 * Copyright (C) 2017 Guillermo Espert Carrasquer
 *
 * This file is part of Gladivs Simple Screen Capture
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
package com.gladivs.gladivsssc.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class DefaultSettings extends Settings {
    
    /*********************************
     *          KEYBOARD
     ********************************/
    
    //Tecla per defecte per a la captura de pantalla: Impr Pant -> 3639
    protected static final List<Integer> TAKE_SCREENSHOT_DEF = Arrays.asList(3639);
    
    //Combinacio de tecles per a la actualitzacio dels monitors: CTRL + Impr Pant -> 29 + 3639
    protected static final List<Integer> REFRESH_MONITORS_DEF = Arrays.asList(29, 3639);
}
