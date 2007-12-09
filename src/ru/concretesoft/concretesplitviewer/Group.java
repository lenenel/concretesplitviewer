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
 * Group.java
 *
 * Created on 27 Июнь 2006 г., 14:49
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
 * @author Mytinski Leonid
 *
 * Класс, описывающий объект "группа". Этот объект содержит всех спортсменов из одной группы.
 */
public class Group {
    private Vector<Athlete> athletes = new Vector<Athlete>();
    private String name;
    private Distance dist;
    /** Creates a new instance of Group */
    public Group() {
    }
     /*
     * Метод добавляет спортсмена в группу.
     */
    public void addAthlete(Athlete a){
        athletes.add(a);
    }
     /*
     * Метод удаляет спортсмена из группы.
     */
    public void removeAthlete(Athlete a){
        athletes.remove(a);
    }
     /*
     * Метод возвращает название группы.
     */
    public String getName(){
        return name;
    }
     /*
     * Метод возвращает дистанцию.
     */
    public Distance getDistance(){
        return dist;
    }
     /*
     * Метод устанавливает дистанцию.
     */
    public void setDistance(Distance d){
        dist = d;
        d.addGroup(this);
    }
     /*
     * Метод возвращает набор времён показаных на перегоне n.
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
     * Метод устанавливает название группы.
     */
    public void setName(String name){
        this.name = name;
    }
     /*
     * Метод возвращает спортсмена по порядковому номеру.
     */
    public Athlete getAthlete(int n){
        return athletes.get(n-1);
    }
     /*
     * Метод возвращает всех спортсменов этой группы.
     */
    public Vector<Athlete> getAthletes(){
        return (Vector<Athlete>)athletes.clone();
    }
     /*
     * Переопределение метода toString
     */
    public String toString(){
        return getName();
    }
}
