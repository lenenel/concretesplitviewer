/*
 * GlassStandartSplitViewerPanel.java
 *
 * Created on 5 Сентябрь 2007 г., 17:50
 */

package ru.concretesoft.concretesplitviewer;

import java.awt.AlphaComposite;
import java.awt.Point;
/**
 *
 * @author  Mytinski Leonid
 */
public class GlassStandartSplitViewerPanel extends javax.swing.JPanel {
    private AlphaComposite composite;
    private AthleteIcon athlete;
    private int cPsNumber;
    private double scale;
    private int yLocation;
    private Point locationOnScreen;
    private int[] xCoord;
    private int yMax;
    private int otst;
    private int[] viewingSplits;
    /** Creates new form GlassStandartSplitViewerPanel */
    public GlassStandartSplitViewerPanel() {
        setOpaque(false);
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents
    @Override
    public void paintComponent(java.awt.Graphics g){
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        
        if(athlete == null){
            return ;
        }
        
        g2.setPaint(athlete.getColor());
        Point p = locationOnScreen;
        Point pl = this.getLocationOnScreen();
        g2.drawString(athlete.getAthlete().getFamilyName(), 
                (int)(p.getX()-pl.getX()) + 20, 
                (int)(p.getY()-pl.getY()) + 20);
        Athlete a = athlete.getAthlete();
        int yA = 0;
        for(int k=0;k<cPsNumber;k++){
            yA += athlete.getAthlete().getLap(viewingSplits[k]).getTimeInSeconds();
        }
        int xTemp = (cPsNumber==0) ? otst : xCoord[cPsNumber-1];
        g2.drawLine((int)(p.getX()-pl.getX()) + xTemp, 
                    (int)(p.getY()-pl.getY()) + (int)((yMax-yA)/scale), 
                    (int)(p.getX()-pl.getX()) + xCoord[cPsNumber], 
                    (int)(p.getY()-pl.getY()) + yLocation);
        yA = yMax-(int)(yLocation*scale);
        for(int i = cPsNumber; i < viewingSplits.length-1; i++){
            int yANew = yA + a.getLap(viewingSplits[i+1]).getTimeInSeconds();
            g2.drawLine((int)(p.getX()-pl.getX()) + xCoord[i], 
                    (int)(p.getY()-pl.getY()) + (int)((yMax-yA)/scale), 
                    (int)(p.getX()-pl.getX()) + xCoord[i+1], 
                    (int)(p.getY()-pl.getY()) + (int)((yMax-yANew)/scale));
            yA = yANew;
        }
        
    }
    public void setAthlete(AthleteIcon athlete) {
        this.athlete = athlete;
    }

    public void setCPsNumber(int cPsNumber) {
        this.cPsNumber = cPsNumber;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setYLocation(int yLocation) {
        this.yLocation = yLocation;
        repaint();
    }

    public void setLocationOnScreen(Point locationOnScreen) {
        this.locationOnScreen = locationOnScreen;
    }

    public void setXCoord(int[] xCoord) {
        this.xCoord = xCoord;
    }

    public void setYMax(int yMax) {
        this.yMax = yMax;
    }

    public void setOtst(int otst) {
        this.otst = otst;
    }

    public void setViewingSplits(int[] viewingSplits) {
        this.viewingSplits = viewingSplits;
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
