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
 * GroupSelectionModel.java
 *
 * Created on 1 Июль 2006 г., 13:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.util.Iterator;
import java.util.Vector;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Mytinski Leonid
 *
 * Класс определяющий модель выбора групп в списке.
 * Если происходит попытка добавления группы к уже выбранным, то происходит проверка совпадения дистанций у выбранных групп и у новой.
 * Выбор осуществляется только в случае совпадения дистанций.
 */
public class GroupSelectionModel implements ListSelectionModel, GroupModelListener{
    Vector<Group> groups;
    GroupListModel groupModel;
    boolean [] selected;
    Distance d;
    int anchor,lead;
    Vector<ListSelectionListener> list;
    /** Creates a new instance of GroupSelectionModel
     * 
     * gr - Набор всех групп
     */
    public GroupSelectionModel(GroupListModel gLM) {
        if(gLM==null) groups=new Vector<Group>();
        else groups =gLM.getGroups();
        groupModel = gLM;
        if(groups==null){
            selected = new boolean [0];
        }else{
            selected = new boolean [groups.size()];
        }
        list = new Vector<ListSelectionListener>();
    }
    
    // Реализация всех необходимых методов
         
    public void setSelectionInterval(int index0, int index1) {
        anchor = index0;
        lead = index1;
        for(int i=0;i<selected.length;i++){
            if(i==index0){
                selected[i] = true;
                d = groups.get(i).getDistance();
            }
            else
                if((i>index0)&&(i<=index1)){
                    if(groups.get(i).getDistance().equals(d)){ selected[i]=true; lead = i;}
                    else selected[i]=false;
                }
                else selected[i] = false;
        }
        Iterator<ListSelectionListener> it = list.iterator();
        while(it.hasNext()){
            it.next().valueChanged(new ListSelectionEvent(this,0,selected.length-1,false));
        }
    }

    public void addSelectionInterval(int index0, int index1) {
        anchor = index0;
        lead = index1;
        for(int i=index0;i<=index1;i++){
           
           
            if(groups.get(i).getDistance().equals(d)) selected[i]=true;
            else selected[i]=false;
          
        }
        Iterator<ListSelectionListener> it = list.iterator();
        while(it.hasNext()){
            it.next().valueChanged(new ListSelectionEvent(groups,0,selected.length-1,false));
        }
    }

    public void removeSelectionInterval(int index0, int index1) {
       anchor = index0;
        lead = index1;
        for(int i=index0;i<=index1;i++){
            selected[i] = false;
        }
        Iterator<ListSelectionListener> it = list.iterator();
        while(it.hasNext()){
            it.next().valueChanged(new ListSelectionEvent(groups,0,selected.length-1,false));
        }
    }
    public Distance getDistance(){
        return d;
    }
    public int getMinSelectionIndex() {
        for(int i=0;i<selected.length;i++){
            if(selected[i]) return i;
        }
        return -1;
    }

    public int getMaxSelectionIndex() {
        for(int i=selected.length-1;i>=0;i--){
            if(selected[i]) return i;
        }
        return -1;
    }
    public boolean isSelectedIndex(int index) {
        return selected[index];
    }

    public int getAnchorSelectionIndex() {
        return anchor;
    }

    public void setAnchorSelectionIndex(int index) {
        anchor = index;
    }

    public int getLeadSelectionIndex() {
        return lead;
    }

    public void setLeadSelectionIndex(int index) {
        lead = index;
    }

    public void clearSelection() {
        if(groups==null){
            selected = null;
        }else{
            for(int i=0;i<selected.length;i++){
                selected[i]=false;
            }
            Iterator<ListSelectionListener> it = list.iterator();
            while(it.hasNext()){
                it.next().valueChanged(new ListSelectionEvent(groups,0,selected.length-1,false));
            }
        }
    }

    public boolean isSelectionEmpty() {
        for(int i=0;i<selected.length;i++){
            if(selected[i]) return false;
        }
        return true;
    }

    public void insertIndexInterval(int index, int length, boolean before) {
        int step;
        if(before) step=-1;
        else step=1;
        
    }

    public void removeIndexInterval(int index0, int index1) {
    }

    public void setValueIsAdjusting(boolean valueIsAdjusting) {
    }

    public boolean getValueIsAdjusting() {
        return false;
    }

    public void setSelectionMode(int selectionMode) {
    }

    public int getSelectionMode() {
        return this.MULTIPLE_INTERVAL_SELECTION;
    }

    public void addListSelectionListener(ListSelectionListener x) {
        list.add(x);
    }

    public void removeListSelectionListener(ListSelectionListener x) {
        list.remove(x);
    }
    public void intervalAdded(ListDataEvent e) {
        contentsChanged(e);
    }

    public void intervalRemoved(ListDataEvent e) {
        contentsChanged(e);
    }

    public void contentsChanged(ListDataEvent e) {
        groups = groupModel.getGroups();
        selected = new boolean[groups.size()];
        Iterator<ListSelectionListener> it = list.iterator();
        while(it.hasNext()){
            it.next().valueChanged(new ListSelectionEvent(this,0,selected.length-1,false));
        }
    }
    
}
