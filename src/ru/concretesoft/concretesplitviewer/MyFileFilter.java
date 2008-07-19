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
 * MyFileFilter.java
 *
 * Created on 27 Июнь 2006 г., 23:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Mytinski Leonid
 *
 * Класс фильтра файлов
 */
public class MyFileFilter extends FileFilter {
    private Collection<String> ext;
    /** Creates a new instance of MyFileFilter */
    public MyFileFilter(Collection<String> exts) {
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
