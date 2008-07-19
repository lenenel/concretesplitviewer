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
 * Distance.java
 *
 * Created on 27 Июнь 2006 г., 19:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mytinski Leonid
 *
 * Класс описывающий дистанцию
 */
public class Distance {
    private String name;
    private int length;
    private int numberOfCP;
//    private int[] lengths;
    private Lap[] laps;
    private List<Group> groups;
    /** Creates a new instance of Distance 
     *
     * @param  name  название дистанции (обычно название группы)
     * @param  length  длина дистанции 
     * @param  numberOfCP  количество пунктов
     * @param  lengths  массив содержащий длины перегонов
     *
     */
    public Distance(String name, int length, int numberOfCP, int[] lengths) {
        this(name, length, numberOfCP, lengths, null);
    }
    public Distance(String name, int length, int numberOfCP, int[] lengths, int[] cPsNumbers){
        this.name = name;
        this.length = length;
//        this.lengths = lengths;
        this.numberOfCP = numberOfCP;
        laps = new Lap[numberOfCP];
        if((lengths!=null)&&(cPsNumbers!=null)&&(lengths.length==cPsNumbers.length)){
            laps[0] = new Lap(Lap.START_CONTROL_POINT, cPsNumbers[0], lengths[0]);
            for(int i = 1; i < lengths.length; i++){
                laps[i] = new Lap(cPsNumbers[i-1], cPsNumbers[i], lengths[i]);
            }
        } else if(lengths!=null){
                setLengthsOfDists(lengths);
        }
        groups = new ArrayList<Group>();
    }
    public Distance(String name, int length, int numberOfCP){
        this(name, length, numberOfCP, null, null);
    }
    /*
     * Метод возвращает название дистанции.
     */
    public String getName(){
        return name;
    }
     /*
     * Метод устанавливает название дистанции.
     */
    public void setName(String n){
        name = n;
    }
     /*
     * Метод возвращает длину дистанции.
     */
    public int getLength(){
        return length;
    }
     /*
     * Метод возвращает количество пунктов
     */
    public int getNumberOfCP(){
        return numberOfCP;
    }
     /*
      * Метод возвращает длину перегона на пункт n
      * Method return lenght of laps with number <code>n</code>. n=1 means distance from start to first control point
      * 
      * @return  lenght of distance from <code>n-1</code>th to <code>n</code>th control point. If distance has no lenghts of laps returned -1.
      *
      * @param  n  lenght of what lap should be returned (n=1 means distance from start to first control point)
      *
      */
    public int getLengthOfDist(int n){
        if(laps[n-1]==null) return -1;
        return laps[n-1].getLength();
    }
     /*
      * Метод устанавливает длину перегона на пункт n равной l.
      */
    public void setLengthOfDist(int n, int l){
        if(laps[n-1]==null) {
            laps[n-1] = new Lap(l);
        } else {
            laps[n-1].setLength(l);
        }
    }
    
    /** Method for set all lengths of laps.
     *
     *  @param  ls  array of lengths
     *
     *  @return  <code>true</code> if set lengths. <code>false</code> if length of array not equals with number of control points in distance
     */
    public boolean setLengthsOfDists(int[] ls){
        if((ls==null)||(ls.length != getNumberOfCP()))
            return false;
        else{
            for(int i=0; i < ls.length; i++)
                setLengthOfDist(i+1, ls[i]);
        }
        return true;
    }
    
    public void removeGroup(Group g){
        groups.remove(g);
    }
     /*
     * Метод добовляет группу к дистанции.
     */
    public void addGroup(Group g){
        if(!groups.contains(g))
            groups.add(g);
        else;
    }
     /*
     * Метод устанавливает набор групп для этой дистанции
     */
    public void setGroups(List<Group> grs){
        groups = new ArrayList<Group>(grs);
    }
     /*
     * Метод возвращает все группы принадлижащие этой дистанции.
     */
    public List<Group> getGroups(){
        return new ArrayList<Group>(groups);
    }
     /*
     * Метод сравнивает эту дистанцию с дистанцией d
     */
    public boolean equals(Distance d){
        return (getLength() == d.getLength()) && (getNumberOfCP() == d.getNumberOfCP());
    }
}
