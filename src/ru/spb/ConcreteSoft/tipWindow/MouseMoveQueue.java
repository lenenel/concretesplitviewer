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
import javax.swing.JPanel;
import sun.security.x509.X400Address;

/**
 * A singleton queue that holds and return last position
 * where mouse motion event occured.
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
        mouseMotion(e);
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
