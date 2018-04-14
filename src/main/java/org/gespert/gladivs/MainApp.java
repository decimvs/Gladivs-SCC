/*
 * Copyright (C) 2016-2018 Guillermo Espert Carrasquer [gespert@yahoo.es]
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

package org.gespert.gladivs;

import javafx.application.Application;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import org.gespert.gladivs.Instances.KeysListener;
import org.gespert.gladivs.TrayMenu.GladivsTrayMenu;
import org.gespert.gladivs.Instances.SystemTray;


public class MainApp extends Application {
    
    public static final String APP_VERSION = "0.7";
    public static final String APP_NAME = "Gladivs Screen Capture";
    public static final String APP_AUTHOR = "Guillermo Espert Carrasquer";
    
    public MainApp()
    {        
        KeysListener.registerHook();

        KeysListener.addGlobalKeyListener();
    }
    
    @Override
    public void start(Stage stage) throws Exception 
    {  
        Platform.setImplicitExit(false);
        GladivsTrayMenu trayMenu = SystemTray.getInstance();
        trayMenu.displayIconInTray();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Esta funció definida en Application es invocada quant l'aplicació rep l'ordre de tancar o finalitzar
     * Finalitza tots els fils de la aplicació.
     */
    @Override
    public void stop()
    {
        KeysListener.unregisterHook();
        SystemTray.getInstance().removeIconFromTray();
        Platform.exit();
        System.exit(1);
    }
}
