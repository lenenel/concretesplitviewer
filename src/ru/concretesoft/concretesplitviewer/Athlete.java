/*
 * Athlete.java
 *
 * Created on 27 ���� 2006 �., 14:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

/**
 *
 * @author ��������� ������
 * 
 * Object of model which content is infarmation of Family name, First name, year of birthday, group in which athlete runs and splits on all control points
 * ������ ������ ���������� � ����������. �������, ���, ��� ��������, ������ � ������� �� ���� �������.
 *  
 */
public class Athlete {
    private String familyName, name;
    private Time[] splits;
    private int yearOfBirth;
    private Group group;
    private boolean dSQ = false;
    
    /**
     * Creates a new instance of Athlete 
     *  ������ ����� ��������� ������� Athlete
     * 
     * 
     * @param fN  Family name (�������)
     * @param n  First name (���)
     * @param s  Splits (�������)
     * @param g  Group (������)
     * @param yOfB  Year of birthday (��� ��������)
     */
    public Athlete(String fN,String n,Time[] s,Group g, int yOfB) {
        familyName = fN;
        name = n;
        splits = s;
        yearOfBirth = yOfB;
        group = g;
        group.addAthlete(this);
                
    }
    
    /**
     * Creates a new instance of Athlete with year of birthday equal 2000
     *  ������ ����� ��������� ������� Athlete � ����� �������� ���������� �� ��������� ������ 2000
     * 
     * 
     * @param fN  Family name (�������)
     * @param n  First name (���)
     * @param s  Splits (�������)
     * @param g  Group (������)
     */
    public Athlete(String fN, String n, Time[] s,Group g){
        this(fN,n,s,g,2000);
    }
    
    /** Method returns time from control point <code>n-1</code> to control point <code>n</code>
     * ����� ���������� ����� �� ������ <code>n-1</code> �� ������ <code>n</code>
     * 
     * @param  n  control point's number (����� ������������ ������ �� �������� ������������ �����)
     *
     * @return  Time from control point <code>n-1</code> to control point <code>n</code>
     *          (����� �� ������ <code>n-1</code> �� ������ <code>n</code>)
     */
    public Time getLap(int n){
        return splits[n-1];
    }
    
    /** Method returns number of control points
     *  ����� ���������� ���������� �������
     *  
     * @return  number of control points (���������� �������, ������� �������� ���������)
     */
    public int getNumberOfLaps(){
        return splits.length;
    }
    
    /** Method for calculting full time that athlete spend on all laps 
     * ����� ���������� ������ ����� ���������� �� ����������� ���� ���������
     * 
     * @return  full time that athlete spend on all laps (������ ����� ���������� �� ����������� ���� ���������)
     */
    public Time getTotalTime(){
        Time t = new Time(0,3);
        for(int i=0;i<splits.length;i++){
            t.addTime(splits[i]);
        }
        return t;
    }
    /** Method returns athlete's year of birth
     * ����� ���������� ��� ��������
     *
     * @return  athlete's year of birth (���������� ��� ��������)
     */
    public int getYearOfBirth(){
        return yearOfBirth;
    }
     /** Family name of athlete
      * ����� ���������� �������
      *
      * @return  family name of Athlete
      */
    public String getFamilyName(){
        return familyName;
    }
     /** First name of athlete
      * ����� ���������� ���
      *
      * @return  First name of athlete
     */
    public String getName(){
        return name;
    }
     /** Method sets new group for this athlete
      * ����� ������ ������ ����������
      *
      * @param  g  new group
     */
    public void setGroup(Group g){
        group = g;
    }
     /** Get group of athlete
      * ����� ���������� ������ ����������
      * 
      * @return  group in which athlete was viewing
     */
    public Group getGroup(){
        return group;
    }
     /** Return information about athlete
      * ��������������� ������ toString
      * 
      * @return  information about athlete in format "FamilyName FirstName FullTimeOfRunning"
     */
    public String toString(){
        return getFamilyName()+" "+getName()+" "+getTotalTime().getTimeString();
    }
}
