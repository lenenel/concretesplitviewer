/*
 * TipWindow library for show tip window
 * Copyright (C) 2006-2007 Mytinski Valeri (Valeri.Mytinski@gmail.com)
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
 * MouseMoveQueue.java
 *
 * Created on 14 Январь 2007 г., 19:28
 *
 */

package ru.spb.ConcreteSoft.tipWindow;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;


/**
 * A singleton queue that holds and return last position
 * where mouse moved event occured.
 *
 * @author mbb
 */
public class MouseMoveQueue implements MouseMotionListener {
    static private boolean isEmpty = true;
//    static private Point absP = new Point(-1,-1);
//    static private Point relP = new Point(-1,-1);
    static private Point[] ps = new Point[]{new Point(-1,-1), new Point(-1,-1)};
    static private String paramString;
    
    
    // singleton pattern
    static private MouseMoveQueue instance = new MouseMoveQueue();
    
    private MouseMoveQueue() {
    }

    static public MouseMoveQueue getInstance() {
        return instance;
    }
    
    public void mouseDragged(MouseEvent e) {
        
    }

    public void mouseMoved(MouseEvent e) {
        mouseMotion(e);
    }
    
    private void mouseMotion(MouseEvent e) {
        if (! (e.getSource() instanceof JComponent)) return;
        JComponent m = (JComponent)e.getSource();
        ps[1].x = e.getX();
        ps[1].y = e.getY();
        ps[0].x = (int)m.getLocationOnScreen().getX() + e.getX() + 10;
        ps[0].y = (int)m.getLocationOnScreen().getY() + e.getY() + 10;
        isEmpty = false;
        paramString = e.paramString();
    }
    
    /*
     * Returns Point where last mouseMoved was
     * or null if there was no new mouse motion since last getXY() call.
     */
    public synchronized Point[] getXY() {
        if (isEmpty) {
            return null;
        } else {
            isEmpty = true;
            return ps;
        }
    }
    
    /*
     * Returns paramString() for last mouse motion event
     * For Debugging
     */
    public synchronized String paramString() {
        return paramString;
    }
}
