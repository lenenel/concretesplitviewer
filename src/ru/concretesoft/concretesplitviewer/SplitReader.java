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
 * @author Мытинский Леонид
 *
 * Абстрактный класс, описывающий читателей из файла сплитов
 */
public abstract  class SplitReader {
    /**
     * Метод для получения названий всех групп
     *
     */
    public abstract Vector<String> getGroupsNames();
    /**
     * Метод для получения всех групп
     *
     */
    public abstract Vector<Group> getAllGroups();
    /**
     * Метод для получения группы по названию
     *
     */
    public abstract Group getGroup(String name);
    /**
     * Метод для получения группы по номеру
     *
     */
    public abstract Group getGroup(int number);
    /**
     * Метод для получения групп по дистанции
     *
     */
    public abstract Vector<Group> getGroupsByDist(int number);
    
    public abstract String getFileName();
    public abstract String getEventDescription();
}
