/*
 * AthleteListModel.java
 *
 * Created on 3 Июль 2006 г., 16:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * 
 * 
 * @author Мытинский Леонид
 * 
 * Класс описывающий модель данных для списка спортсменов.
 * Модель содержит объекты типа AthleteIcon.
 */
public class AthleteListModel implements ListModel,ListSelectionModel,ListSelectionListener{
        private Vector<ListDataListener> list;
        private Vector<AthleteIcon> athletes;
        private Vector<ListSelectionListener> listenerSelection;
        private Distance d;
        private int[] viewLaps;
        private JList groupsList;
        private int anchor;
        private int lead;
        private boolean[] selected;
        private FontMetrics fontMetrics;
        private Color[] colors = new Color[]{
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.PINK,
            Color.CYAN,
            Color.MAGENTA,
            Color.ORANGE
        };
        
        public AthleteListModel(FontMetrics fM){
            fontMetrics = fM;
            list = new Vector<ListDataListener>();
            listenerSelection = new Vector<ListSelectionListener>();
            d=null;
            selected = new boolean[0];
            
        }
        public int getSize() {
            if(athletes==null)return 0;
            return athletes.size();
        }
        /**
         * Метод получает набор спортсменов, формирует данные для модели
         * добовляет эти данные в модель и оповещает всех слушателей изменений модели
         *
         * @param  as  <code>Vector</code> of athletes, whose sould be in model
         */
        public void setAthletes(Vector<Athlete> as){
            
            if((as!=null)&&(as.size()>0)){
                athletes = new Vector<AthleteIcon>();
                selected = new boolean[as.size()];
                int i=0;
                d = as.get(0).getGroup().getDistance();
                Iterator<Athlete> itA = as.iterator();
                while(itA.hasNext()){
                    athletes.add(new AthleteIcon(itA.next(),fontMetrics));
                    Color col;
                    if(i<colors.length){
                        col = colors[i];
                    }
                    else{
                        Color tmp = colors[i % colors.length];
                        int red = tmp.getRed()-(int)(tmp.getRed()/3*Math.random());
                        int green = tmp.getGreen()-(int)(tmp.getGreen()/3*Math.random());
                        int blue = tmp.getBlue()-(int)(tmp.getBlue()/3*Math.random());
                        col = new Color(red,green,blue);
                    }
                    athletes.lastElement().setColor(col);
                    i++;
                }
                allLaps();
                
            }
            else{
                athletes = new Vector<AthleteIcon>();
                d = null;
                selected = null;
                viewLaps=null;
            }
            
            Iterator<ListDataListener> it = list.iterator();
            while(it.hasNext()){
                it.next().contentsChanged(new ListDataEvent(this,ListDataEvent.CONTENTS_CHANGED,0,athletes.size()));
            }
        }
        
        public Object getElementAt(int index) {
            return athletes.get(index);
        }

        public void addListDataListener(ListDataListener l) {
            list.add(l);
        }

