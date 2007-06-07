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
    /** Creates a new instance of OSVReader */
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
        for(int i=1;i<groups.length;i++){
            allGroups.add(new Group());
            String[] tmp = groups[i].split(" ",2);
            groupsNames.add(tmp[0]);
            allGroups.lastElement().setName(tmp[0]);
            tmp = groups[i].split("\n");
            String[] tmp1 = tmp[0].split("\\s*,\\s*");
            int l = (int)((new Double(tmp1[2].split("\\s+")[0])).doubleValue()*1000);
            int nCP = (new Integer(tmp1[1].split("\\s+")[0])).intValue()+1;
            Distance d = new Distance(tmp1[0],l,nCP);
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
            for(int j=1;j<tmp.length;j++){
                String[] tmp2 = tmp[j].split("\\s+");
//                Time totTime = new Time(tmp2[2],3);
                Time[] splits = new Time[nCP];
                for(int k = 0; k<nCP;k++){
                    try{
                        splits[k]=new Time(tmp2[k+3],2);
                    }
                    catch(java.lang.NumberFormatException e){
                        splits[k]=new Time(0,2);
                    }
                    catch(java.lang.ArrayIndexOutOfBoundsException e){
                        splits[k]=new Time(0,2);
                    }
                }
                Athlete a = new Athlete(tmp2[0],tmp2[1],splits,allGroups.lastElement());

            }
        }
        
        Iterator<Group> it = allGroups.iterator();
        while(it.hasNext()){
            Distance d = it.next().getDistance();
            if(d.getLengthOfDist(1) < 0){
                d.setLengthsOfDists(Tools.calculatLengthsOfLaps(d.getGroups()));
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
