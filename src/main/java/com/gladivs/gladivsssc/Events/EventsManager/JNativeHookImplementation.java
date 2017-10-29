/*
 * Copyright (C) 2016 - 2017 Guillermo Espert Carrasquer [gespert@yahoo.es]
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

import com.gladivs.gladivsssc.Instances.GlobalListenerInstance;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Esta clase implementa la interfaç NativeKeyListener.
 * Proporciona accés als mètodes que gestionaràn els events,
 * que están definits en la classe GlobalListener.
 * 
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class JNativeHookImplementation implements NativeKeyListener {
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent nke)
    {
        GlobalListenerInstance.getInstance().KeyPressed(nke);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke)
    {
        GlobalListenerInstance.getInstance().KeyReleased(nke);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        GlobalListenerInstance.getInstance().KeyTyped(nke);
    }
    
}
