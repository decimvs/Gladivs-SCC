/*
 * Copyright (C) 2017 Guillermo Espert Carrasquer
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
package com.gladivs.gladivsssc.Events.EventsManager;

import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * Esta interfaç defineix els mètodes necessaris per a gestionar 
 * un dels tres events que es generen amb les tecles.
 * 
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public interface GlobalKeysListener {
    
    /**
     * Este mètode es crida quant una tecla ha sigut pressionada, pero 
     * encara no ha sigut soltada.
     * 
     * @param nke 
     */
    public void onGlobalKeyPressed(NativeKeyEvent nke);
    
    /**
     * Este mètode es crida quant una tecla ha sigut soltada.
     * @param nke 
     */
    public void onGlobalKeyReleased(NativeKeyEvent nke);
    
    /**
     * Este mètode es crida quant una tecla ha sigut pressionada i soltada.
     * És un event que indica una pulsació.
     * @param nke 
     */
    public void onGlobalKeyTyped(NativeKeyEvent nke);
}
