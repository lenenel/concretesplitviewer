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
 * SecondBestSplitViewer.java
 *
 * Created on 30 Июнь 2006 г., 0:17
 */

package ru.concretesoft.concretesplitviewer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import ru.spb.ConcreteSoft.tipWindow.MouseMoveQueue;
import ru.spb.ConcreteSoft.tipWindow.TipWindow;

/**
 *
 * @author Mytinski Leonid
 *
 * Panel for viewing splits relatively second best athlete
 * Панель для отображения сплитов в виде относительно второго лучшего
 */
public class SecondBestSplitViewer extends javax.swing.JPanel implements SplitViewer,ListDataListener,ListSelectionListener, MouseListener, MouseMotionListener{
    private AthleteListModel aModel;

    private Time[] secondBest;

    private boolean draw = false;
    private int[] xCoord;
    
    private int otst;
    private List<int[]> yCoord;
    private int[] prom;
    private int yMax;

    private TipWindow tipWindow;
    private TipThreadSplitViewer tipThread;

    private int yMin;
    
    private List<XCoordinatesListener> listeners;
    
    private AthleteIcon editingAthlete;
    private int editingCPNumber;
    private GlassSecondBestViewPanel glassPane;
    private int yLocationOfStartDrag;
    /** Creates new form SecondBestSplitViewer */
    public SecondBestSplitViewer() {
        editingAthlete = null;
        
        tipWindow = new TipWindow();
        initComponents();

        otst = -1;
        addMouseMotionListener(MouseMoveQueue.getInstance());
        addMouseMotionListener(this);
        addMouseListener(this);
        listeners = new LinkedList<XCoordinatesListener>();
        
        glassPane = new GlassSecondBestViewPanel();
//        tipWindow = new TipWindow();
////        tipWindow.setVisible(true);
//        TipThreadSplitViewer tipThread = new TipThreadSplitViewer(tipWindow, this);
//        tipThread.start();
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
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        Dimension d = getSize();
        g2.setPaint(Color.WHITE);
        g2.fillRect(0,0,d.width,d.height);
        g2.setPaint(Color.BLACK);
        FontMetrics fM = g2.getFontMetrics();
        otst = (otst==-1)? fM.stringWidth("-000:00")+5: otst;
        int width = d.width-otst;
        int height = d.height-2;
        Distance dist = aModel.getDistance();
        AthleteIcon[] athletes = (AthleteIcon[])(aModel.getSelectedValues());
        int[] splits = aModel.getViewingSplits();
        
        if(draw){
            int[] sizes = new int[splits.length];
            int tL = 0;
            int totalLength = 0;
            prom = new int[sizes.length]; 
            for(int i=0;i<sizes.length;i++){
                tL+=secondBest[splits[i]-1].getTimeInSeconds();
                prom[i]=tL;
                totalLength+=dist.getLengthOfDist(splits[i]);
            }
            int x=otst;
            xCoord = new int[sizes.length];
            for(int i=0;i<sizes.length;i++){
                sizes[i] = (int)(((double)dist.getLengthOfDist(splits[i])/totalLength)*width);
                g2.drawLine(x+sizes[i],0,x+sizes[i],height);
//                String s;
//                if(i<=(splits.length-2)){
//                    int diff = splits[i+1]-splits[i];
//                    if(diff>1) 
//                        s = splits[i]+"-"+(splits[i+1]-1);
//                    else 
//                        s=splits[i]+"";
//                } else {
//                    if(splits[i]==dist.getNumberOfCP()) 
//                        s=java.util.ResourceBundle.getBundle("ru/concretesoft/concretesplitviewer/i18on").getString("Finish");
//                    else 
//                        s = splits[i]+"";
//                }
//                g2.drawString(s,x+sizes[i]-fM.stringWidth(s)/2,height+fM.getHeight()+1);
                x+=sizes[i];
                xCoord[i] = x;
            }
            calculateYCoord();
            int h = yMax-yMin;
           
            int hTime = fM.getHeight();
            double stepTime = 30.0;
            int nT =(int) (h / stepTime);
            while(nT == 0){
                stepTime = Math.round(stepTime/2);
                nT = (int)(h / stepTime);
            }
            while((height/nT)<(hTime+10)){
                stepTime*=2;
                nT = (int) (h / stepTime);
            }
            Time tmp = new Time(0,2);
            tmp.setTimeInSeconds(Math.abs(yMin));
            String s = tmp.getTimeString();
            int y0 = (int)(((-yMin)/(double)h)*height);
            
            if(yMin<0)
                s = "-"+tmp.getTimeString();
            else if(yMin>0)
                s = "+"+tmp.getTimeString();
            else 
                s = tmp.getTimeString();
            
            g2.setPaint(Color.BLACK);
            g2.drawString(s,otst-fM.stringWidth(s),hTime-1);
             float dash1[] = {10.0f};
            BasicStroke dashed = new BasicStroke(1.0f, 
                                                  java.awt.BasicStroke.CAP_BUTT, 
                                                  java.awt.BasicStroke.JOIN_MITER, 
                                                  10.0f, dash1, 0.0f);
            g2.setStroke(dashed);
            for(int i=1;i<=nT;i++){
                int timeCur = yMin + (Math.abs(yMin) % (int)stepTime) + (int)(stepTime*i);
                
                tmp.setTimeInSeconds(Math.abs(timeCur));
                
                if(timeCur<0){
                    s = "-"+tmp.getTimeString();
                     
                } else if(timeCur>0){
                    s = "+"+tmp.getTimeString();
                } else 
                    s = tmp.getTimeString();
                
                int yH = (int)((timeCur-yMin)/(double)h*height);
//                yH = (int)((stepTime*i / h)*height);
                g2.setPaint(Color.BLACK);
                g2.drawString(s,otst-fM.stringWidth(s),yH);
               
                g2.setPaint(Color.LIGHT_GRAY);
                g2.drawLine(otst,yH,otst + width,yH);
            }
            g2.setStroke(new BasicStroke(1.0f));
           
            g2.setPaint(Color.LIGHT_GRAY);
            g2.drawLine(otst,y0,xCoord[xCoord.length-1],y0);
            Iterator<int[]> it = yCoord.iterator();
            int k=0;
            while(it.hasNext()){
                
                g2.setPaint(athletes[k].getColor());
                int[] yC = it.next();
                int y = (int)(((yC[0]-yMin)/(double)h)*height);

                g2.drawLine(otst,y0,xCoord[0],y);
                for(int i=1;i<splits.length;i++){

                    int y2 = (int)(((yC[i]-yMin)/(double)h)*height);
                    g2.drawLine(xCoord[i-1],y,xCoord[i],y2);
                    //                   System.out.println(xCoord[i-1]+"  "+y+"  "+xCoord[i]+"  "+y2);
                    y=y2;

                }
                k++;
            }
            
        }else{
            xCoord=null;
        }
        for(XCoordinatesListener e: listeners){
            e.xCoordinatesChanged(this);
        }
    }

