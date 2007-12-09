/*
 * ConcreteSplitViewer program for analazing splits.
 * Copyright (C) 2006-2007 Mytinski Leonid (Leonid.Mytinski@gmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */ 

/*
 * NotRightFormat.java
 * 
 * Created on 21.09.2007, 20:03:05
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.io.File;

/**
 *
 * @author Mytinski Leonid
 * 
 * Exception when split reader trys to open file that have not right format
 */
public class NotRightFormatException extends Exception{
    /**Create new exception
     * 
     * @param file file with not right format
     * @param format name of format
     * @param message why not right
     */
    public NotRightFormatException(File file, String format, String message){
        super("File " + file.getPath() + 
                " is not " + format + 
                " format file because of " + message);
    }
    
}
