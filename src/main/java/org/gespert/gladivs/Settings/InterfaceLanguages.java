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
package org.gespert.gladivs.Settings;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class InterfaceLanguages {
    
    private LanguageObject localeLanguage;
    
    public Collection<LanguageObject> getAvailableInterfaceLanguagesList()
    {
        return Arrays.asList(
            new LanguageObject("English", "English", "en"),
            new LanguageObject("Spanish", "Español", "es"),    
            new LanguageObject("Catalan", "Català", "ca"),
            new LanguageObject("French", "Français", "fr")
        );
    }
    
    protected void setLocaleLanguage(LanguageObject lo)
    {
        localeLanguage = lo;
    }
    
    public LanguageObject getLocaleLanguage()
    {
        return localeLanguage;
    }
    
    public class LanguageObject{
        
        private String englishName;
        private String nativeName;
        private String langCode;

        public LanguageObject(String englishName, String nativeName, String langCode) {
            this.englishName = englishName;
            this.nativeName = nativeName;
            this.langCode = langCode;
            
            if(Locale.getDefault().getLanguage().equals(langCode))
            {
                InterfaceLanguages.this.setLocaleLanguage(this);
            }
        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public String getNativeName() {
            return nativeName;
        }

        public void setNativeName(String nativeName) {
            this.nativeName = nativeName;
        }

        public String getLangCode() {
            return langCode;
        }

        public void setLangCode(String langCode) {
            this.langCode = langCode;
        }
        
        @Override
        public String toString()
        {
            return nativeName;
        }
    }
}
