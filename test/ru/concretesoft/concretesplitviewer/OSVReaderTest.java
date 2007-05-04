/*
 * OSVReaderTest.java
 * JUnit based test
 *
 * Created on 27 »˛Ì¸ 2006 „., 21:20
 */

package ru.concretesoft.concretesplitviewer;

import junit.framework.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author lene
 */
public class OSVReaderTest extends TestCase {
    
    public OSVReaderTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(OSVReaderTest.class);
        
        return suite;
    }

    public void testGetGroupsNames() throws IOException {
        OSVReader oR = new OSVReader(new File("1.osv"));
        Vector<String> gr= oR.getGroupsNames();
        int expL = 39;
        int realL = gr.size();
        assertEquals(expL,realL);
        
        String expFirst = "Ã21≈";
        String realFirst = gr.get(0);
        assertEquals(expFirst,realFirst);
        
        String expSec = "∆21≈";
        String realSec = gr.get(1);
        assertEquals(expSec,realSec);
        
        expSec = "∆21¡";
        realSec = gr.get(38);
        assertEquals(expSec,realSec);
    }

    public void testGetAllGroups() throws IOException {
        OSVReader oR = new OSVReader(new File("1.osv"));
        Vector<Group> gr = oR.getAllGroups();
        Group first = gr.get(0);
        Athlete fA = first.getAthlete(1);
        
        String expFirst = "¡Œ–“Õ» ";
        String realFirst = fA.getFamilyName();
        assertEquals(expFirst,realFirst);
       
        fA = first.getAthlete(19);
        String expSec = "Ã≈–≈Õ÷Œ¬";
        String realSec = fA.getFamilyName();
        assertEquals(expSec,realSec);
        
        first = gr.get(13);
        expFirst = "Ã40";
        realFirst = first.getName();
        assertEquals(expFirst,realFirst);
        fA = first.getAthlete(3);
        expFirst = "Ã≈À‹Õ» Œ¬";
        realFirst = fA.getFamilyName();
        assertEquals(expFirst,realFirst);
    }

    public void testGetGroup() throws IOException {
        OSVReader oR = new OSVReader(new File("1.osv"));
        Group gr = oR.getGroup("Ã21≈");
        
        Athlete fA = gr.getAthlete(1);
        String expFirst = "¡Œ–“Õ» ";
        String realFirst = fA.getFamilyName();
        assertEquals(expFirst,realFirst);
        
        fA = gr.getAthlete(19);
        expFirst = "Ã≈–≈Õ÷Œ¬";
        realFirst = fA.getFamilyName();
        assertEquals(expFirst,realFirst);
        
        gr = oR.getGroup("Ã40");
        
        fA = gr.getAthlete(3);
        expFirst = "Ã≈À‹Õ» Œ¬";
        realFirst = fA.getFamilyName();
        assertEquals(expFirst,realFirst);
        
         gr = oR.getGroup(13);
        
        fA = gr.getAthlete(3);
        expFirst = "Ã≈À‹Õ» Œ¬";
        realFirst = fA.getFamilyName();
        assertEquals(expFirst,realFirst);
        
        Distance d = gr.getDistance();
        expFirst = "Ã40 ∆21¿";
        realFirst = d.getName();
        assertEquals(expFirst,realFirst);
        
        int l = 5440;
        int rL = d.getLength();
        assertEquals(l,rL);
        
        l = 13;
        rL = d.getNumberOfCP();
        assertEquals(l,rL);
    }

    public void testGetGroupsByDist() throws IOException {
        OSVReader oR = new OSVReader(new File("1.osv"));
        
    }
    
}
