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
 * SplitReaderWraper.java
 * 
 * Created on 21.09.2007, 20:49:45
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mytinski Leonid
 * 
 * Class that would choose usable <code>SplitViewer</code> for file
 */
public class SplitReaderWraper {
    private File file;
    private SplitReader splitReader;
    private List<Class> readers;
    private URLClassLoader classLoader;
    
    /** Creates a new instance of <code>SplitReaderWraper</code> for file <code>file</code>
     * 
     * @param file file that sould be tryed to open
     */
    public SplitReaderWraper(File file, String[] readersNames, URLClassLoader classLoader){
        readers = new ArrayList<Class>();
        setFile(file);
        this.classLoader = classLoader;
        for(String reader: readersNames){
            try{
                Class readerClass = this.getClass().getClassLoader().loadClass(reader);
                readers.add(readerClass);
            }catch(ClassNotFoundException ex){
                Logger.getLogger(SplitReaderWraper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Creates a new instance of <code>SplitReaderWraper</code> with given class names of readers
     */
    public SplitReaderWraper(String[] readersNames, URLClassLoader classLoader){
        this(null, readersNames, classLoader);
    }
    
    /** Trys to read splits from file in all known formats
     * 
     * @return <code>SplitReader</code> object if trying to read is ok or <code>null</code> if readers are can't read this file
     * @throws java.io.IOException 
     */
    @SuppressWarnings("unchecked")
    public SplitReader createSplitReader() throws IOException{
        if(file==null)
            return null;
        
        int countTry = 0;
        while((splitReader == null)&&(countTry < readers.size())){
            try{
                splitReader = (SplitReader) readers.get(countTry).getConstructor(File.class).newInstance(file);
             }
            catch (InstantiationException ex) {
                Logger.getLogger(SplitReaderWraper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SplitReaderWraper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(SplitReaderWraper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                if(ex.getCause().getClass().equals(NotRightFormatException.class)){
                    System.out.println(ex.getCause().getMessage());
                }else{
                    Logger.getLogger(SplitReaderWraper.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(SplitReaderWraper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(SplitReaderWraper.class.getName()).log(Level.SEVERE, null, ex);
            } 
            countTry++;
        }
        return splitReader;
    }

    /**
     * 
     * @return file to read or <code>null</code> if file not set
     */
    public File getFile() {
        return file;
    }

    /**Sets file to read
     * 
     * @param file file to read
     */
    public void setFile(File file) {
        this.file = file;
        splitReader = null;
    }
}
