/*
 * SplitViewer.java
 *
 * Created on 30 Июнь 2006 г., 0:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Мытинский Леонид
 *
 * Интерфейс для отображения сплитов
 */
public interface SplitViewer {
    public void removeSplit(int x);
    public void setModel(AthleteListModel model);
    public AthleteListModel getModel();
    /**
     *  Returns number of lap, that associated whith this point
     *
     *  @param  p  point which may be associated whith one of laps.
     *
     *  @return  positive number of laps whith that associated the point or -1 if the point is associated with no point.
     */
    public int getSplit(Point p);
    /**
     * Returns x coordinates of viewing laps
     *
     * @return  array of viewing lap's x coordinates. <code>null</code> if nothing show
     */
    public int[] getXCoordinatesOfLaps();
    /**
     * Add listener of changing x coordinates
     *
     * @param  listener  listener of changing
     */
    public void addXCoordinatesListener(XCoordinatesListener listener);
    /**
     * Remove listener of changing x coordinates
     *
     * @param  listener  listener of changing
     */
    public void removeXCoordinatesListener(XCoordinatesListener listener);
}