        public void removeListDataListener(ListDataListener l) {
            list.remove(l);
        }
        /**
         * Метод возвращает всех спортсменов которые содержаться в моделе.
         */
        public Vector<Athlete> getAthletes(){
            if(athletes==null) return null;
            Vector<Athlete> at = new Vector<Athlete>();
            Iterator<AthleteIcon> it = athletes.iterator();
            while(it.hasNext()){
                at.add(it.next().getAthlete());
            }
            return at;
        
        
        }
        private void allLaps(){
            if(d==null){
                viewLaps = null;
            } else{
                viewLaps = new int[d.getNumberOfCP()];
                for(int i=0;i<d.getNumberOfCP();i++){
                    viewLaps[i] = i+1;
                }
            }
//            setViewSplits(viewLaps);
//            Iterator<ListDataListener> it = list.iterator();
//            while(it.hasNext())
//                it.next().contentsChanged(new ListDataEvent(this,ListDataEvent.CONTENTS_CHANGED,0,athletes.size()));
        }
         /** 
          * Restores all splits from distance
          *
          * @return  <code>true</code> if restoring succesfull else <code>false</code>. Not succesfull restoring if distance not set.
          */
        public boolean restoreAllSplits(){
            if(d==null)
                return false;
            else{
                setViewSplits(null);
                
//                Iterator<ListDataListener> it = list.iterator();
//                while(it.hasNext())
//                    it.next().contentsChanged(new ListDataEvent(this,ListDataEvent.CONTENTS_CHANGED,0,athletes.size()));
                return true;
            }
        }
         /** Gets array of viewing laps
          *
          *  @return  array of viewing laps or <code>null</code> if all splits not restored
          *
          *  @see  restoreAllSplits();
          */
        public int[] getViewingSplits(){
            boolean ok = true;
            if(viewLaps==null)
                ok = restoreAllSplits();
            else;
            return ok ? (int[])viewLaps.clone() : null ;
        }
        /** Removes splits for control point. If only one lap in model, then do nothing
         *
         *  @param  n  determin control point's number for lap that should be removed
         */
        public void removeSplitsForN(int n){
            int i = 0;
            
            if(viewLaps.length<=1)//If only one lap in view than exit
                return;
            else;
            
            //Find position of lap that should be removed
            //Найти положение перегона, который нужно удалить. Ну и конструкция =) 
            try{
                while(viewLaps[i] != n)i++;
            } catch(java.lang.ArrayIndexOutOfBoundsException e){ return ;}
            
            
            int[] splits = new int[viewLaps.length-1];
            for(int j=0;j<i;j++) splits[j] = viewLaps[j];
            for(int j=i;j<splits.length;j++) splits[j]=viewLaps[j+1];
            setViewSplits(splits);

        }
        /** Added viewing lap for control point
         *
         *  @param  n  contol point's number
         */
        public void addSplitsForN(int n){
            if(n>d.getNumberOfCP()) return;//If CP's number greter than number of control points
            for(int i=0;i<viewLaps.length;i++){
                if(n<viewLaps[i]){
                    int[] splOld = viewLaps;
                    viewLaps = new int[splOld.length+1];
                    for(int j=0;j<i;j++) viewLaps[j] = splOld[j];
                    viewLaps[i] = n;
                    for(int j=i+1;j<viewLaps.length;j++) viewLaps[j]=splOld[j-1];
                    setViewSplits(viewLaps);
//                    Iterator<ListDataListener> it = list.iterator();
//                    while(it.hasNext())
//                        it.next().contentsChanged(new ListDataEvent(this,ListDataEvent.CONTENTS_CHANGED,0,athletes.size()));
                    return;
                }else;
            }
            //If we not add lap yet than CP's number greter then the last selected CP's number
            int[] splOld = viewLaps;
            viewLaps = new int[splOld.length+1];
            for(int j=0;j<splOld.length;j++) viewLaps[j] = splOld[j];
            viewLaps[splOld.length] = n;
            setViewSplits(viewLaps);
        }
         /**
         * Метод устанавливает выбранные перегоны, и вызывает изменение отображаемой информации
         */
        public void setViewSplits(int[] spl){
            if(spl == null){
                allLaps();
            } else{
                viewLaps = (int[]) spl.clone();
            }
            if(athletes != null){
                Iterator<AthleteIcon> it = athletes.iterator();
                while(it.hasNext()){
                    AthleteIcon aI = it.next();
                    aI.setSplits(spl);
                }
                Vector<AthleteIcon> athletesNew = new Vector<AthleteIcon>();
                it = athletes.iterator();
                while(it.hasNext()){
                    if(athletesNew.size()==0)
                        athletesNew.add(it.next());
                    else{
                        int j=0;
                        AthleteIcon first = athletesNew.get(j);
                        AthleteIcon cur = it.next();
                        int size = athletesNew.size();
                        int curTime = cur.getTotalTime().getTimeInSeconds();
                        while(curTime>=first.getTotalTime().getTimeInSeconds()){
                            j++;
                            if(j==size) break;
                            first = athletesNew.get(j);
                        }
                        
                        athletesNew.insertElementAt(cur,j);
                    }
                }
                athletes = athletesNew;
                if(athletes.size()>0){
                    int timeBefore = athletes.get(0).getTotalTime().getTimeInSeconds();
                    athletes.get(0).setPosition(1);
                    int position=1;
                    for(int j=1;j<athletes.size();j++){
                        int curTime = athletes.get(j).getTotalTime().getTimeInSeconds();
                        if(curTime>timeBefore){

                            timeBefore = curTime;
                            position = j+1;
                        }


                        selected[j] = athletes.get(j).isSelected();
                        athletes.get(j).setPosition(position);
                    }
                }
                Iterator<ListDataListener> it2 = list.iterator();
                while(it2.hasNext()){
                    it2.next().contentsChanged(new ListDataEvent(this,ListDataEvent.CONTENTS_CHANGED,0,athletes.size()));
                }
            }
        }
        public Distance getDistance(){
            return d;
        }
        /**
         * Метод возвращает весь нобор данных модели
         */
        public Vector<AthleteIcon> getValues(){
            return athletes;
        }
        /**
         * Метод возвращает выбранные данные
         */
        public Object[] getSelectedValues(){
             Vector<AthleteIcon> value = new Vector<AthleteIcon>();
             if(athletes != null){
                 Iterator<AthleteIcon> it = athletes.iterator();
                 while(it.hasNext()){
                    AthleteIcon aI = it.next();
                    if(aI.isSelected()) value.add(aI);
                 }
                 
             }
             return value.toArray(new AthleteIcon[value.size()]);
        }

