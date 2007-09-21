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
