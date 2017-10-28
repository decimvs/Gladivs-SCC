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
package com.gladivs.gladivsssc.Configuration;

/**
 * Provides a object map for the JSON config files
 * @author Guillermo
 */
public class Values 
{
    /* Combination keys for screen capture */
    private String parameterName;
    private Integer intValue;
    private String stringValue;
    private Integer parameterId;
    private String parameterValueType;
    private String parameterString;
    
    /*----- GETTERS -----*/
    
    public String getParameterName(){ return parameterName; }
    public Integer getIntValue(){ return intValue; }
    public String getStringValue(){ return stringValue; }
    public Integer getParameterId() { return parameterId; }
    public String getParameterValueType() { return parameterValueType; }
    public String getParameterString() { return parameterString; }
    
    /*----- SETTERS ------*/
    
    /* Combination keys for screen capture */
    public void setParameterName(String k){ this.parameterName = k; }
    public void setParameterValue(Integer k){ this.intValue = k; }
    public void setParameterValue(String k){ this.stringValue = k; }
    public void setParameterId(Integer k){ this.parameterId = k; }
    public void setParameterValueType(String k){ this.parameterValueType = k; }
    public void setParameterString(String k) { this.parameterString = k; }
}
