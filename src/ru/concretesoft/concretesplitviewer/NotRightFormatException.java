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
