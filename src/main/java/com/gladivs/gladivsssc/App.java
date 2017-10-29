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
package com.gladivs.gladivsssc;

import com.gladivs.gladivsssc.Events.ApplicationEventsSuscriber;
import com.gladivs.gladivsssc.Instances.ConfigurationInstance;
import com.gladivs.gladivsssc.Instances.JNativeHookInstance;
import com.gladivs.gladivsssc.Instances.LoggerInstance;
import com.gladivs.gladivsssc.Instances.SystemTrayIconInstance;
import com.gladivs.gladivsssc.Instances.WindowsInstances;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 * Esta classe proporciona un mecanisme convenient per a establir el procés de 
 * arrancada de la aplicació.
 * 
 * Es defineix de manera ordenada la seqüència amb la que s'aniràn carregant
 * els diferents components de la aplicació.
 * 
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class App {
    
    private static ApplicationEventsSuscriber applicationEventsSuscriber;
    private static final Logger LOGGER = LoggerInstance.getLogger();
    
    /**
     * Inicialitza tots els components de la aplicació de manera seqüencial.
     */
    public static void Start()
    {
        //Primera tasca: arrancar el sistema de control per als events de teclat
        //en la capa de SO.
        LoadJNativeHook();
        
        //Segona tasca: arrancar el listener d'events general de l'aplicació.
        LoadEventListener();
        
        //Tercera tasca: crear una instància de la classe de gestió de la configuració
        LoadConfiguration();
        
        //Quarta tasca: carregar i mostrar la icona dins l'àrea de notificació
        //del sistema operatiu.
        LoadSystemTrayIcon();
        
        //Quinta tasca: carregar la finestra principal sense mostrarla.
        LoadMainWindow();
                
        LOGGER.info("Gladivs Simple Capture, application is started");
    }
    
    private static void LoadJNativeHook()
    {
        try {
            GlobalScreen.registerNativeHook();
            
            GlobalScreen.addNativeKeyListener(JNativeHookInstance.getInstance());
        }
        catch (NativeHookException ex) {
            JOptionPane.showMessageDialog(null, "Fatal error was occured: native events listener couldn't been loaded. Application can't continue and will be stopped. Please contact with us and tell us about this error, we will try to fix it. Thank you!", "Fatal Error", JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }

        // Get the logger for "org.jnativehook" and set the level to warning.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
    }
    
    /**
     * Inicia el sistema de control dels events enregistrats en la capa del 
     * Sistema Operatiu. Este sistema enviarà els events a la aplicació, que 
     * reaccionarà si les tecles capturades perteneixen a la aplicació.
     */
    private static void LoadEventListener()
    {
        //ApplicationEventsSuscriber es un listener global. Escolta i gestiona
        //els events com la captura de pantalla i tots els demés. Este listener te
        //prioritat predeterminada i és l'últim en rebre els events, si no s'ha definit cap
        //altre amb prioritat alta.
        applicationEventsSuscriber = new ApplicationEventsSuscriber();
    }
    
    /**
     * Crea una nova instància de la classe Configuration i la deixa
     * disponible per a la resta de la aplicació.
     */
    private static void LoadConfiguration()
    {
        ConfigurationInstance.getConfiguration();
    }
    
    /**
     * Carrega i mostra la icona dins l'àrea de notificacions del sistema
     * operatiu.
     */
    private static void LoadSystemTrayIcon()
    {
        SystemTrayIconInstance.getInstance().displayIconInTray();
    }
    
    /**
     * Carrega la finestra principal i la deixa preparada per a la seua 
     * utilització.
     */
    private static void LoadMainWindow()
    {
        WindowsInstances.getMainWindow();
    }
}
