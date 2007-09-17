/*
 * Distance.java
 *
 * Created on 27 Июнь 2006 г., 19:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.util.Vector;

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
    private Vector<Group> groups;
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
        groups = new Vector<Group>();
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
    @SuppressWarnings("unchecked")
    public void setGroups(Vector<Group> grs){
        groups = (Vector<Group>)grs.clone();
    }
     /*
     * Метод возвращает все группы принадлижащие этой дистанции.
     */
    @SuppressWarnings("unchecked")
    public Vector<Group> getGroups(){
        return (Vector<Group>) groups.clone();
    }
     /*
     * Метод сравнивает эту дистанцию с дистанцией d
     */
    public boolean equals(Distance d){
        return (getLength() == d.getLength()) && (getNumberOfCP() == d.getNumberOfCP());
    }
}
