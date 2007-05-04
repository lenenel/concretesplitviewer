/*
 * AthleteIcon.java
 *
 * Created on 28 Июнь 2006 г., 22:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.Icon;



/**
 *
 * @author Мытинский Леонид
 *
 * Вспомогательный класс для отображения спортсменов в списке спортсменов
 */
public class AthleteIcon implements Icon{
    private Athlete athlete;
    private Graphics g;
    private FontMetrics fM;
    private Color color;
    private boolean selected;
    private int otst = 5;
    private int[] viewSplits;
    private int position;
    private Time totTime,diffTime;
    /**
     * Creates a new instance of AthleteIcon
     * 
     * a - Спортсмен
     * fM - объект типа java.awt.FontMetrics для определения размеров
     */
    public AthleteIcon(Athlete a,FontMetrics fM) {
        athlete =a;
        this.fM = fM;
        color = Color.WHITE;
    }
    /**
     * Метод возвращает спортсмена.
     *
     */
   public Athlete getAthlete(){
       return athlete;
   }
   /**
     * Метод устанавливает цвет спорстмена.
     *
     */
   public void setColor(Color c){
       color = c;
       
   }
   /**
     * Метод возвращает цвет спортсмена.
     *
     */
   public Color getColor(){
       return color;
   }
   /**
     * Метод делает спортсмена выбраным или снимает выбор.
     *
     */
   public void setSelected(boolean selected){
       this.selected = selected;
       
       
   }
   /**
     * Метод устанавливает выбранные перегоны.
     *
     */
   public void setSplits(int[] spl){
        viewSplits = spl;
        
        totTime = null;

        diffTime = null;
        
   }
   /**
     * Метод устанавливает место, которое занимает спортсмен.
     *
     */
   public void setPosition(int n){
       position = n;
   }
   /**
     * Метод возвращает состояние выбора спортсмена.
     *
     */
   public boolean isSelected(){
       return selected;
   }
   
   /** Method calculating "mean" speed this athlete
    *
    * @return  "mean" speed
    *
    * @see  #Tools.getComplexMeanSpeed(Distance, Athlete, int[], double)
    */
   public Time getMeanSpeed(){
       return Tools.getComplexMeanSpeed(athlete.getGroup().getDistance(),athlete, viewSplits, 0.1);
   }

   /**
     * Метод возвращает полное время по всем выбранным перегонам
     *
     */
   public Time getTotalTime(){
       
       Time totTime = new Time(0,3);
       if(viewSplits != null)
           for(int i=0;i<viewSplits.length;i++){
                totTime.addTime(athlete.getLap(viewSplits[i]));
            }
       else 
           totTime = athlete.getTotalTime();
       return totTime;
   }
   
   
   // Реализация методов интерфейса Icon
   
    public void paintIcon(Component c, Graphics g, int x, int y) {
        String text = athlete.getFamilyName()+" "+athlete.getName();
        if(totTime==null)
                totTime = getTotalTime();
//        Time totTime = getTotalTime();
        String time = totTime.getTimeString();
        
        if(diffTime==null){
            Time meanSpeed = getMeanSpeed();
            String meanS = meanSpeed.getTimeString();
            int totLength = Tools.calculatTotalLength(athlete.getGroup().getDistance(), viewSplits);
            int idealTime =(int) (meanSpeed.getTimeInSeconds() * totLength/1000.0);
            diffTime = new Time(0,2);
            diffTime.setTimeInSeconds(totTime.getTimeInSeconds()-idealTime);
        }
        
        String diff = diffTime.getTimeString();
        
        int sizeTime = g.getFontMetrics().stringWidth("0:00:00");
        int sizeMest = g.getFontMetrics().stringWidth("000");
        int sizeMean = g.getFontMetrics().stringWidth("+000:00");
        Color curColor;
        Color foreground;
        if(selected){
           foreground = Color.WHITE;
           curColor = color;
        }
        else {
           foreground = Color.BLACK;
           curColor = Color.WHITE;
        }
        g.setColor(curColor);
        g.fillRect(x,y,getIconWidth(),getIconHeight());
         
        g.setColor(foreground);
        g.drawString(position+"",x,y+g.getFontMetrics().getHeight()-3);
        g.drawString(time,x+sizeMest+otst,y+g.getFontMetrics().getHeight()-3);
        g.drawString(diff,x+sizeMest+sizeTime+2*otst,y+g.getFontMetrics().getHeight()-3);
        g.drawString(text,x+sizeTime+3*otst+sizeMest+sizeMean,y+g.getFontMetrics().getHeight()-3);
        this.g = g;
    }

    public int getIconWidth() {
        String text = athlete.getFamilyName()+" "+athlete.getName();
        if(g==null){
            return fM.stringWidth(text)+fM.stringWidth("000 0:00:00 +000:00")+otst*2;
        }
        
        return g.getFontMetrics().stringWidth(text)+g.getFontMetrics().stringWidth("000 0:00:00 +000:00")+otst*2;
        
    }

    public int getIconHeight() {
        if(g==null){
            return fM.getHeight()+5;
        }
        return g.getFontMetrics().getHeight()+5;
    }
    
    
}
