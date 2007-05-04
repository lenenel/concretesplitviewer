/*
 * Group.java
 *
 * Created on 27 ���� 2006 �., 14:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author ��������� ������
 *
 * �����, ����������� ������ "������". ���� ������ �������� ���� ����������� �� ����� ������.
 */
public class Group {
    private Vector<Athlete> athletes = new Vector<Athlete>();
    private String name;
    private Distance dist;
    /** Creates a new instance of Group */
    public Group() {
    }
     /*
     * ����� ��������� ���������� � ������.
     */
    public void addAthlete(Athlete a){
        athletes.add(a);
    }
     /*
     * ����� ������� ���������� �� ������.
     */
    public void removeAthlete(Athlete a){
        athletes.remove(a);
    }
     /*
     * ����� ���������� �������� ������.
     */
    public String getName(){
        return name;
    }
     /*
     * ����� ���������� ���������.
     */
    public Distance getDistance(){
        return dist;
    }
     /*
     * ����� ������������� ���������.
     */
    public void setDistance(Distance d){
        dist = d;
        d.addGroup(this);
    }
     /*
     * ����� ���������� ����� ����� ��������� �� �������� n.
     */
    public HashSet<Time> getTimesOnLap(int n){
        HashSet<Time> tmp = new HashSet<Time>();
        Iterator<Athlete> it = athletes.iterator();
        while(it.hasNext()){
            tmp.add(it.next().getLap(n));
            
        }
        return tmp;
    }
     /*
     * ����� ������������� �������� ������.
     */
    public void setName(String name){
        this.name = name;
    }
     /*
     * ����� ���������� ���������� �� ����������� ������.
     */
    public Athlete getAthlete(int n){
        return athletes.get(n-1);
    }
     /*
     * ����� ���������� ���� ����������� ���� ������.
     */
    public Vector<Athlete> getAthletes(){
        return (Vector<Athlete>)athletes.clone();
    }
     /*
     * ��������������� ������ toString
     */
    public String toString(){
        return getName();
    }
}