    @Override
    public String toString(){
        return java.util.ResourceBundle.getBundle("ru/concretesoft/concretesplitviewer/i18on").getString("Second_Best_View");
    }
    
    /**
     * Method check all parametrs needed for draw chart 
     * 
     */
    private void setDraw(){
        draw = (secondBest != null) && (aModel.getViewingSplits() != null) && (aModel.getSelectedValues().length != 0);
    }


    public void removeSplit(int x) {
        if(xCoord!=null){
            for(int i=0;i<xCoord.length;i++){
                if(x<xCoord[i]){
                    aModel.removeSplitsForN(aModel.getViewingSplits()[i]);
                    break;
                }
            }
        }
    }

    /**
     * Method calculate all y coordinates of chart for all selected athletes
     * and add they to <code>ArrayList yCoord</code>. Also finds maximum and
     * minimum of all calculated y coordinates and store they to <code>yMax</code> 
     * and <code>yMin</code> fields.
     * 
     * 
     */
    private void calculateYCoord(){
        
        AthleteIcon[] athletes = (AthleteIcon[])(aModel.getSelectedValues());
        yMax = 0;
        yCoord = new ArrayList<int[]>();
        
        yMin = 0;

        for(int i=0;i<athletes.length;i++){
            Athlete a = athletes[i].getAthlete();
            treatmentOneAthlete(a);
            
        }
        
        
    }
    /**
     * Method calculates all y coordinates of chart for one athlete 
     * and add they to <code>List yCoord</code>
     * 
     * @param  a  athlete whose chart would be showed
     */
    private void treatmentOneAthlete(Athlete a){
        yMax--;
        yMin++;
        int[] splits = aModel.getViewingSplits();
        if (yCoord==null) yCoord = new ArrayList<int[]>();
        else;
        yCoord.add(new int[splits.length]);
        yCoord.get(yCoord.size() - 1)[0] = a.getLap(splits[0]).getTimeInSeconds()-prom[0];

        if(yCoord.get(yCoord.size() - 1)[0]<yMin)yMin=yCoord.get(yCoord.size() - 1)[0];
        else if(yCoord.get(yCoord.size() - 1)[0]>yMax)yMax=yCoord.get(yCoord.size() - 1)[0];
        else;

        int tot = 0;
        for(int i = 0;i<splits.length;i++){
            tot += a.getLap(splits[i]).getTimeInSeconds();
            yCoord.get(yCoord.size() - 1)[i] = tot - prom[i];
            if(yCoord.get(yCoord.size() - 1)[i]<yMin)yMin=yCoord.get(yCoord.size() - 1)[i];
            if(yCoord.get(yCoord.size() - 1)[i]>yMax)yMax=yCoord.get(yCoord.size() - 1)[i];
        }
        yMax++;
        yMin--;
    }
    public void setModel(AthleteListModel aM){
        aModel = aM;
        aM.addListDataListener(this);
        aM.addListSelectionListener(this);
    }
    public AthleteListModel getModel(){
        return aModel;
    }



