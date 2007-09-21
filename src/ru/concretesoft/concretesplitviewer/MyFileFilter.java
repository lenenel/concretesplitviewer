/*
 * MyFileFilter.java
 *
 * Created on 27 Июнь 2006 г., 23:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Mytinski Leonid
 *
 * Класс фильтра файлов
 */
public class MyFileFilter extends FileFilter {
    private Vector<String> ext;
    /** Creates a new instance of MyFileFilter */
    public MyFileFilter(Vector<String> exts) {
        ext=exts;
    }

    public boolean accept(File f) {
        String name = f.getName();
        boolean b=true;
        if(f.isFile()){
            b = false;
            String[] parts = name.split("\\.");
            
            Iterator it = ext.iterator();
            while((!b)&&(it.hasNext())){
                b = parts[parts.length-1].equals(it.next());
            }
        }
        return b;
    }

    public String getDescription() {
        String val="";
        Iterator it = ext.iterator();
        while(it.hasNext()) val+=it.next()+";";
        val=val.substring(0,val.length()-1);
        return val;
    }
    
}
