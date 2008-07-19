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
 * GroupListModel.java
 *
 * Created on 23 Январь 2007 г., 21:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Mytinski Leonid
 */
public class GroupListModel implements ListModel{

    Collection<ListDataListener> list = new LinkedList<ListDataListener>();

    List<Group> groups;
    public int getSize() {
        if(groups==null) return 0;
        return groups.size();
    }
    public List<Group> getGroups(){
        if(groups != null)
            return new ArrayList<Group>(groups);
        else
            return null;
    }
    public void setGroups(List<Group> gr){
        groups = gr;
        Iterator<ListDataListener> it = list.iterator();
        while(it.hasNext()){
            it.next().contentsChanged(new ListDataEvent(this,ListDataEvent.CONTENTS_CHANGED,0,groups.size()));
        }
    }
    public Object getElementAt(int index) {
        return groups.get(index);
    }

    public void addListDataListener(ListDataListener l) {
        list.add(l);
    }

    public void removeListDataListener(ListDataListener l) {
        list.remove(l);
    }

    
    
}
