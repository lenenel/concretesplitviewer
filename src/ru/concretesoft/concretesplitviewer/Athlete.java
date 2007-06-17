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
 * @author Мытинский Леонид
 * 
 * Object of model which content is infarmation of Family name, First name, year of birthday, group in which athlete runs and splits on all control points
 * Объект хранит информацию о спортсмене. Фамилия, Имя, год рождения, группу и отсечки на всех пунктах.
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
     *  Создаёт новый экземпляр объекта Athlete
     * 
     * 
     * @param fN  Family name (Фамилия)
     * @param n  First name (Имя)
     * @param s  Splits (Отсечки)
     * @param g  Group (Группа)
     * @param yOfB  Year of birthday (Год рождения)
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
     /** Return information about athlete
      * Переопределение метода toString
      * 
      * @return  information about athlete in format "FamilyName FirstName FullTimeOfRunning"
     */
    public String toString(){
        return getFamilyName()+" "+getName()+" "+getTotalTime().getTimeString();
    }
}
