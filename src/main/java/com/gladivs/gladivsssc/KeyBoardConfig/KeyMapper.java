/*
 * Copyright (C) 2016 Guillermo Espert Carrasquer [gespert@yahoo.es]
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
package com.gladivs.gladivsssc.KeyBoardConfig;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class KeyMapper {
    
    private int idNativeKey;
    private String nativeKeyConstantName;
    private String stringLang;
    
    /**
     * Default non arguments constructor
     */
    public KeyMapper()
    {
        this(null);
    }
    
    /**
     * Constructor that accepts a string to set as visible
     * @param name 
     */
    public KeyMapper(String name)
    {
        this.idNativeKey = 0;
        this.nativeKeyConstantName = null;
        this.stringLang = name;
    }
    
    @Override
    public String toString()
    {
        return stringLang;
    }
    
    public void setIdNativeKey(int idNativeKey){ this.idNativeKey = idNativeKey; }
    public void setNativeKeyConstantName(String nativeKeyConstantName){ this.nativeKeyConstantName = nativeKeyConstantName; }
    public void setStringLang(String stringLang){ this.stringLang = stringLang; }
    
    public int getIdNativeKey(){ return idNativeKey; }
    public String getNativeKeyConstantName(){ return nativeKeyConstantName; }
    public String getStringLang(){ return stringLang; }
}
