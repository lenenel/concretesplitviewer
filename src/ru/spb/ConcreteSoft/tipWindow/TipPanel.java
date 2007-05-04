/*
 * TipPanel.java
 *
 * Created on 14 Январь 2007 г., 18:43
 *
 */

package ru.spb.ConcreteSoft.tipWindow;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author mbb
 */
public class TipPanel extends JPanel implements MouseListener {
    
    public TipPanel() {
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
        System.out.println(e.getX());
    }
    
}
