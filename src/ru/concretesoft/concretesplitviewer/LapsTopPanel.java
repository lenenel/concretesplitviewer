/*
 * LapsTopPanel.java
 *
 * Created on 22 Май 2007 г., 21:31
 */

package ru.concretesoft.concretesplitviewer;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author  Mytinski Leonid
 *
 * Panel show all laps as bricks with same heigths and length relate to size of lap. Color of filling brick means selected or not it lap.
 */
public class LapsTopPanel extends javax.swing.JPanel implements ListDataListener,MouseListener,XCoordinatesListener{
    private AthleteListModel model;
    private int[] xCoord;
    private boolean[] selected;
    private SplitViewer splitViewer;
    private int yOtst = 10;
    /** Creates new form LapsTopPanel */
    private LapsTopPanel() {
        
    }
    public LapsTopPanel(AthleteListModel model, SplitViewer splitViewer) {
        setModel(model);
        xCoord = null;
        this.addMouseListener(this);
        setSplitViewer(splitViewer);
        
    }
    public void paint(Graphics g){
        
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = getSize();
        double width = d.getWidth();
        double height = d.getHeight();
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, (int) width, (int) height);
        
        Distance distance = model.getDistance();
        if((model!=null)&&(distance!=null)){
            int otst;
            FontMetrics fM = g2.getFontMetrics();
            otst = fM.stringWidth("-000:00")+5;
            int allLength = distance.getLength();
            double scale = (width-otst) / allLength;
            int nOfCp = distance.getNumberOfCP();
            xCoord = new int[nOfCp+1];
            int[] xCoordFromViewer = splitViewer.getXCoordinatesOfLaps();
            //Creating boolean array of selected laps
            selected = new boolean[nOfCp];
            for(boolean b:selected){
                b=false;
            }
            int[] viewSplits = model.getViewingSplits();
            for(int i: viewSplits){
                selected[i-1]=true;
            }

            
            int curX = otst;
            xCoord[0]=curX;
            yOtst = (int)(height/2);
            for(int i = 0; i < nOfCp; i++){
                
//                g2.setPaint(Color.BLACK);
//                g2.drawLine(curX+(int)w,0,curX+(int)w,(int)(height/2));
//                if(selected[i])
//                    g2.setPaint(Color.GREEN);
//                else
//                    g2.setPaint(Color.RED);
//                g2.fillRect(curX+1,0,(int)w-1,(int)(height/2));
//                g2.setPaint(Color.BLACK);
//                String s;
//                s= ((i+1)==distance.getNumberOfCP())? java.util.ResourceBundle.getBundle("ru/concretesoft/concretesplitviewer/i18on").getString("Finish"): (i+1)+"";
//                if(fM.stringWidth(s)<=w){
//                    g2.drawString(s,curX+(int)(w/2)-fM.stringWidth(s)/2,fM.getHeight());
//                }else;
                
            }
            if((xCoordFromViewer!=null)&&(xCoordFromViewer.length==viewSplits.length)){
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int lastView=0;
//                g2.drawLine(xCoord[0],(int)(height/2),xCoord[0],(int)height);
                for(int i = 1; i < nOfCp+1; i++){
                    double w = distance.getLengthOfDist(i)*scale;
                    curX=curX+(int)w;
                    xCoord[i]=curX;
                    GeneralPath pathTop = new GeneralPath();
                    GeneralPath pathBottom = new GeneralPath();
                    pathTop.moveTo(xCoord[i-1],yOtst);
                    pathTop.lineTo(xCoord[i-1],0);
                    pathTop.lineTo(xCoord[i],0);
                    pathTop.lineTo(xCoord[i],yOtst);
                    if(selected[i-1])
                        g2.setPaint(Color.GREEN);
                    else
                        g2.setPaint(Color.RED);
                    g2.fill(pathTop);
                    g2.setStroke(new BasicStroke(1.0f));
                    g2.setPaint(Color.BLACK);
                    g2.draw(pathTop);
                    pathBottom.moveTo(xCoord[i],yOtst);
                    if(lastView < viewSplits.length){
                        if(viewSplits[lastView]>i){
                            int x = (lastView>0)?xCoordFromViewer[lastView-1]:xCoord[0];
                            pathBottom.lineTo(x,(float)height);
//                            g2.drawLine(xCoord[i],(int)(height/2),x,(int)height);
                        }else if(viewSplits[lastView]==i){
                            pathBottom.lineTo(xCoordFromViewer[lastView],(float)height);
                            int x = (lastView>0)?xCoordFromViewer[lastView-1]:xCoord[0];
                            pathBottom.lineTo(x,(float)height);
//                            g2.drawLine(xCoord[i],(int)(height/2),xCoordFromViewer[lastView],(int)height);
                            lastView++;
                        }else;
                    }else{
                        pathBottom.lineTo(xCoordFromViewer[xCoordFromViewer.length-1],(float)height);
//                        g2.drawLine(xCoord[i],(int)(height/2),xCoordFromViewer[xCoordFromViewer.length-1],(int)height);
                    }
                    pathBottom.lineTo(xCoord[i-1],yOtst);
                    
                    GradientPaint greenToWhite = new GradientPaint(xCoord[0],yOtst,Color.GREEN,xCoord[0],(int)height,Color.WHITE);
                    if(selected[i-1])
                        g2.setPaint(greenToWhite);
                    else
                        g2.setPaint(Color.RED);
                    
                    g2.fill(pathBottom);
                    float dash1[] = {1.0f};
                    BasicStroke dashed = new BasicStroke(0.5f, 
                                                  java.awt.BasicStroke.CAP_BUTT, 
                                                  java.awt.BasicStroke.JOIN_MITER, 
                                                  10.0f, dash1, 0.0f);
                    g2.setStroke(dashed);
                    g2.setPaint(Color.BLACK);
                    g2.draw(pathBottom);
                }
            }else;
        }else;
    }
    /** Sets model
     *
     * @param  a  <code>AthleteListModel</code> model
     */
    public void setModel(AthleteListModel a){
        model = a;
        model.addListDataListener(this);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void intervalAdded(ListDataEvent e) {
    }

    public void intervalRemoved(ListDataEvent e) {
    }

    public void contentsChanged(ListDataEvent e) {
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if((xCoord[0]<x)&&(y<yOtst)){
            for(int i=1;i<xCoord.length;i++){
                if(xCoord[i]>x){
                    if(selected[i-1]) 
                        model.removeSplitsForN(i);
                    else
                        model.addSplitsForN(i);
                    break;
                }else;
            }
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public SplitViewer getSplitViewer() {
        return splitViewer;
    }

    public void setSplitViewer(SplitViewer splitViewer) {
        if(this.splitViewer!=null){
            this.splitViewer.removeXCoordinatesListener(this);
        }
        this.splitViewer = splitViewer;
        this.splitViewer.addXCoordinatesListener(this);
    }

    public void xCoordinatesChanged(SplitViewer source) {
        repaint();
    }
    
}
