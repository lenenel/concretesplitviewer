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
 * SplitReader.java
 *
 * Created on 27 Июнь 2006 г., 14:32
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
 * Абстрактный класс, описывающий читателей из файла сплитов
 * Absctract class for split reader
 */
public abstract  class SplitReader {
    /**
     * Метод для получения названий всех групп
     *
     * @return <code>Vector</code> of groups names 
     */
    public abstract Vector<String> getGroupsNames();
    /**
     * Метод для получения всех групп
     *
     * @return <code>Vector</code> of all existings groups if reader
     */
    public abstract Vector<Group> getAllGroups();
    /**
     * Метод для получения группы по названию
     *
     * @param name name of group
     * @return group with name equals parameter <code>name</code>
     */
    public abstract Group getGroup(String name);
    /**
     * Метод для получения группы по номеру
     *
     * @param index group's index in reader
     * @return group that in reader have index <code>index</code>
     */
    public abstract Group getGroup(int index);
    /**
     * Метод для получения групп по дистанции
     *
     * @param index distance's index in reader
     * @return set of groups that run distance with index <code>index</code>
     */
    public abstract Vector<Group> getGroupsByDist(int index);
    
    public abstract String getFileName();
    public abstract String getEventDescription();
}
