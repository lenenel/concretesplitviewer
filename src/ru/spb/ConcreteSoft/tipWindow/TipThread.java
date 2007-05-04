/*
 * TipThread.java
 *
 * Created on 15 Январь 2007 г., 21:06
 *
 */

package ru.spb.ConcreteSoft.tipWindow;

import java.awt.Point;

/**
 *
 * @author mbb
 */
public class TipThread extends Thread {
    TipWindow tipWindow;
    
    public TipThread(TipWindow tipWindow) {
        this.tipWindow = tipWindow;
    }
    
    public void run() {
        while (true) {
            try {
                sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (! tipWindow.isVisible()) continue;
            Point[] p = MouseMoveQueue.getInstance().getXY();
            if (p == null) continue;
            tipWindow.setLabelText(
                    "<html><font color=blue>"+MouseMoveQueue.getInstance().paramString()+"</font>"
                    +"<p><font color=red> x=</font>"+p[0].x
                    +"<p><font color=red> y=</font>"+p[0].y);
            tipWindow.setLocation(p[0]);
        }
    }
}
