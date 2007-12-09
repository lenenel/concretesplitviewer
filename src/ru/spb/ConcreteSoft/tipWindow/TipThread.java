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
