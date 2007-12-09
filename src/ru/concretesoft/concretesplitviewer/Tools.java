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
 * Tools.java
 *
 * Created on 2 Июнь 2006 г., 23:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

/**
 *
 * @author Mytinski Leonid
 * 
 * Class content different method for analazing splits
 * Класс содержит различные методы для анализа сплитов
 */
public class Tools {
    private static final int TOTAL_DIST = 10000;
    
    
    private Tools() { // (Pattern ?"Singleton"?) Запрещение создания экземпляра класса 
    }
    
    /** Method for finding splits of "ideal" athlete, that showed detrmin time (first, second, third, ...)
     * Метод возвращает сплиты виртуального спортсмена.
     *
     * @param  all  <code>Collection</code> of <code>Group</code>, from that will be finding "ideal" athlete (набор груп для вычисления виртуального спортсмена)
     * @param  any  what time will be show on each lap (место занимаемое на перегоне)
     *
     * @return  array of <code>Time</code> determin splits of "ideal" athlete or <code>null</code> if <code>Collection</code> presents groups for different distances
     */
    public static Time[] getAnyBest(Collection<Group> all,int any){
         Time[] best,secondBest;
         int number;
         secondBest = null;
         if(all.size()>0) {
             
        
             
             Iterator<Group> it = all.iterator();
             TreeSet<Time>[] tS = null; // Content - all times of all athlets, who have to be in finding, on each lap
             Distance d = null;
             // Filling of tS
             while(it.hasNext()){
                 Group g = it.next();
                 if(d == null)
                     d = g.getDistance();
                 else if(!d.equals(g.getDistance()))
                     return null;
                 else;
                 
                 if(tS == null){ // if Set have not begined filling yet
                     number = d.getNumberOfCP();
                     tS = new TreeSet[number];
                     secondBest = new Time[number];
                 }else;

                 for(int i=0;i<tS.length;i++){
                     HashSet<Time> hS = g.getTimesOnLap(i+1);
                     if(tS[i]==null) tS[i] = new TreeSet<Time>();
                     tS[i].addAll(hS);
                 }

                 
             }
             
             // If on first lap one or more times then do finding else set return value to null
             if(tS[0].size()>=1){
                 for(int i=0;i<tS.length;i++){
                     if(tS[i].size()>=any){
                          if((tS[i].first().getTimeInSeconds()!=0)||(tS[i].size()==any))
                            secondBest[i] = (Time)tS[i].toArray()[any-1];
                         else if(tS[i].size()>any)
                             secondBest[i] = (Time)tS[i].toArray()[any];
                         else 
                             secondBest[i] = (Time)tS[i].toArray()[tS[i].size()-1];
                     }
                     else if(tS[0].size()>=1){
                         secondBest[i] = tS[i].last();
                     }
                     else
                         secondBest[i] = new Time(0,2);
                 }
             } else secondBest = null;
         }

        return secondBest;
    }
    
    /** Method for calculating mean speed on lap from <code>n-1</code> to <code>n</code>
     *
     * @param  d  <code>Distance</code> on that was run athlete
     * @param  a  <code>Athlete</code> whose speed will be calculating
     * @param  n  control point's number 
     * 
     * @return  <code>Time</code> that determin mean speed in format time/km 
     */
    public static Time getMeanSpeedOnLap(Distance d, Athlete a, int n){
        int lengOfDist = d.getLengthOfDist(n);
        double lengKM = lengOfDist/1000.0;
        Time time = a.getLap(n);
        int timeS = time.getTimeInSeconds();
        int speedS = (int)(timeS/lengKM);
        Time speed = new Time(0,2);
        speed.setTimeInSeconds(speedS);
        return speed;
    }
    
    /** Method for calculating "mean" speed from set of laps with equlas weights
     *  meanSpeed = mean ariphmetic of mean speed on each lap
     *  
     * @param  d  <code>Distance</code> on that was run athlete
     * @param  a  <code>Athlete</code> whose speed will be calculating
     * @param  laps  array of lap's numbers from that would be calculating "mean" speed. If equals <code>null</code> then used all laps of distance
     *
     *
     * @return  <code>Time</code> that determin "mean" speed in format time/km
     */
    public static Time getMeanSpeed(Distance d, Athlete a, int[] laps){
        boolean allLaps = (laps == null);
        int numberOfLaps = allLaps ? d.getNumberOfCP() : laps.length;
        Time speed = new Time(0,2);
        for (int i = 0; i < numberOfLaps ; i++){
            speed.addTime(getMeanSpeedOnLap(d, a, (allLaps ? (i+1) : laps[i]) ));
        }
        speed.setTimeInSeconds(speed.getTimeInSeconds()/numberOfLaps);
        return speed;
    }
    
    /** Method for calculating "mean" speed on all laps of distance
     *
     * @param  d  <code>Distance</code> on that was run athlete
     * @param  a  <code>Athlete</code> whose speed will be calculating
     *
     * @return  <code>Time</code> that determin "mean" speed in format time/km
     *
     * @see  #getMeanSpeed(Distance, Athlete, int[])
     */
    public static Time getMeanSpeed(Distance d, Athlete a){
        return getMeanSpeed(d, a, null);
    }
    
