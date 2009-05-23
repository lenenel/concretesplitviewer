/*
 * ConcreteSplitViewer program for analazing splits.
 * Copyright (C) 2006-2007 Mytinski Leonid (Leonid.Mytinski@gmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */ 

/*
 * SFReader.java
 *
 * Created on 3 ���� 2006 �., 19:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Mytinski Leonid
 *
 * Класс для чтения SFR файлов
 */
public class SFReader extends SplitReader{
    private File file;
    private FileInputStream fIS;
    
    private String all;
    
    private List<String> groupsNames;
    private List<Group> allGroups;
    private String encoding = "CP1251";
    private String eventDescription = ""; // Event description if it presents in file
    
    /** Creates a new instance of SFReader
     * @param f splits file
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws ru.concretesoft.concretesplitviewer.NotRightFormatException
     */
    public SFReader(File f) throws FileNotFoundException, IOException, NotRightFormatException {
        this.file=f;
        fIS = new FileInputStream(file);
        
        int length = (int)file.length();
        byte[] s = null;
        try {
            s = new byte[length];
        } catch (java.lang.OutOfMemoryError e) {
            throw new IOException("File too long to fit into memory.");
        }
        
        fIS.read(s);
        try{
            all=new String(s,encoding);
        } catch(UnsupportedEncodingException e){
            all = "";
        }
        
        groupsNames = new ArrayList<String>();
        allGroups = new ArrayList<Group>();
        String[] allLines = all.split("\n");
        Group g=null;
        String[] athleteAtr=null;
        Time[]  splits = null;
        Time    finishTime = null;
        int[] lengths = null;
        boolean dSQ=false;
        
        //for each line
        try{
            for(int i=0;i<allLines.length;i++){
                //If line have the first not blank symbol "H"(М) or "D"(Ж) then parse one group
                if(allLines[i].matches("\\s+["+java.util.ResourceBundle.getBundle("ru/concretesoft/concretesplitviewer/i18on").getString("H")+java.util.ResourceBundle.getBundle("ru/concretesoft/concretesplitviewer/i18on").getString("D")+"].*\r*")){
                    if((athleteAtr!=null)&&(!dSQ)){
                        new Athlete(athleteAtr[3], athleteAtr[4], splits, g, 1900, athleteAtr[athleteAtr.length - 1]);
                        
//                        if(allGroups.lastElement().getDistance().getLengthOfDist(1)<0){
//                            if(lengths!=null)
//                                for(int j=0;j<lengths.length;j++){
//                                allGroups.lastElement().getDistance().setLengthOfDist(j+1,lengths[j]);
//                                }
//                        }
                        athleteAtr=null;
                        splits = null;
                        lengths = null;
                        dSQ = false;
                    }
                    g = new Group();
                    String[] groupsAtr = allLines[i].split("\\s+");
                    g.setName(groupsAtr[1]);
                    Distance d = new Distance(groupsAtr[1],(new Integer(groupsAtr[2])).intValue(),(new Integer(groupsAtr[4])).intValue()+1);
                    g.setDistance(d);
                    allGroups.add(g);
                    groupsNames.add(groupsAtr[1]);
                    
                } else if(g!=null){
                    
                    
                    
                    if(allLines[i].matches("\\s*\\d+.*\r*")){
                        if((athleteAtr!=null)&&(!dSQ)){
                            new Athlete(athleteAtr[3], athleteAtr[4], splits, g, 1900, athleteAtr[athleteAtr.length - 1]);
//                            if(allGroups.lastElement().getDistance().getLengthOfDist(1)<0){
//                                if(lengths!=null)
//                                    for(int j=0;j<lengths.length;j++){
//                                        allGroups.lastElement().getDistance().setLengthOfDist(j+1,lengths[j]);
//                                    }
//                            }
                        }
                        
                        athleteAtr = allLines[i].split("\\s+");
                        if(allLines[i].matches(".*"+java.util.ResourceBundle.getBundle("ru/concretesoft/concretesplitviewer/i18on").getString("DSQ")+".*\r*")) {
                            dSQ = true;
                        } else {
                            dSQ = false;
                            finishTime = new Time(athleteAtr[athleteAtr.length - 1]);
                        }
                        
                    } else if(allLines[i].matches("\\s*split:.*\r*")){
                        String times = allLines[i].split("split: ")[1];
                        splits = new Time[g.getDistance().getNumberOfCP()];
                        for(int j=0;j<splits.length;j++){
                            
                            
                            try{
                                String tmp = times.substring(6*j,6*(j+1));
                                tmp = tmp.replaceAll("\\s+","");
                                splits[j] = new Time(tmp,2);
                            } catch(java.lang.NumberFormatException e){
                                splits[j]=new Time(0,2);
                            } catch(java.lang.ArrayIndexOutOfBoundsException e){
                                splits[j]=new Time(0,2);
                            } catch(java.lang.StringIndexOutOfBoundsException e){
                                splits[j]=new Time(1,2);
                            }
                        }
                    } else if(allLines[i].matches("\\s*speed:.*\r*")){
//                        String times = allLines[i].split("speed: ")[1];
//                        lengths = new int[g.getDistance().getNumberOfCP()];
//                        int totLen=0;
//                        for(int j=0;j<splits.length-1;j++){
//                            
//                            Time tmpTime;
//                            try{
//                                String tmp = times.substring(6*j,6*(j+1));
//                                tmp = tmp.replaceAll("\\s+","");
//                                tmpTime = new Time(tmp,2);
//                            } catch(java.lang.NumberFormatException e){
//                                tmpTime=new Time(1,2);
//                            } catch(java.lang.ArrayIndexOutOfBoundsException e){
//                                lengths = null;
//                                break;
//                            } catch(java.lang.StringIndexOutOfBoundsException e){
//                                lengths = null;
//                                break;
//                            }
//                            lengths[j] = (int)(((double)splits[j].getTimeInSeconds()/tmpTime.getTimeInSeconds())*1000);
//                            totLen+=lengths[j];
//                        }
//                        if(lengths!=null)
//                            lengths[splits.length-1]=g.getDistance().getLength()-totLen;
                    }
                    
                    
                    
                }
            }
            
            // Add last athlete in last group
            if((athleteAtr!=null)&&(!dSQ)){
                new Athlete(athleteAtr[3], athleteAtr[4], splits, g, 1900, athleteAtr[athleteAtr.length - 1]);
            }
            
            //Set lengths of laps
            Iterator<Group> it = allGroups.iterator();
            while(it.hasNext()){
                Distance d = it.next().getDistance();
                if(d.getLengthOfDist(1) < 0){
                    d.setLengthsOfDists(Tools.calculatLengthsOfLaps(d.getGroups()));
                }
            }
        }catch(ArrayIndexOutOfBoundsException e){
            throw new NotRightFormatException(f, "SFR", " array index of bounds");
        }
        if(groupsNames.size()==0){
            throw new NotRightFormatException(f, "SFR", "parser can't find group");
        }
        
    }
    
    public List<String> getGroupsNames() {
        return groupsNames;
    }
    
    public List<Group> getAllGroups() {
        return allGroups;
    }
    
    public Group getGroup(String name) {
        int index = groupsNames.indexOf(name);
        return allGroups.get(index);
    }
    
    public Group getGroup(int index) {
        return allGroups.get(index);
    }
    
    public List<Group> getGroupsByDist(int index) {
        return null;
    }
    
    public String getFileName() {
        return file.getName();
    }
    
    public String getEventDescription() {
        return eventDescription;
    }
}
