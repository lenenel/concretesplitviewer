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
