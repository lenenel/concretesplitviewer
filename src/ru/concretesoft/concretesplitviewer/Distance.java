/*
 * Distance.java
 *
 * Created on 27 ���� 2006 �., 19:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.util.Vector;

/**
 *
 * @author ��������� ������
 *
 * ����� ����������� ���������
 */
public class Distance {
    private String name;
    private int length;
    private int numberOfCP;
    private int[] lengths;
    private Vector<Group> groups;
    /** Creates a new instance of Distance 
     *
     * name - �������� ��������� (������ �������� ������)
     * length - ����� ��������� 
     * numberOfCP - ���������� �������
     * lengths - ������ ���������� ����� ���������
     *
     */
    public Distance(String name, int length, int numberOfCP, int[] lengths) {
        this.name = name;
        this.length = length;
        this.lengths = lengths;
        this.numberOfCP = numberOfCP;
        groups = new Vector<Group>();
    }
    public Distance(String name, int length, int numberOfCP){
        this(name, length, numberOfCP, null);
    }
    /*
     * ����� ���������� �������� ���������.
     */
    public String getName(){
        return name;
    }
     /*
     * ����� ������������� �������� ���������.
     */
    public void setName(String n){
        name = n;
    }
     /*
     * ����� ���������� ����� ���������.
     */
    public int getLength(){
        return length;
    }
     /*
     * ����� ���������� ���������� �������
     */
    public int getNumberOfCP(){
        return numberOfCP;
    }
     /*
      * ����� ���������� ����� �������� �� ����� n
      * Method return lenght of laps with number <code>n</code>. n=1 means distance from start to first control point
      * 
      * @return  lenght of distance from <code>n-1</code>th to <code>n</code>th control point. If distance has no lenghts of laps returned -1.
      *
      * @param  n  lenght of what lap should be returned (n=1 means distance from start to first control point)
      *
      */
    public int getLengthOfDist(int n){
        if(lengths==null) return -1;
        return lengths[n-1];
    }
     /*
      * ����� ������������� ����� �������� �� ����� n ������ l.
      */
    public void setLengthOfDist(int n, int l){
        if(lengths==null) lengths = new int[getNumberOfCP()+1];
        lengths[n-1] = l;
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
     * ����� ��������� ������ � ���������.
     */
    public void addGroup(Group g){
        if(!groups.contains(g))
            groups.add(g);
        else;
    }
     /*
     * ����� ������������� ����� ����� ��� ���� ���������
     */
    public void setGroups(Vector<Group> grs){
        groups = (Vector<Group>)grs.clone();
    }
     /*
     * ����� ���������� ��� ������ ������������� ���� ���������.
     */
    public Vector<Group> getGroups(){
        return (Vector<Group>) groups.clone();
    }
     /*
     * ����� ���������� ��� ��������� � ���������� d
     */
    public boolean equals(Distance d){
        return (getLength() == d.getLength()) && (getNumberOfCP() == d.getNumberOfCP());
    }
}
