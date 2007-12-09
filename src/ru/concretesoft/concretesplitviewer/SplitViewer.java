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
 * SplitViewer.java
 *
 * Created on 30 Июнь 2006 г., 0:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.awt.Point;

/**
 *
 * @author Mytinski Leonid
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
    /**
     * Returns <code>JPanel</code> that will be glass for main frame
     * 
     * @return <code>JPanel</code> that will be glass for main frame
     */
    public javax.swing.JPanel getGlassPane();
}
