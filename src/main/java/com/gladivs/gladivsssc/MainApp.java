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

package com.gladivs.gladivsssc;

import com.gladivs.gladivsssc.Instances.JNativeHookInstance;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.stage.Stage;


public class MainApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception 
    {  
        //Esta opció permet tancar la aplicació quant no queden finestres obertes.
        //Per a la nostra aplicació no interesa este comportament, i el fixem a false.
        //Ara serà necessari cridar implicitament al mètode: Platform.exit(), per a tancar
        //la aplicació.
        Platform.setImplicitExit(false);
        
        //Iniciem la aplicació.
        App.Start();
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
     *Esta funció definida en Application es invocada quant l'aplicació rep l'ordre de tancar o finalitzar
     * Finalitza tots els fils de la aplicació.
     */
    @Override
    public void stop()
    {
        //Finalitzem el sistema de control d'events del sistema.
        JNativeHookInstance.stop();
        
        System.exit(0);
    }
}
