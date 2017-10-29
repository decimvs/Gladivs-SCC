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
package com.gladivs.gladivsssc.Keyboard;

import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * Classe auxiliar per a definir mètodes que ajuden a verificar i controlar
 * la entrada de teclat.
 * 
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class ManageKeys {
    
    /**
     * Retorna true si el codi de tecla passada es una tecla modificadora
     * o false en cas contrari.
     * 
     * @param kc
     * @return 
     */
    public static boolean isModifier(int kc)
    {
        switch(kc)
        {
            case NativeKeyEvent.VC_SHIFT:
            case NativeKeyEvent.VC_CONTROL:
            case NativeKeyEvent.VC_ALT:
            case NativeKeyEvent.VC_META:
            case NativeKeyEvent.VC_CONTEXT_MENU:
                return true;
            default:
                return false;
        }
    }
    
}
