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
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
            } else
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
        // This is WHITE background for whole rectangle in all cases
        curColor = Color.WHITE;
        g.setColor(curColor);
        g.fillRect(x,y,getIconWidth(),getIconHeight());
        // Then border for selected
        if(selected){
            // Outer "color" rectangle
            curColor = color;
            g.setColor(curColor);
            g.fillRoundRect(x,y,getIconWidth()+4,getIconHeight(),6,6);
            // Fill inner rectangle with gradient color-to-white from bottom to top
            Graphics2D g2 = (Graphics2D)g;
            GradientPaint colorToWhite = new GradientPaint(x+4,y+getIconHeight(),color,x+4,y+4,Color.white);
            g2.setPaint(colorToWhite);
            g2.fillRect(x+2,y+2,getIconWidth(),getIconHeight()-4);
        }
        // Then text
        foreground = Color.BLACK;
        g.setColor(foreground);
        // Use one base line for all elements
        int yPosition = y + g.getFontMetrics().getHeight() - 0;
        // Right horizontal alligment for "position", "time" and "diff" with deltaX
        int deltaX = sizeMest - g.getFontMetrics().stringWidth(position+"");
        g.drawString(position+"", 2 + x + deltaX , yPosition);
        deltaX = sizeTime - g.getFontMetrics().stringWidth(time);
        g.drawString(time, 2 + x + sizeMest + otst + deltaX, yPosition);
        deltaX = sizeMean - g.getFontMetrics().stringWidth(diff);
        g.drawString(diff, 2 + x + sizeMest + sizeTime + 2*otst + deltaX, yPosition);
        g.drawString(text, 2 + x+sizeTime+3*otst+sizeMest+sizeMean, yPosition);
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