    public void intervalAdded(ListDataEvent e) {
        contentsChanged(e);
    }

    public void intervalRemoved(ListDataEvent e) {
        contentsChanged(e);
    }

    public void contentsChanged(ListDataEvent e) {
//        calculateYCoord();
        if(aModel.getDistance()==null){
            secondBest = null;
        }else{
            Object[] allSelected = aModel.getGroupsList().getSelectedValues();
            List<Group> groups = new ArrayList<Group>();
            for(Object g : allSelected)
                groups.add((Group)g);
            
            secondBest = Tools.getAnyBest(groups,2);
        }
        setDraw();
        repaint();
    }

    public void valueChanged(ListSelectionEvent e) {
//        calculateYCoord();
        setDraw();
        repaint();
    }
    
    public int getSplit(Point p){
        if(xCoord!=null){
            for(int i=0;i<xCoord.length; i++){
                if(p.getX()<xCoord[i]){
                    return aModel.getViewingSplits()[i];
                }else;
            }
        }else{
            return -1;
        }
        return -1;
    }

    public void mouseClicked(MouseEvent evt) {
        if((evt.getButton()==MouseEvent.BUTTON2)||(MouseEvent.getMouseModifiersText(evt.getModifiers()).equals("Shift+Button1"))){
            aModel.restoreAllSplits();
        }
        else if(evt.getButton()==MouseEvent.BUTTON1){
            
            removeSplit(evt.getX());
        }
    }
    
    
    public void mousePressed(MouseEvent e) {
        //Find near node
        int x = e.getX();
        int y = e.getY();
        
        for(int i = 0; i < xCoord.length; i++){
//            System.out.println(x+" "+xCoord[i]);
            if(Math.abs(xCoord[i]-x) < 5){// 5 horizontal points on both sides
                editingCPNumber = i;// index(only from viewing) of control point which would be edited
                double scale = (yMax - yMin) / (double)getSize().height;
                AthleteIcon[] selectedAthletes = (AthleteIcon[]) aModel.getSelectedValues();
                for(int j = 0; j < yCoord.size(); j++){
                    int yA = (int) ((yCoord.get(j)[i] - yMin) / scale);
//                    System.out.println(y+" "+yA);
                    if(Math.abs(y-yA)<2){// 2 vertical points on both sides
                        yLocationOfStartDrag = yA;// Store y coordinate(of node) of start dragging
                        editingAthlete = selectedAthletes[j];// set editing athlete
                        // put all needed parameters to glass panel
                        glassPane.setAthlete(editingAthlete);
                        glassPane.setViewingSplits(aModel.getViewingSplits());
                        glassPane.setCPsNumber(editingCPNumber);
                        glassPane.setScale(scale);
                        glassPane.setXCoord(xCoord);
                        glassPane.setYCoord(yCoord.get(j));
                        glassPane.setOtst(otst);
                        glassPane.setYMin(yMin);
                        glassPane.setVisible(true);
                        glassPane.setLocationOnScreen(this.getLocationOnScreen());
                        glassPane.setYLocation(y);
//                        System.out.println(editingAthlete.getAthlete().getFamilyName());
                        break;
                    }
                }
                break;
            } else if(x < xCoord[i]){
                break;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        glassPane.setVisible(false);
        glassPane.setAthlete(null);
        if(editingAthlete == null){
            return ;
        }
        double scale = (yMax - yMin) / (double)getSize().height;
        editingCPNumber = aModel.getViewingSplits()[editingCPNumber];// change to real cp's number 
        Time oldTime = editingAthlete.getAthlete().getLap(editingCPNumber);
        int diff = e.getY()-yLocationOfStartDrag;
        int diffInSec = (int)(diff*scale);
        Time newTime = new Time(0, 2);
        newTime.setTimeInSeconds(oldTime.getTimeInSeconds()+diffInSec);
        editingAthlete.getAthlete().setTimeOnLap(newTime, editingCPNumber);
        editingAthlete = null;
        editingCPNumber = 0;
    }
    
    public void mouseEntered(MouseEvent e) {
        tipThread = new TipThreadSplitViewer(tipWindow, this);
        tipThread.start();
    }

    public void mouseExited(MouseEvent e) {
        tipThread.finish();
    }
    public int[] getXCoordinatesOfLaps(){
        return xCoord;
    }
    public void addXCoordinatesListener(XCoordinatesListener listener) {
        listeners.add(listener);
    }
    public void removeXCoordinatesListener(XCoordinatesListener listener) {
        listeners.remove(listener);
    }

    public void mouseDragged(MouseEvent e) {
        if((editingAthlete == null)||(glassPane==null)){
            return ;
        }
        glassPane.setYLocation(e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        
    }
    
    public void setGlassPane(GlassSecondBestViewPanel tP){
        glassPane = tP;
    }

    public JPanel getGlassPane() {
        return glassPane;
    }
}
