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

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import javafx.application.Platform;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * Proporciona una classe que servix de façade entre la capa nativa
 * dels events i la gestió a nivell d'aplicació d'estos events.
 * 
 * En esta classe es registren tots els listeners per a estos events.
 * Quant els events es produixquen s'aniràn propagant cap als listeners 
 * enregistrats.
 * 
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class GlobalListener {
    
    /**
     * Defineix la llista de tots els listeners que estàn suscrits
     * als events disponibles i la seua prioritat.
     */
    private TreeMap<Integer, GlobalKeysListener> listeners;
    
    /** Contador de listeners **/
    private int listenersOrder;
    
    /** Consume event flag **/
    private boolean consumeEvent = false;
    
    /** Constants **/
    public static final int KEY_PRESSED = 0;
    public static final int KEY_RELEASED = 1;
    public static final int KEY_TYPED = 2;
    
    public GlobalListener()
    {
        listeners = new TreeMap<>();
        listenersOrder = 0;
    }
    
    
    
    /**
     * Afegeix un nou listener suscrit a este event.
     * 
     * @param gkl 
     */
    public void addListener(GlobalKeysListener gkl)
    {
        addListener(gkl, --listenersOrder);
    }
    
    /**
     * Permet de registrar un listener amb ordre de prioritat.
     * Este ordre de prioritat deu ser qualsevol número > 0.
     * El listener amb el número de prioritat més alt, consumirà l'event
     * i tallarà la cadena de propagació.
     * 
     * @param gkl 
     * @param consumeEventPriority 
     */
    public void addListener(GlobalKeysListener gkl, int consumeEventPriority)
    {
        listeners.put(consumeEventPriority, gkl);
    }
    
    /**
     * Trau un listener de la llista de registre dels listeners.
     * 
     * @param gkl 
     */
    public void removeListener(GlobalKeysListener gkl)
    {
        for(Entry<Integer, GlobalKeysListener> entry : listeners.entrySet())   
        {
            if(entry.getValue() == gkl)
            {
                listeners.remove(entry.getKey());
            }
        }
    }
    
    /**
     * Receptor per al event de tecla pressionada
     * 
     * @param nke 
     */
    public void KeyPressed(NativeKeyEvent nke)
    {
        
        Platform.runLater(()->{
            routeEvents(KEY_PRESSED, nke);
        });
    }
    
    /**
     * Receptor per al event de tecla soltada
     * @param nke 
     */
    public void KeyReleased(NativeKeyEvent nke)
    {
        Platform.runLater(()->{
            routeEvents(KEY_RELEASED, nke);
        });
    }
    
    /**
     * Receptor per al event tecla polsada (pressionar i soltar).
     * @param nke 
     */
    public void KeyTyped(NativeKeyEvent nke)
    {
        Platform.runLater(()->{
            routeEvents(KEY_TYPED, nke);
        });
    }
    
    /**
     * Permet consumir un event i evitar la propagació
     */
    public void consumeEvent()
    {
        this.consumeEvent = true;
    }
    
    /**
     * Rep els events i els propaga a tots els listeners.
     * Si hi ha ordre de consum, enviará l'event només al listener
     * amb ordre major dins la llista.
     * 
     * @param eventType
     * @param nke 
     */
    private void routeEvents(int eventType, NativeKeyEvent nke)
    {
        NavigableMap<Integer, GlobalKeysListener> nm = listeners.descendingMap();
        
        for(Entry<Integer, GlobalKeysListener> entry : nm.entrySet())
        {
            fireEvents(eventType, nke, entry.getValue());
            
            //Si el event está marcat amb un número de prioritat,
            //és finalitza la propagació.
            if(this.consumeEvent)
            {
                this.consumeEvent = false;
                break;
            }
        }
    }
    
    /**
     * Executa el mètode definit en cada listener per al event esdevingut.
     * 
     * @param eventType
     * @param nke
     * @param gkl 
     */
    private void fireEvents(int eventType, NativeKeyEvent nke, GlobalKeysListener gkl)
    {
        switch(eventType)
        {
            case KEY_PRESSED:
                gkl.onGlobalKeyPressed(nke);
                break;
            case KEY_RELEASED:
                gkl.onGlobalKeyReleased(nke);
                break;
            case KEY_TYPED:
                gkl.onGlobalKeyTyped(nke);
                break;
        }
    }
}
