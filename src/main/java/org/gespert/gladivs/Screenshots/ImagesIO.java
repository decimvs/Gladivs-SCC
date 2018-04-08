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
package org.gespert.gladivs.Screenshots;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import javax.imageio.ImageIO;
import org.hashids.Hashids;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class ImagesIO {
    
    /**
     * Desa una image a la ruta especificada.
     * Si el directori al que fa referencia l'arxiu no existeix, es crearà.
     * @param bf
     * @param file
     * @return 
     */
    public boolean saveImageToDisk(BufferedImage bf, File file)
    {
        if(file != null)
        {
            //Variables per la comprobació del directori
            String path = file.getParent();
            File dirPath = new File(path);
            
            try {
                //Si el direcotori no existeix el creem.
                if(!dirPath.exists())
                {
                    Files.createDirectories(dirPath.toPath());
                }
            
                ImageIO.write(bf, "PNG", file);

                return true;
            }
            catch(Exception ex)
            {
                System.out.println("Error al desar l'imatge de la captura. Error: " + ex.getLocalizedMessage());
            }
        }
       
        return false;
    }
    
    /**
     * Crea un nom d'arxiu amb la data i hora. Ademés li afegeix un codi adicional
     * basat en un hash que es calcula per mitjà del nanosegons en el moment
     * de crear el hash. Este mètode dona una alta probabilitat d'unicitat del 
     * nom d'arxiu que es cree.
     * @return 
     */
    public static String generateFileName()
    {
        LocalDateTime localDate = LocalDateTime.now();
        Hashids hashids = new Hashids("gladivs salt");
        String hash = hashids.encode(localDate.getNano());
        
        String fileName = new String()
            .concat("Captura")
            .concat("_")
            .concat(String.valueOf(localDate.getDayOfMonth()))
            .concat("-")
            .concat(String.valueOf(localDate.getMonthValue()))
            .concat("-")
            .concat(String.valueOf(localDate.getYear()))
            .concat("_")
            .concat(String.valueOf(localDate.getHour()))
            .concat(".")
            .concat(String.valueOf(localDate.getMinute()))
            .concat(".")
            .concat(String.valueOf(localDate.getSecond()))
            .concat("_")
            .concat(hash)
            .concat(".png");
        
        return fileName;
    }
}
