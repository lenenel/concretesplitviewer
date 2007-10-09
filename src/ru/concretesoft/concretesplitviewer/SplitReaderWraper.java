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
    private Class[] readers = new Class[]{
        OCT2007Reader.class,
        OSVReader.class,
        SFReader.class
    };
    
    /** Creates a new instance of <code>SplitReaderWraper</code> for file <code>file</code>
     * 
     * @param file file that sould be tryed to open
     */
    public SplitReaderWraper(File file){
        splitReader = null;
        setFile(file);
    }
    /**
     * Creates a new instance of <code>SplitReaderWraper</code>
     */
    public SplitReaderWraper(){
        this(null);
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
        while((splitReader == null)&&(countTry < readers.length)){
            try{
                splitReader = (SplitReader) readers[countTry].getConstructor(File.class).newInstance(file);
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
