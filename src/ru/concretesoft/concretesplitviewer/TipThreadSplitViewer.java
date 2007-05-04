/*
 * TipThreadSplitViewer.java
 *
 * Created on 17 Февраль 2007 г., 18:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.awt.Point;
import java.util.Collection;
import java.util.Iterator;
import ru.spb.ConcreteSoft.tipWindow.MouseMoveQueue;
import ru.spb.ConcreteSoft.tipWindow.TipWindow;

/**
 *
 * @author lene
 */
public class TipThreadSplitViewer extends Thread {
   TipWindow tipWindow;
   SplitViewer splitViewer;
   private boolean finish = false;
    
    public TipThreadSplitViewer(TipWindow tipWindow, SplitViewer sV) {
        this.tipWindow = tipWindow;
        splitViewer = sV;
    }
    
    public void run() {
        while (!finish) {
            try {
                sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
//            if (! tipWindow.isVisible()) continue;
            Point[] ps = MouseMoveQueue.getInstance().getXY();
            if (ps == null) continue;
            System.out.println(ps[1].getX());
            int i = splitViewer.getSplit(ps[1]);
            if(i>0){
                
                Collection<AthleteIcon> athletes = splitViewer.getModel().getOneLap(i);
                Time prevTime = null;
                int position=0;
                Iterator<AthleteIcon> it = athletes.iterator();
                String text = "";
                if(it.hasNext()){
                    AthleteIcon a = it.next();
                    prevTime = a.getAthlete().getLap(i);
                    position++;
                    if(!a.isSelected())
                        text += position+" "+a.getAthlete().getLap(i).getTimeString()+" <b>" + a.getAthlete().getFamilyName()+" "+a.getAthlete().getName()+"</b><br>";
                    else {
                        int r = a.getColor().getRed();
                        int g = a.getColor().getGreen();
                        int b = a.getColor().getBlue();
                        int rgb = r*65536+g*256+b;
    //                    int rgb = a.getColor().getRGB();
    //                    String h = Integer.toHexString(r)+Integer.toHexString(g)+Integer.toHexString(b);
                        String h = Integer.toHexString(rgb);
                        while(h.length()<6){
                            h = "0"+h;
                        }
                        text += "<font color=\"#"+h+"\">"+position+" "+a.getAthlete().getLap(i).getTimeString()+" <b>" + a.getAthlete().getFamilyName()+" "+a.getAthlete().getName()+"</b></font><br>";
                    }
                }else;
                while(it.hasNext()){
                    AthleteIcon a = it.next();
                    if(prevTime.compareTo(a.getAthlete().getLap(i)) < 0){
                        position++;
                        prevTime = a.getAthlete().getLap(i);
                    }else;
                    int r = a.getColor().getRed();
                    int g = a.getColor().getGreen();
                    int b = a.getColor().getBlue();
                    int rgb = r*65536+g*256+b;
//                    int rgb = a.getColor().getRGB();
//                    String h = Integer.toHexString(r)+Integer.toHexString(g)+Integer.toHexString(b);
                    String h = Integer.toHexString(rgb);
                    while(h.length()<6){
                        h = "0"+h;
                    }
                    if(a.isSelected()){
                        text += "<font color=\"#"+h+"\">"+position+" "+a.getAthlete().getLap(i).getTimeString()+" <b>" + a.getAthlete().getFamilyName()+" "+a.getAthlete().getName()+"</b></font><br>";
                    }
//                    System.out.println(it.next());
                }
                tipWindow.setLabelText("<html><body>"+i+"<br>"+text);
                tipWindow.setLocation(ps[0]);
                if (! tipWindow.isVisible()) tipWindow.setVisible(true);
            }else{
                tipWindow.setVisible(false);
            }
            
        }
    }
    public void finish(){
        finish = true;
    }
}