    /** Method for calculating "mean" speed more complex way than <code>getMeanSpeed</code>
     *  First calculating "mean" speed with help <code>getMeanSpeed</code>. 
     *  Then remove from array of laps element with maximum difference with mean value.
     *  And call yourself with new array
     *
     * @param  d  <code>Distance</code> on that was run athlete
     * @param  a  <code>Athlete</code> whose speed will be calculating
     * @param  laps  array of lap's numbers from that would be calculating "mean" speed. If equals <code>null</code> then used all laps of distance
     * @param  k  parameter that determin relative maximum of difference laps with mean ( value from <code>0</code> to <code>1</code> )
     *
     * @return <code>Time</code> that determin "mean" speed in format time/km
     */
    public static Time getComplexMeanSpeed(Distance d, Athlete a, int[] laps, double k){
        Time speedSimple = getMeanSpeed(d, a, laps);
        int j=-1;
        int max=0;
        
        boolean allLaps = (laps == null);
        int numberOfLaps = allLaps ? d.getNumberOfCP() : laps.length;
        
        for(int i=0; i < numberOfLaps; i++){
            int speedS = speedSimple.getTimeInSeconds();
            int speed = getMeanSpeedOnLap(d, a, (allLaps ? i+1: laps[i]) ).getTimeInSeconds();
//            System.out.println(speed+"  "+speedS);
            if(Math.abs(speed-speedS)/(double)speedS >= k){
                if(max < Math.abs(speed-speedS)){
                    max = Math.abs(speed-speedS);
                    j=i;
                }else;
                
            }else;
        }
        
        if(j<0) return speedSimple;
        else{
            int[] lapsN = new int[numberOfLaps-1];
            for(int i=0; i < numberOfLaps; i++){
                if(i<j){
                    lapsN[i] = allLaps ? i+1 : laps[i];
                }
                else if(i>j){
                    lapsN[i-1] = allLaps ? i+1 : laps[i];
                }
                else;
            }
            return getComplexMeanSpeed(d,a,lapsN,k);
        }
    }
    /** Method for calculating "mean" speed from all laps of distance
     *
     * @param  d  <code>Distance</code> on that was run athlete
     * @param  a  <code>Athlete</code> whose speed will be calculating
     * @param  k  parameter that determin relative maximum of difference laps with mean ( value from <code>0</code> to <code>1</code> )
     *
     * @return <code>Time</code> that determin "mean" speed in format time/km
     *
     * @see  #getComplexMeanSpeed(Distance, Athlete, int[], double)
     */
    public static Time getComplexMeanSpeed(Distance d, Athlete a, double k){
        return getComplexMeanSpeed(d, a, null, k);
    }
    
    /** Method for calculating "mean" speed more complex way than <code>getMeanSpeed</code>
     *  First calculating "mean" speed with help <code>getMeanSpeed</code>.
     *  Then remove from array of laps elementes with difference beetwen mean value more than k*(mean value).
     *  And return result of call <code>getMeanSpeed</code> with new array.
     *
     * @param  d  <code>Distance</code> on that was run athlete
     * @param  a  <code>Athlete</code> whose speed will be calculating
     * @param  laps  array of lap's numbers from that would be calculating "mean" speed
     * @param  k  parameter that determin relative maximum of difference laps with mean ( value from <code>0</code> to <code>1</code> )
     *
     * @return <code>Time</code> that determin "mean" speed in format time/km
     */
    public static Time getComplexMeanSpeed_First(Distance d, Athlete a, int[] laps, double k){
        Time speedSimple = getMeanSpeed(d, a, laps);
        int j=0;
        for(int i=0; i < laps.length; i++){
            int speedS = speedSimple.getTimeInSeconds();
            int speed = getMeanSpeedOnLap(d, a, laps[i]).getTimeInSeconds();
            if(Math.abs(speed-speedS)/(double)speedS < k){
                j++;
//                System.out.println(speed+"  "+speedS);
            }
            else;
        }
        int[] lapsN = new int[j];
        j=0;
        for(int i=0; i < laps.length; i++){
            int speedS = speedSimple.getTimeInSeconds();
            int speed = getMeanSpeedOnLap(d, a, laps[i]).getTimeInSeconds();
            if(Math.abs(speed-speedS)/(double)speedS < k){
                lapsN[j] = laps[i];
                j++;
            }
            else;
        }
        return getMeanSpeed(d, a, lapsN);
    }
    /** Method calculating (approximately) length of each lap from full length of distance and second best time on each lap
     *
     * @param  grV  Set of group from that will be calculating second best time. Groups in set should be with one distance.
     * 
     * @return  array of length of each lap in meters or <code>null</code> if <code>grV</code> presents groups for different distances
     */
    public static int[] calculatLengthsOfLaps(Vector<Group> grV){
        Time[] secBest = getAnyBest(grV,2);
        if(secBest!=null){
            Distance d = grV.firstElement().getDistance();
            int totTime=0;
            int[] val = new int[secBest.length];
            for(int i=0;i<secBest.length;i++){
                totTime+=secBest[i].getTimeInSeconds();
            }
            for(int i=0;i<secBest.length;i++){
                val[i] = (int)(((double)secBest[i].getTimeInSeconds() / totTime)*TOTAL_DIST);
            }
            return val;
        }
        else
            return null;
    }
    /** Calculate total length of distance consist of given laps
     *
     * @param  d  distance for calculating
     * @param  laps  set of laps for calculating
     *
     * @return  length of distance consist of given laps or <code>-1<code> if length of array <code>laps</code> more than number of control points in distance
     */
    public static int calculateTotalLength(Distance d, int[] laps){
        if(laps==null){
           return TOTAL_DIST;
        }else if(laps.length > d.getNumberOfCP()){
            return -1;
        }else{
           int totL = 0; 
           for(int i=0;i<laps.length; i++){
               totL += d.getLengthOfDist(laps[i]);
           }
           return totL;
       }
    }
    public static int calculateTotalLength(Distance d){
        return calculateTotalLength(d, null);
    }
}