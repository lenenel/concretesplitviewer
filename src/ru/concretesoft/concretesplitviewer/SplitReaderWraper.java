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

/**
 *
 * @author Mytinski Leonid
 * 
 * Class that would choose usable <code>SplitViewer</code> for file
 */
public class SplitReaderWraper {
    private File file;
    private SplitReader splitReader;
    
    
    public SplitReaderWraper(File file){
        splitReader = null;
        setFile(file);
    }
    public SplitReaderWraper(){
        this(null);
    }
    
    public SplitReader createSplitReader() throws IOException{
        if (splitReader == null){
            try{
                splitReader = new OSVReader(file);
            }
            catch(NotRightFormatException e){
                System.out.println(e.getMessage());
            }
        }
        if (splitReader == null){
            try{
                splitReader = new SFReader(file);
            }
            catch(NotRightFormatException e){
                System.out.println(e.getMessage());
            }
        }
        return splitReader;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        splitReader = null;
    }
}
