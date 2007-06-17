/*
 * SplitReader.java
 *
 * Created on 27 ���� 2006 �., 14:32
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
 * ����������� �����, ����������� ��������� �� ����� �������
 */
public abstract  class SplitReader {
    /**
     * ����� ��� ��������� �������� ���� �����
     *
     */
    public abstract Vector<String> getGroupsNames();
    /**
     * ����� ��� ��������� ���� �����
     *
     */
    public abstract Vector<Group> getAllGroups();
    /**
     * ����� ��� ��������� ������ �� ��������
     *
     */
    public abstract Group getGroup(String name);
    /**
     * ����� ��� ��������� ������ �� ������
     *
     */
    public abstract Group getGroup(int number);
    /**
     * ����� ��� ��������� ����� �� ���������
     *
     */
    public abstract Vector<Group> getGroupsByDist(int number);
    
    public abstract String getFileName();
    public abstract String getEventDescription();
}
