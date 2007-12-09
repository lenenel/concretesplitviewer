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
 * Lap.java
 * 
 * Created on 17.09.2007, 21:39:04
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

/**
 *
 * @author Mytinski Leonid
 * 
 * Class desribed one lap of distance.
 * 
 */
public class Lap {
    private int length; // length of lap
    private int beginNumber; 
    private int endNumber;  
    /**
     * Start
     */
    public static final int START_CONTROL_POINT = 0;
    /**
     * Finsh
     */
    public static final int FINISH_CONTROL_POINT = -1;
    /**
     * Number of control point is unknown
     */
    public static final int UNKNOWN_CONTROL_POINT = -2;
    /**Create new lap
     * 
     * @param beginNumber real number of control point from which lap begin
     * @param endNumber real number of control point where lap ends
     * @param length length of lap
     */
    public Lap(int beginNumber, int endNumber, int length){
        setBeginNumber(beginNumber);
        setEndNumber(endNumber);
        setLength(length);
    }
    /**
     * 
     * 
     * @param length length of lap
     */
    public Lap(int length){
        this(UNKNOWN_CONTROL_POINT, UNKNOWN_CONTROL_POINT, length);
    }

    /**
     * 
     * @return length of lap
     */
    public int getLength() {
        return length;
    }

    /**
     * 
     * @param length length of lap
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * 
     * @return real number of control point from which lap begin
     */
    public int getBeginNumber() {
        return beginNumber;
    }

    /**
     * 
     * @param beginNumber real number of control point from which lap begin
     */
    public void setBeginNumber(int beginNumber) {
        this.beginNumber = beginNumber;
    }

    /**
     * 
     * @return real number of control point where lap ends
     */
    public int getEndNumber() {
        return endNumber;
    }

    /**
     * 
     * @param endNumber real number of control point where lap ends
     */
    public void setEndNumber(int endNumber) {
        this.endNumber = endNumber;
    }
}
