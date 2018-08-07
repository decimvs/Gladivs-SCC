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

package org.gespert.gladivs.VersionChecker;

/**
 *
 * @author Guillermo Espert Carrasquer <gespert at yahoo dot es>
 */
public class VersionData {
    
    private String latest_version;
    private String download_link;
    private Integer major_version;
    private Integer minor_version;
    private Integer revision_version;
    
    //###  SETTERS ################################################
    
    public void setLatest_version(String lt){ latest_version = lt; }
    public void setDownload_link(String dl){ download_link = dl; }
    public void setMajor_version(Integer major_version) { this.major_version = major_version; }
    public void setMinor_version(Integer minor_version) { this.minor_version = minor_version; }
    public void setRevision_version(Integer revision_version) { this.revision_version = revision_version; }
    
    //###  GETTERS ################################################
    
    public String getLatest_version(){ return latest_version; }
    public String getDownload_link(){ return download_link; }
    public Integer getMajor_version() { return major_version; }
    public Integer getMinor_version() { return minor_version; }
    public Integer getRevision_version() { return revision_version; }
}
