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
 * Athlete.java
 *
 * Created on 27 Июнь 2006 г., 14:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

/**
 *
 * @author Mytinski Leonid
 * 
 * Object of model which content is infarmation of Family name, First name, year of birthday, group in which athlete runs and splits on all control points
 * Объект хранит информацию о спортсмене. Фамилия, Имя, год рождения, группу и отсечки на всех пунктах.
 *  
 */
public class Athlete {
    private String familyName, name;
    private Time[] splits,originalSplits;
    private int yearOfBirth;
    private Group group;
    private boolean dSQ = false;
    private java.util.Vector<AthleteListener> listeners;
    
    /*
     * Finish time as it's in protocol (or in split-file), not calculated as sum of splits.
     * If there isn't finish time in protocol, then 0.
     */
    private Time finishTime; 
    
    /**
     * Creates a new instance of Athlete 
     *  Создаёт новый экземпляр объекта Athlete
     * 
     * @param fN  Family name (Фамилия)
     * @param n  First name (Имя)
     * @param s  Splits (Отсечки)
     * @param g  Group (Группа)
     * @param yOfB  Year of birthday (Год рождения)
     * @param finish Finish time from protocol (Финишное время по протоколу)
     */
    public Athlete(String fN,String n,Time[] s,Group g, int yOfB, String finish) {
        familyName = fN;
        name = n;
        splits = s;
        originalSplits = s;
        yearOfBirth = yOfB;
        String[] hhmmss = finish.trim().split(":");
        int seconds = 0;
        for (int i = 0; i < hhmmss.length; i++) {
            seconds = 60 * seconds + Integer.parseInt(hhmmss[i]);
        }
        finishTime = new Time(seconds,2);
        finishTime.setTimeInSeconds(seconds);
        group = g;
        group.addAthlete(this);
        listeners = new java.util.Vector<AthleteListener>();
    }
    
    /**
     * Creates a new instance of Athlete with default finishTime (zero)
     *  Создаёт новый экземпляр объекта Athlete со значением finishTime по умолчанию (ноль)
     * 
     * 
     * @param fN  Family name (Фамилия)
     * @param n  First name (Имя)
     * @param s  Splits (Отсечки)
     * @param g  Group (Группа)
     * @param yOfB  Year of birthday (Год рождения)
     */
    public Athlete(String fN,String n,Time[] s,Group g, int yOfB) {
        this(fN, n, s, g, yOfB, "00");
    }
    
    /**
     * Creates a new instance of Athlete with year of birthday equal 2000
     *  Создаёт новый экземпляр объекта Athlete с годом рождения спортсмена по умолчанию равным 2000
     * 
     * 
     * @param fN  Family name (Фамилия)
     * @param n  First name (Имя)
     * @param s  Splits (Отсечки)
     * @param g  Group (Группа)
     */
    public Athlete(String fN, String n, Time[] s,Group g){
        this(fN,n,s,g,2000);
    }
    
    /** Method returns time from control point <code>n-1</code> to control point <code>n</code>
     * Метод возвращает время от пункта <code>n-1</code> до пункта <code>n</code>
     * 
     * @param  n  control point's number (номер контрольного пункта до которого определяется время)
     *
     * @return  Time from control point <code>n-1</code> to control point <code>n</code>
     *          (Время от пункта <code>n-1</code> до пункта <code>n</code>)
     */
    public Time getLap(int n){
        return splits[n-1];
    }
    
    /** Method returns number of control points
     *  Метод возвращает количество пунктов
     *  
     * @return  number of control points (количество пунктов, которые пробежал спортсмен)
     */
    public int getNumberOfLaps(){
        return splits.length;
    }
    
    /** Method for calculting full time that athlete spend on all laps 
     * Метод возвращает полное время затраченое на прохождение всех перегонов
     * 
     * @return  full time that athlete spend on all laps (полное время затраченое на прохождение всех перегонов)
     */
    public Time getTotalTime(){
        Time t = new Time(0,3);
        for(int i=0;i<splits.length;i++){
            t.addTime(splits[i]);
        }
        return t;
    }
    /**
     * Set new time on <code>n</code> lap 
     * 
     * @param  t  new time
     * @param  n  control point's number
     * 
     */
    public void setTimeOnLap(Time t, int n){
        splits[n-1]=t;
        notifyListeners();
    }
    /** Revert all changes made for this athlete
     * 
     * 
     */
    public void revertAllChanges(){
        splits = originalSplits;
        notifyListeners();
    }
    /**
     * Returns finish time
     * @return finish time
     */
    public Time getFinishTime() { return finishTime; }
    
    /** Method returns athlete's year of birth
     * Метод возвращает год рождения
     *
     * @return  athlete's year of birth (возвращает год рождения)
     */
    public int getYearOfBirth(){
        return yearOfBirth;
    }
     /** Family name of athlete
      * Метод возвращает Фамилию
      *
      * @return  family name of Athlete
      */
    public String getFamilyName(){
        return familyName;
    }
     /** First name of athlete
      * Метод возвращает Имя
      *
      * @return  First name of athlete
     */
    public String getName(){
        return name;
    }
     /** Method sets new group for this athlete
      * Метод меняет группу спортсмена
      *
      * @param  g  new group
     */
    public void setGroup(Group g){
        group = g;
    }
     /** Get group of athlete
      * Метод возвращает группу спортсмена
      * 
      * @return  group in which athlete was viewing
     */
    public Group getGroup(){
        return group;
    }
    private void notifyListeners(){
        for(AthleteListener list: listeners){
            list.splitsChanged();
        }
    }
    /**
     * 
     * @param  list  listener of changing athlete
     */
    public void addAthleteListener(AthleteListener list){
        listeners.add(list);
    }
    /**
     * 
     * @param  list  listener of changing athlete
     */
    public void removeAthleteListener(AthleteListener list){
        listeners.remove(list);
    }
     /** Return information about athlete
      * Переопределение метода toString
      * 
      * @return  information about athlete in format "FamilyName FirstName FullTimeOfRunning"
     */
    @Override
    public String toString(){
        return getFamilyName()+" "+getName()+" "+getTotalTime().getTimeString();
    }
}
