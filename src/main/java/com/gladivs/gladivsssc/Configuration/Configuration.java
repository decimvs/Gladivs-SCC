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

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guillermo
 */
public class Configuration 
{
    private HashMap<String, Values> defaultValues;
    private HashMap<String, Values> userValues;
    private ObjectMapper objMapper;
    private final String configFile = "defaultConfig.json";
    
    /** Constants definition **/
    
    //Print screen combination keys
    public final String PSKEY1 = "printScreenKey1";
    public final String PSKEY2 = "printScreenKey2";
    public final String PSKEY3 = "printScreenKey3";
    
    //Refresh monitor miniatures combination keys
    public final String RMKEY1 = "refreshMonitorMoniaturesKey1";
    public final String RMKEY2 = "refreshMonitorMoniaturesKey2";
    public final String RMKEY3 = "refreshMonitorMoniaturesKey3";
    
    //Other settings
    public final String SAVE_PATH = "screenshotsSavePath";
    public final String CHECK_UPDATES = "checkServerForNewUpdates";
    public final String DEFAULT_LANGUAGE = "defaultUserLanguage";
    public final String AUTOHIDE_WINDOW = "autohideWindowWhenTakeScreenshot";
    public final String NEW_INSTALLATION = "isTheFirstTimeThatAppRuns";
    
