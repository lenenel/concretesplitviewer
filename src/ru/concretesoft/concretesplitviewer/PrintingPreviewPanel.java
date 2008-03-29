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
 * PrintingPreviewPanel.java
 *
 * Created on 16 Декабрь 2007 г., 18:34
 */

package ru.concretesoft.concretesplitviewer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;
import javax.swing.RepaintManager;

/**
 *
 * @author  Mytinski Leonid
 * 
 * Panel for print and print preview from ConcreteSplitViewer
 */
public class PrintingPreviewPanel extends javax.swing.JPanel {
    private JComponent chartComponent;
    private JComponent listComponent;
    private double listPart = 0.2;
    /** Creates new form PrintingPreviewPanel */
    public PrintingPreviewPanel(JComponent chartComponent, JComponent listComponent) {
        this.chartComponent = chartComponent;
        this.listComponent = listComponent;
//        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Dimension chartDimension = chartComponent.getSize();
        Dimension listDimension = listComponent.getSize();
        Dimension size = getSize();
        double chartXScale = (((double)size.width)/chartDimension.width)*(1-listPart);
        double chartYScale = ((double)size.height)/chartDimension.height;
        double listXScale = ((double)size.width)/listDimension.width * listPart;
        double listYScale = ((double)size.height)/listDimension.height;

        g2.transform(AffineTransform.getScaleInstance(chartXScale, chartYScale));
        RepaintManager currentManager = RepaintManager.currentManager(chartComponent);
        currentManager.setDoubleBufferingEnabled(false);
        chartComponent.paint(g2);
        currentManager.setDoubleBufferingEnabled(true);
        g2.transform(AffineTransform.getScaleInstance(1/chartXScale, 1/chartYScale));

        g2.translate(size.width*(1-listPart), 0.0);
        g2.transform(AffineTransform.getScaleInstance(listXScale, 1));
        currentManager = RepaintManager.currentManager(listComponent);
        currentManager.setDoubleBufferingEnabled(false);
        listComponent.paint(g2);
        currentManager.setDoubleBufferingEnabled(true);
        g2.transform(AffineTransform.getScaleInstance(1/listXScale, 1));
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}