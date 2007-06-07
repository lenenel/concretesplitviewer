/*
 * ToolsTest.java
 * JUnit based test
 *
 * Created on 15 ������ 2007 �., 20:34
 */

package ru.concretesoft.concretesplitviewer;

import java.io.File;
import java.io.IOException;
import junit.framework.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

/**
 *
 * @author lene
 */
public class ToolsTest extends TestCase {
    
    public ToolsTest(String testName) {
        super(testName);
    }

    public void testGetAnyBest() {
    }

    public void testGetMeanSpeedOnLap() {
        try {
            OSVReader oR = new OSVReader(new File("1.osv"));
            Group g = oR.getGroup(0);
            Athlete a = g.getAthlete(1);
            Distance d = g.getDistance();
            Vector<Group> gr = new Vector<Group>();
            gr.add(g);
            if(d.getLengthOfDist(1)<0){
                int [] lens = Tools.calculatLengthsOfLaps(gr);
                for(int i=0; i< lens.length; i++)
                    d.setLengthOfDist(i+1,lens[i]);
            }
            
            Time result = Tools.getMeanSpeedOnLap(d,a,1);
            Time exp = new Time(0,2);
            exp.setTimeInSeconds(315);
            int resInt = result.getTimeInSeconds();
            int expInt = exp.getTimeInSeconds();
            assertEquals(expInt, resInt);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void testGetMeanSpeed() {
        try {
            OSVReader oR = new OSVReader(new File("1.osv"));
            Group g = oR.getGroup(0);
            Athlete a = g.getAthlete(2);
            Distance d = g.getDistance();
            Vector<Group> gr = new Vector<Group>();
            gr.add(g);
            if(d.getLengthOfDist(1)<0){
                int [] lens = Tools.calculatLengthsOfLaps(gr);
                for(int i=0; i< lens.length; i++)
                    d.setLengthOfDist(i+1,lens[i]);
            }
            int[] numbers = new int[a.getNumberOfLaps()];
            for(int i=0; i<a.getNumberOfLaps(); i++){
                numbers[i] = i+1;
            }
            Time result = Tools.getMeanSpeed(d,a,numbers);
            Time exp = new Time(0,2);
            exp.setTimeInSeconds(315);
            int resInt = result.getTimeInSeconds();
            int expInt = exp.getTimeInSeconds();
            assertEquals(expInt, resInt);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void testGetComplexMeanSpeed_First() {
        try {
            OSVReader oR = new OSVReader(new File("1.osv"));
            Group g = oR.getGroup(0);
            Athlete a = g.getAthlete(2);
            Distance d = g.getDistance();
            Vector<Group> gr = new Vector<Group>();
            gr.add(g);
            if(d.getLengthOfDist(1)<0){
                int [] lens = Tools.calculatLengthsOfLaps(gr);
                for(int i=0; i< lens.length; i++)
                    d.setLengthOfDist(i+1,lens[i]);
            }
            int[] numbers = new int[a.getNumberOfLaps()];
            for(int i=0; i<a.getNumberOfLaps(); i++){
                numbers[i] = i+1;
            }
            Time result = Tools.getComplexMeanSpeed_First(d,a,numbers,0.1);
            Time exp = new Time(0,2);
            exp.setTimeInSeconds(315);
            int resInt = result.getTimeInSeconds();
            int expInt = exp.getTimeInSeconds();
            assertEquals(expInt, resInt);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void testGetComplexMeanSpeed() {
        try {
            OSVReader oR = new OSVReader(new File("1.osv"));
            Group g = oR.getGroup(0);
            Athlete a = g.getAthlete(2);
            Distance d = g.getDistance();
            Vector<Group> gr = new Vector<Group>();
            gr.add(g);
            if(d.getLengthOfDist(1)<0){
                int [] lens = Tools.calculatLengthsOfLaps(gr);
                for(int i=0; i< lens.length; i++)
                    d.setLengthOfDist(i+1,lens[i]);
            }
            int[] numbers = new int[a.getNumberOfLaps()];
            for(int i=0; i<a.getNumberOfLaps(); i++){
                numbers[i] = i+1;
            }
            Time result = Tools.getComplexMeanSpeed(d,a,numbers,0.1);
            Time exp = new Time(0,2);
            exp.setTimeInSeconds(315);
            int resInt = result.getTimeInSeconds();
            int expInt = exp.getTimeInSeconds();
            assertEquals(expInt, resInt);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