    public Configuration()
    {
        objMapper = new ObjectMapper();
        
        try
        {
            //Read and load default parameters
            readDefaultParameters();
        
            //Get and load user parameters
            readUserParameters();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void readUserParameters()
    {
        try 
        {
            File configdir = new File(System.getProperty("user.home") + "/.gladivs/config");
            File usrconf = new File(System.getProperty("user.home") + "/.gladivs/config/user.json");
            
            if(!configdir.exists())
            {
                configdir.mkdirs();
            }
            
            usrconf.createNewFile();
            
            if(usrconf.length() > 0L)
            {
                List tmpReadParams = objMapper.readValue(usrconf, List.class);
                createUserParametersList(tmpReadParams);
            }
            else
            {
                userValues = defaultValues;
                writeUserParameters(defaultValues);
            }
            
        } catch (IOException ex) 
        {
            userValues = defaultValues;
            writeUserParameters(defaultValues);
            System.out.println(ex.getMessage());
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createUserParametersList(List tmpParams)
    {
        userValues = new HashMap<String, Values>();
        
        try
        {
            for(int i = 0; i < tmpParams.size(); i++)
            {
                Values v = new Values();
                LinkedHashMap lhm = (LinkedHashMap) tmpParams.get(i);
                String paramVal = null;

                if(lhm.containsKey("parameterId"))
                {
                    v.setParameterId((Integer) lhm.get("parameterId"));
                }
                else
                {
                    throw new Exception("Error parameterId not found");
                }
                
                if(lhm.containsKey("parameterName"))
                {
                    v.setParameterName((String) lhm.get("parameterName"));
                }
                else
                {
                    throw new Exception("Error parameterName not found");
                }
                
                if(lhm.containsKey("parameterString"))
                {
                    v.setParameterString((String) lhm.get("parameterString"));
                }
                else
                {
                    throw new Exception("Error parameterString not found");
                }

                if(lhm.containsKey("parameterValue"))
                {
                    paramVal = String.valueOf(lhm.get("parameterValue"));
                }
                else
                {
                    throw new Exception("Error parameterValue not found");
                }

                //Determine if parameter value can be converted to Integer value
                if (paramVal != null && paramVal.matches("^-?\\d+$") && paramVal.length() >= 1)
                {
                    if(defaultValues.get(lhm.get("parameterName")).getParameterValueType().equals("int"))
                    {
                        //Try to convert value to Integer
                        try
                        {
                            v.setParameterValue(Integer.parseInt(paramVal));
                            v.setParameterValueType("int");
                        }
                        catch(Exception e)
                        {
                            System.out.println("Error with value format. Int found; real value: String. " + e.getMessage());
                        }
                    }
                    else
                    {
                        throw new Exception("Error with value format. Int found; real value: String");
                    }
                }
                else
                {
                    if(!defaultValues.get(lhm.get("parameterName")).getParameterValueType().equals("int"))
                    {
                        v.setParameterValue(paramVal);
                        v.setParameterValueType("String");
                    }
                    else
                    {
                        throw new Exception("Error with value format. String found; real value: int");
                    }
                }

                userValues.put(v.getParameterName(), v);
            }
            
            if(userValues == null || userValues.size() != defaultValues.size()) userValues = defaultValues;
        }
        catch(Exception ex)
        {
            userValues = defaultValues;
            ex.printStackTrace();
            System.out.println("Default Settings are loaded.");
        }
    }     
     
    
    /**
     * Reads configuration parameters from configuration file
     */
    private void readDefaultParameters()
    {
        URL source = getClass().getResource("/config/" + configFile);
        
        try 
        {
            List tmpReadParams = objMapper.readValue(source, List.class);
            
            createDefaultParametersList(tmpReadParams);
        } catch (IOException ex) 
        {
            System.out.println("Configuration file load error");
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Map readed parameters into an object and stores it on an ArrayList
     * @param tmpParams 
     */
    private void createDefaultParametersList(List tmpParams)
    {
        defaultValues = new HashMap<String, Values>();
        
        for(int i = 0; i < tmpParams.size(); i++)
        {
            Values v = new Values();
            LinkedHashMap lhm = (LinkedHashMap) tmpParams.get(i);
            
            v.setParameterId((Integer) lhm.get("parameterId"));
            v.setParameterName((String) lhm.get("parameterName"));
            v.setParameterString((String) lhm.get("parameterString"));
            
            String paramVal = String.valueOf(lhm.get("parameterValue"));
            
            //Determine if parameter value can be converted to Integer value
            if (paramVal.matches("^-?\\d+$") && paramVal.length() >= 1)
            {
                //Try to convert value to Integer
                try
                {
                    v.setParameterValue(Integer.parseInt(paramVal));
                    v.setParameterValueType("int");
                }
                catch(NumberFormatException e)
                {
                    //If finally it can't be converted to Integer so store it as a String
                    v.setParameterValue(paramVal);
                    v.setParameterValueType("String");
                }
            }
            else
            {
                v.setParameterValue(paramVal);
                v.setParameterValueType("String");
            }
            
            defaultValues.put(v.getParameterName(), v);
        }
    }
    
    /**
     * Update the specified parameter name with the specified parameter value.
     * If the boolean parmeter of the method is true, this changes are stored immediately, 
     * if value is false, the changes are not writed to a config file. If save is not performed, 
     * all modified parameters will be lost when program finishes.
     * @param paramName
     * @param paramValue
     * @param writeNow 
     */
    public void modifyParameter(String paramName, Values paramValue, boolean writeNow)
    {
        if(userValues != null && userValues.containsKey(paramName))
        {
            userValues.replace(paramName, paramValue);
        }
        
        if(writeNow) writeUserParameters(userValues);
    }
    
    /**
     * Update the specified parameter name with the specified parameter value.
     * This method writes inmediately to a config file.
     * @param paramName
     * @param paramValue 
     */
    public void modifyParameter(String paramName, Values paramValue)
    {
        modifyParameter(paramName, paramValue, true);
    }
    
    public void writeUserParameters()
    {
        writeUserParameters(userValues);
    }
    
    public void writeUserParameters(HashMap<String, Values> from)
    {
        JsonFactory factory = new JsonFactory();
       
        try {
            File configdir = new File(System.getProperty("user.home") + "/.gladivs/config");
            File usrconf = new File(System.getProperty("user.home") + "/.gladivs/config/user.json");
            
            if(!configdir.exists())
            {
                configdir.mkdirs();
            }
            
            usrconf.createNewFile();
            
            JsonGenerator generator = factory.createGenerator(usrconf, JsonEncoding.UTF8);
            
            //Start list
            generator.writeStartArray();
            
            for(Values v:from.values())
            {
                generator.writeStartObject();
                generator.writeNumberField("parameterId", v.getParameterId());
                generator.writeStringField("parameterName", v.getParameterName());
                
                if(v.getParameterValueType().equals("int"))
                {
                    generator.writeNumberField("parameterValue", v.getIntValue());
                }
                else
                {
                    generator.writeStringField("parameterValue", v.getStringValue());
                }
                
                generator.writeStringField("parameterValueType", v.getParameterValueType());
                generator.writeStringField("parameterString", v.getParameterString());
                
                generator.writeEndObject();
            }
            
            generator.writeEndArray();
            
            generator.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Return an ArrayList with all default configuration parameters
     * @return 
     */
    public HashMap<String, Values> getDefaultValues()
    {
        return defaultValues;
    }
    
    /**
     * Return an ArrayList with all user configuration parameters if exists or defaultConfig
     * if user config parameter is not set.
     * @return 
     */
    public HashMap<String, Values> getUserValues()
    {
        return userValues;
    }
}
