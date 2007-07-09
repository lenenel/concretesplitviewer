/*
 * GroupListModel.java
 *
 * Created on 23 Январь 2007 г., 21:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.util.Iterator;
import java.util.Vector;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author lene
 */
public class GroupListModel implements ListModel{

    Vector<ListDataListener> list = new Vector<ListDataListener>();

    Vector<Group> groups;
    public int getSize() {
        if(groups==null) return 0;
        return groups.size();
    }
    public Vector<Group> getGroups(){
        return (Vector<Group>)groups;
    }
    public void setGroups(Vector<Group> gr){
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
