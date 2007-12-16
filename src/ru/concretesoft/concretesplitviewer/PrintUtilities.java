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
 * PrintUtilities.java
 *
 * Created on 16 December 2007 y., 11:05
 */

package ru.concretesoft.concretesplitviewer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import javax.swing.JComponent;
import javax.swing.RepaintManager;

/**
 *
 * @author Mytinski Leonid
 */
public class PrintUtilities implements Printable{
    
    private JComponent component;

    public PrintUtilities(JComponent component){
        this.component = component;
    }
    
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return(NO_SUCH_PAGE);
        } else {
            Graphics2D g2d = (Graphics2D)graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            
            Dimension chartDimension = component.getSize();
            
            double chartXScale = pageFormat.getImageableWidth()/chartDimension.width;
            double chartYScale = pageFormat.getImageableHeight()/chartDimension.height;
            
            g2d.transform(AffineTransform.getScaleInstance(chartXScale, chartYScale));
            
            RepaintManager currentManager = RepaintManager.currentManager(component);
            currentManager.setDoubleBufferingEnabled(false);
            component.paint(g2d);
            currentManager.setDoubleBufferingEnabled(true);
            return(PAGE_EXISTS);
        }
    }
    
}
