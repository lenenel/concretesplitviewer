/*
 * OSVReaderTest.java
 * JUnit based test
 *
 * Created on 27 ���� 2006 �., 21:20
 */

package ru.concretesoft.concretesplitviewer;

import ru.concretesoft.concretesplitviewer.*;
import ru.concretesoft.concretesplitviewer.OSVReader;
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
        
        String expFirst = "�21�";
        String realFirst = gr.get(0);
        assertEquals(expFirst,realFirst);
        
        String expSec = "�21�";
        String realSec = gr.get(1);
        assertEquals(expSec,realSec);
        
        expSec = "�21�";
        realSec = gr.get(38);
        assertEquals(expSec,realSec);
    }

    public void testGetAllGroups() throws IOException {
        OSVReader oR = new OSVReader(new File("1.osv"));
        Vector<Group> gr = oR.getAllGroups();
        Group first = gr.get(0);
        Athlete fA = first.getAthlete(1);
        
        String expFirst = "�������";
        String realFirst = fA.getFamilyName();
        assertEquals(expFirst,realFirst);
       
        fA = first.getAthlete(19);
        String expSec = "��������";
        String realSec = fA.getFamilyName();
        assertEquals(expSec,realSec);
        
        first = gr.get(13);
        expFirst = "�40";
        realFirst = first.getName();
        assertEquals(expFirst,realFirst);
        fA = first.getAthlete(3);
        expFirst = "���������";
        realFirst = fA.getFamilyName();
        assertEquals(expFirst,realFirst);
    }

    public void testGetGroup() throws IOException {
        OSVReader oR = new OSVReader(new File("1.osv"));
        Group gr = oR.getGroup("�21�");
        
        Athlete fA = gr.getAthlete(1);
        String expFirst = "�������";
        String realFirst = fA.getFamilyName();
        assertEquals(expFirst,realFirst);
        
        fA = gr.getAthlete(19);
        expFirst = "��������";
        realFirst = fA.getFamilyName();
        assertEquals(expFirst,realFirst);
        
        gr = oR.getGroup("�40");
        
        fA = gr.getAthlete(3);
        expFirst = "���������";
        realFirst = fA.getFamilyName();
        assertEquals(expFirst,realFirst);
        
         gr = oR.getGroup(13);
        
        fA = gr.getAthlete(3);
        expFirst = "���������";
        realFirst = fA.getFamilyName();
        assertEquals(expFirst,realFirst);
        
        Distance d = gr.getDistance();
        expFirst = "�40 �21�";
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
