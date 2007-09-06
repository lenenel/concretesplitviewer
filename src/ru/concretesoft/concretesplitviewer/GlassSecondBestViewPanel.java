/*
 * GlassSecondBestViewPanel.java
 *
 * Created on 4 Сентябрь 2007 г., 22:08
 */

package ru.concretesoft.concretesplitviewer;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
/**
 *
 * @author  Mytinski Leonid
 * 
 * Glass panel for second best view that show chart after affecting drag&drop
 */
public class GlassSecondBestViewPanel extends javax.swing.JPanel {
    private AlphaComposite composite;
    private AthleteIcon athlete;
    private int cPsNumber;
    private double scale;
    private int yLocation;
    private Point locationOnScreen;
    private int yMin;
    private int[] xCoord;
    private int[] yCoord;
    private int[] yCoordOrig;
    private int otst;
    private int[] viewingSplits;

    /** Creates new form TransientPanel */
    public GlassSecondBestViewPanel() {
        setOpaque(false);
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
    }
    public void setAthlete(AthleteIcon athlete){
        this.athlete = athlete;
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

    public void setCPsNumber(int numberOfCP) {
        this.cPsNumber = numberOfCP;
    }
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        
        if(athlete == null){
            return ;
        }
        
        g2.setPaint(athlete.getColor());
        Point p = locationOnScreen;
        Point pl = this.getLocationOnScreen();
        Time t = new Time(0,2);
        int diff = yCoord[cPsNumber] - yCoordOrig[cPsNumber];
        t.setTimeInSeconds(diff);
        g2.drawString(athlete.getAthlete().getFamilyName()+" "+t.getTimeString(), 
                (int)(p.getX()-pl.getX()) + otst + 1, 
                getHeight()-20);
        g2.setComposite(composite);
        Athlete a = athlete.getAthlete();
        int xTemp = (cPsNumber == 0) ? otst : xCoord[cPsNumber-1];
        int yTemp = (cPsNumber == 0) ? 0 : yCoord[cPsNumber-1];
        g2.drawLine((int)(p.getX()-pl.getX()) + xTemp, 
                    (int)(p.getY()-pl.getY()) + (int)((yTemp-yMin)/scale), 
                    (int)(p.getX()-pl.getX()) + xCoord[cPsNumber], 
                    (int)(p.getY()-pl.getY()) + (int)((yCoord[cPsNumber]-yMin)/scale));
        for(int i = cPsNumber; i < viewingSplits.length-1; i++){
            g2.drawLine((int)(p.getX()-pl.getX()) + xCoord[i], 
                    (int)(p.getY()-pl.getY()) + (int)((yCoord[i]-yMin)/scale), 
                    (int)(p.getX()-pl.getX()) + xCoord[i+1], 
                    (int)(p.getY()-pl.getY()) + (int)((yCoord[i+1]-yMin)/scale));
        }
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setYLocation(int yLocation) {
        this.yLocation = yLocation;
        int diff = (int)(yLocation*scale)-yCoord[cPsNumber]+yMin;
        for(int i = cPsNumber; i < yCoord.length; i++)
            yCoord[i]=yCoord[i]+diff;
        repaint();
    }

    public void setLocationOnScreen(Point locationOnScreen) {
        this.locationOnScreen = locationOnScreen;
    }

    public void setYMin(int yMin) {
        this.yMin = yMin;
    }

    public void setXCoord(int[] xCoord) {
        this.xCoord = xCoord;
    }

    public void setYCoord(int[] yCoord) {
        this.yCoord = yCoord.clone();
        yCoordOrig = yCoord.clone();
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
