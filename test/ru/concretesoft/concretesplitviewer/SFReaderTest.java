/*
 * SFReaderTest.java
 * JUnit based test
 *
 * Created on 3 Июль 2006 г., 21:08
 */

package ru.concretesoft.concretesplitviewer;

import junit.framework.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author lene
 */
public class SFReaderTest extends TestCase {
    
    public SFReaderTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(SFReaderTest.class);
        
        return suite;
    }

    public void testGetGroupsNames() throws FileNotFoundException, IOException {
        SFReader oR = new SFReader(new File("/home/lene/060702_split.txt"));
        Vector<String> gr= oR.getGroupsNames();
        int expL = 23;
        int realL = gr.size();
        assertEquals(expL,realL);
        
        String expFirst = "Ж10";
        String realFirst = gr.get(0);
        assertEquals(expFirst,realFirst);
//        
        String expSec = "М21А";
        String realSec = gr.get(15);
        assertEquals(expSec,realSec);
//        
        expSec = "МЖ7";
        realSec = gr.get(22);
        assertEquals(expSec,realSec);
    }

    public void testGetAllGroups() {
    }

    public void testGetGroup() {
        
        
    }

    public void testGetGroupsByDist() {
    }
    
}
