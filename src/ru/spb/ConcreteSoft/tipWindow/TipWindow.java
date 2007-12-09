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
 * TipWindow.java
 *
 * Created on 14 Январь 2007 г., 22:40
 *
 */

package ru.spb.ConcreteSoft.tipWindow;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JWindow;

/**
 *
 * @author mbb
 */
public class TipWindow extends JFrame implements MouseListener {
    private JLabel label = new JLabel("Text looooooooooooooong");
    
    public TipWindow() {
        setUndecorated(true);
//        setBackground(new Color(255,255,255,63));
//        setForeground(new Color(255,255,255,63));
//        label.setBackground(new Color(255,255,255,63));
//        label.setForeground(new Color(255,255,255,63));
        setFocusableWindowState(false);
        setFocusable(false);
        disableEvents(MouseEvent.MOUSE_EVENT_MASK);
        setAlwaysOnTop(true);
        getContentPane().add(label);
        pack();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    
    public void setLabelText(String text) {
//        setVisible(false);
//        getContentPane().remove(label);
//        pack();
        label.setText(text);
//        getContentPane().add(label);
        pack();
//        repaint();
//        setVisible(true);
    }
}
