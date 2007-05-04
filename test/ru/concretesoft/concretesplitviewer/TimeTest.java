/*
 * TimeTest.java
 * JUnit based test
 *
 * Created on 27 Èþíü 2006 ã., 17:31
 */

package ru.concretesoft.concretesplitviewer;

import junit.framework.*;

/**
 *
 * @author lene
 */
public class TimeTest extends TestCase {
    
    public TimeTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(TimeTest.class);
        
        return suite;
    }

    public void testSetTimeInSeconds() {
        System.out.println("setTimeInSeconds");
        
        int t = 123456;
        Time instance = new Time(0,3);
        
        instance.setTimeInSeconds(t);
        if(!instance.getTimeString().equals("34:17:36")){
            fail(instance.getTimeString()+" not equals with: 34:17:36");
        }
        t = 1234567;
        instance.setTimeInSeconds(t);
         if(!instance.getTimeString().equals("342:56:07")){
            fail(instance.getTimeString()+" not equals with: 342:56:07");
        }
       
    }

   

    public void testAddTime() {
        System.out.println("addTime");
        
        Time t = new Time("12:34:56",3);
        Time instance =new Time(123456,3);
        
        instance.addTime(t);
        
        String expResult = "25:09:52";
        String result = instance.getTimeString();
        assertEquals(expResult, result);
        
        t = new Time("12:34",2);
        instance =new Time(1234,2);
        instance.addTime(t);
        
        expResult = "25:08";
        result = instance.getTimeString();
        assertEquals(expResult, result);
        
        t = new Time("12:34:62",3);
        instance =new Time(1234,2);
        instance.addTime(t);
        
        expResult = "767:36";
        result = instance.getTimeString();
        assertEquals(expResult, result);
        
        t = new Time("12:34:62",3);
        instance =new Time(1234,2);
        t.addTime(instance);
        
        expResult = "12:47:36";
        result = t.getTimeString();
        assertEquals(expResult, result);
    }

    public void testGetTimeString() {
        System.out.println("getTimeString");
        
        Time instance = new Time("12:34:56",3);
        
        String expResult = "12:34:56";
        String result = instance.getTimeString();
        assertEquals(expResult, result);
        
        instance = new Time("12:34",2);
          
        expResult = "12:34";
        result = instance.getTimeString();
        assertEquals(expResult, result);
        
        instance = new Time("12:34:63",3);
          
        expResult = "12:35:03";
        result = instance.getTimeString();
        assertEquals(expResult, result);
        
        instance = new Time("12:65:63",3);
          
        expResult = "13:06:03";
        result = instance.getTimeString();
        assertEquals(expResult, result);
        
         instance = new Time("61:65:63",3);
          
        expResult = "62:06:03";
        result = instance.getTimeString();
        assertEquals(expResult, result);
        
        instance = new Time("12:34:56",2);
          
        expResult = "754:56";
        result = instance.getTimeString();
        assertEquals(expResult, result);
        
        instance = new Time("0:56",2);
          
        expResult = "0:56";
        result = instance.getTimeString();
        assertEquals(expResult, result);
        
        
    }



    

    

   
    
}
