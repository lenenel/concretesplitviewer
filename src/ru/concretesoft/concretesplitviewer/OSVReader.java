/*
 * OSVReader.java
 *
 * Created on 27 Июнь 2006 г., 19:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;


import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author Мытинский Леонид
 *
 * Класс для чтения OSV файлов
 */
public class OSVReader extends SplitReader{
    private File file;
    private FileInputStream fIS;
    
    private String all;
    private String nameOfComp;
    private Vector<String> groupsNames;
    private Vector<Group> allGroups;
    private String encoding = "CP1251";
    /** Creates a new instance of OSVReader
     *
     * @param  file  file in format "OSV"
     *
     *
     */
    public OSVReader(File file)  throws IOException {
        this.file=file;
        fIS = new FileInputStream(file);
        
        
        
        int length = (int)file.length();
        byte[] s = new byte[length];
       
           
            
           
        fIS.read(s);
        try{
            all=new String(s,encoding);
        }
        catch(UnsupportedEncodingException e){
            all = "";
        }
        String[] groups = all.split("#");
        groupsNames = new Vector<String>();
        allGroups = new Vector<Group>();
        //From 1 because the first item is begin of file
        for(int i=1;i<groups.length;i++){
            allGroups.add(new Group());
            
            //Find name of group
            String[] tmp = groups[i].split(" ",2);
            //Set name for group
            groupsNames.add(tmp[0]);
            allGroups.lastElement().setName(tmp[0]);
            
            
            tmp = groups[i].split("\n");
            
            //Find parameters of distance
            String[] tmp1 = tmp[0].split("\\s*,\\s*");
            int l = (int)((new Double(tmp1[2].split("\\s+")[0])).doubleValue()*1000); //Length of distance
            int nCP = (new Integer(tmp1[1].split("\\s+")[0])).intValue()+1; //Number of control points (+1 because finish lap)
            Distance d = new Distance(tmp1[0],l,nCP);
            
            //Find same distance (comparing parameters) in other groups
            Iterator<Group> it = allGroups.iterator();
            while(it.hasNext()){
                Distance dTmp = it.next().getDistance();
                if(dTmp == null) break;
                if(dTmp.equals(d)){
                    dTmp.setName(dTmp.getName()+" "+d.getName());
                    d=dTmp;
                    break;
                }
            }
            allGroups.lastElement().setDistance(d);
            
            //Parsing of one group
            for(int j=1;j<tmp.length;j++){
                String[] tmp2 = tmp[j].split("\\s+",3); //Name of athlete
                String[] tmp3 = tmp[j].split("\\d\\d:\\d\\d:\\d\\d");
                
//                Time totTime = new Time(tmp2[2],3);
                Time[] splits = new Time[nCP];
                for(int k = 0; k<nCP;k++){
                    try{
                        splits[k]=new Time(tmp3[1].substring(k*6,(k+1)*6),2);
                    }
                    catch(java.lang.NumberFormatException e){
                        splits=null;
                        break;
                    }
                    catch(java.lang.ArrayIndexOutOfBoundsException e){
                        splits=null;
                        break;
                    }
                    catch(java.lang.IndexOutOfBoundsException e){
                        splits=null;
                        break;
                    }
                }

                if(splits!=null)
                    new Athlete(tmp2[0],tmp2[1],splits,allGroups.lastElement());
                else;

            }
        }
        
        Iterator<Group> it = allGroups.iterator();
        while(it.hasNext()){
            Distance d = it.next().getDistance();
            if(d.getLengthOfDist(1) < 0){
                d.setLengthsOfDists(Tools.calculatLengthsOfLap(d.getGroups()));
            }
        }
    }

    public Vector<String> getGroupsNames() {
        return groupsNames;
    }

    public Vector<Group> getAllGroups() {
        return allGroups;
    }

    public Group getGroup(String name) {
        int index = groupsNames.indexOf(name);
        return allGroups.get(index);
    }

    public Group getGroup(int number) {
        return allGroups.get(number);
    }

    public Vector<Group> getGroupsByDist(int number) {
        return null;
    }
    
}