        public void setSelectionInterval(int index0, int index1) {
            anchor = index0;
            lead = index1;
            for(int i=0;i<selected.length;i++){
              if((i>=index0)&&(i<=index1)){
                    selected[i] = !selected[i];//invert status
                    athletes.get(i).setSelected(selected[i]);//set new status
              } else;
                   
            }
            Iterator<ListSelectionListener> it = listenerSelection.iterator();
            while(it.hasNext()){
                it.next().valueChanged(new ListSelectionEvent(this,0,selected.length-1,false));
            }
        }

        public void addSelectionInterval(int index0, int index1) {
            setSelectionInterval(index0, index1);
            
        }

        public void removeSelectionInterval(int index0, int index1) {
            setSelectionInterval(index0, index1);
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
            if(selected==null){
                
            }else{
                for(int i=0;i<selected.length;i++){
                    selected[i]=false;
                    athletes.get(i).setSelected(false);
                }
                Iterator<ListSelectionListener> it = listenerSelection.iterator();
                while(it.hasNext()){
                    it.next().valueChanged(new ListSelectionEvent(this,0,selected.length-1,false));
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
            listenerSelection.add(x);
        }

        public void removeListSelectionListener(ListSelectionListener x) {
            listenerSelection.remove(x);
        }

    public void valueChanged(ListSelectionEvent e) {
        setAthletes(null);
        Object[] g1 =  groupsList.getSelectedValues();
        Vector<Athlete> all = new Vector<Athlete>();
        for(int i=0;i<g1.length;i++){
            Iterator<Athlete> it = ((Group)g1[i]).getAthletes().iterator();
            while(it.hasNext()){
                
                if(all.size()==0)
                    all.add(it.next());
                else{
                    int j=0;
                    Athlete first = all.get(j);
                    Athlete cur = it.next();
                    int size = all.size();
                    int curTime = cur.getTotalTime().getTimeInSeconds();
                    while(curTime>first.getTotalTime().getTimeInSeconds()){
                        j++;
                        if(j==size) break;
                        first = all.get(j);
                    }
                    all.insertElementAt(cur,j);
                }
            }
                
        }
        clearSelection();
        setAthletes(all);
        setViewSplits(null);
    }

    public JList getGroupsList() {
        return groupsList;
    }

    public void setGroupsList(JList groupsList) {
        this.groupsList = groupsList;
    }
    
    /** Method for getting list atheletes for one lap
     *
     *
     *
     */
     public Collection<AthleteIcon> getOneLap(int number){
         Vector<AthleteIcon> athletes = new Vector<AthleteIcon>();
         Iterator<AthleteIcon> iter = this.athletes.iterator();
         while(iter.hasNext()){
            AthleteIcon aI = iter.next();
            int size = athletes.size();
            if(size==0){
                athletes.add(aI);
            } else {
                int i = findNearEl(athletes, aI.getAthlete(), number, 0, size-1);
                athletes.insertElementAt(aI, i);
            }
         }
         return athletes;
     }
     private int findNearEl(Vector<AthleteIcon> as, Athlete a, int number, int min, int max){
         int size = max - min;
         int d = size/2;
         if(size==0){
             Athlete atCur = as.get(min).getAthlete();
             int c = atCur.getLap(number).compareTo(a.getLap(number));
             if(c<0)
                 return min+1;
             else
                 return min;
             
         } else if(size==1){
            Athlete atCur = as.get(min).getAthlete();
            int c = atCur.getLap(number).compareTo(a.getLap(number));
            if(c<0){
                atCur = as.get(max).getAthlete();
                c = atCur.getLap(number).compareTo(a.getLap(number));
                if(c < 0)
                    return max+1;
                else
                    return max;
            } else{
                return min;
            }
         }else{
             Athlete atCur = as.get(min+d).getAthlete();
             int c = atCur.getLap(number).compareTo(a.getLap(number));
             if(c<0){
                 return findNearEl(as, a, number, min+d, max);
             } else if(c>0){
                 return findNearEl(as, a, number, min, min+d);
             } else{
                 return min+d;
             }
         }
     }
}
