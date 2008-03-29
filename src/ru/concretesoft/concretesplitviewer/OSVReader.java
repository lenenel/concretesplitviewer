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
 * OSVReader.java
 *
 * Created on 27 ���� 2006 �., 19:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.concretesoft.concretesplitviewer;


import ru.concretesoft.concretesplitviewer.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Mytinski Leonid
 *
 * Reader of OSV format files
 */
public class OSVReader extends SplitReader{
    
    private final Boolean DEBUG = false;
    
    private File file;
    private FileInputStream fIS;
    
    private String all;
    private String nameOfComp;
    private Vector<String> groupsNames;
    private Vector<Group> allGroups;
    private String encoding = "CP1251";
    private int version = 0; // 0 - unknown version
    private String eventDescription = ""; // Event description if it presents in file
    
    /** Creates a new instance of OSVReader 
     * @param file splits file
     * @throws java.io.IOException 
     * @throws ru.concretesoft.concretesplitviewer.NotRightFormatException
     */
    public OSVReader(File file)  throws IOException, NotRightFormatException {
        this.file=file;
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
        String[] groups = all.split("#");
        
        if (isVersionOne(file, groups)) {
            // Already parsed by isVersionOne()
        } else {
            // Not 'Version 1'
            groupsNames = new Vector<String>();
            allGroups = new Vector<Group>();
            try{
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
                            } catch(java.lang.NumberFormatException e){
                                splits[k]=new Time(0,2);
                            } catch(java.lang.ArrayIndexOutOfBoundsException e){
                                splits[k]=new Time(0,2);
                            }
                        }
                        Athlete a = new Athlete(tmp2[0],tmp2[1],splits,allGroups.lastElement());

                    }
                }
            }catch(ArrayIndexOutOfBoundsException e){
                throw new NotRightFormatException(file, "OSV", " array index of bound.");
            }
            Iterator<Group> it = allGroups.iterator();
            while(it.hasNext()){
                Distance d = it.next().getDistance();
                if(d.getLengthOfDist(1) < 0){
                    d.setLengthsOfDists(Tools.calculatLengthsOfLaps(d.getGroups()));
                }
            }
        }
        
        if((groupsNames==null)||(groupsNames.size()==0)){
            throw new NotRightFormatException(file, "OSV", " unknown reason.");
        }
    }
    
    /**
     * Parse OSV-file as 'Version 1'
     * @param groups Strings that produced by splitting file with '#'
     * @return true if successful, false if any kind of error found
     */
    private boolean isVersionOne(File file, String[] groups) throws NotRightFormatException {
        if (groups.length <= 0){
            System.err.println("Please check are there records in file. May be it is empty.");
            return false;
        }
        String[] headerLines = groups[0].split("\n");
        if (headerLines.length <= 0) {
            System.out.println("It is not 'Version 1' OSV-file. No header lines.");
            return false;
        }
        
        Pattern p = Pattern.compile(".*Version[ \\t]+(1)\\s*");
        Matcher m = p.matcher(headerLines[0]);
        if (! m.matches()) {
            System.out.println("It is not 'Version 1' OSV-file. First line not contain 'Version 1'.");
            return false;
        }
        System.out.println("It seems 'Version 1' OSV-file.");
        if (headerLines.length < 5) {
            System.err.println("Bad 'Version 1' file format: header contains less than 5 lines.");
            return false;
        }
        /*
         * Parse header lines and find interesting descriptors
         */
        int nameStart = 0, nameEnd = 0, resultStart = 0, resultEnd = 0, splitStart = 0;
        boolean namePresented = false, resultPresented = false, splitPresented = false;
        Pattern pName = Pattern.compile(".*@NAME(.+),(.+)\\s*");
        Pattern pResult = Pattern.compile(".*@RESULT(.+),(.+)\\s*");
        Pattern pSplits = Pattern.compile(".*@SPLITS[ \\t]+(\\d+)\\s*");
        
        for (int i = 1; i < headerLines.length; i++) {
            Matcher mName = pName.matcher(headerLines[i]);
            if (mName.matches()) {
                nameStart = Integer.parseInt(mName.group(1).trim());
                nameEnd   = Integer.parseInt(mName.group(2).trim());
                nameEnd   = nameEnd + nameStart - 1;
                namePresented = true;
                continue;
            }
            Matcher mResult = pResult.matcher(headerLines[i]);
            if (mResult.matches()) {
                resultStart = Integer.parseInt(mResult.group(1).trim());
                resultEnd   = Integer.parseInt(mResult.group(2).trim());
                resultEnd   = resultEnd + resultStart - 1;
                resultPresented = true;
                continue;
            }
            Matcher mSplits = pSplits.matcher(headerLines[i]);
            if (mSplits.matches()) {
                splitStart = Integer.parseInt(mSplits.group(1));
                splitPresented = true;
                continue;
            }
            // Here is line that doesn't match NAME, nor RESULT, nor SPLITS.
            // May be it is event description.
            // So it should be used as Title for main window.
        }
        // Check if all interesting descriptors presented
        if (! namePresented) {
            System.err.println("Bad 'Version 1' file format: @NAME is not presented.");
            return false;
        }
        if (! resultPresented) {
            System.err.println("Bad 'Version 1' file format: @RESULT is not presented.");
            return false;
        }
        if (! splitPresented) {
            System.err.println("Bad 'Version 1' file format: @SPLITS is not presented.");
            return false;
        }
        eventDescription = headerLines[4].trim();
        
        // All interesting descriptors presented.
        // Parse.
        groupsNames = new Vector<String>();
        allGroups = new Vector<Group>();
        try{
            for(int i=1; i < groups.length; i++){
                allGroups.add(new Group());
                // Split all group lines
                String[] groupLines = groups[i].split("\\n");
                // Parse first line in group: find name, number of points and distance
                String groupName = "";
                int groupPoints = 0;
                int groupDistance = 0;
                Pattern pGroup = Pattern.compile("[ \\t]*([^\\s]+)[ \\t]*,[ \\t]*(\\d+).*,[ \\t]*(\\d+)\\.(\\d+).*\\s*");
                Matcher mGroup = pGroup.matcher(groupLines[0]);
                if (! mGroup.matches()) {
                    System.err.println("Bad 'Version 1' file format. Bad group description line: '"+groupLines[0]+"'");
                    return false;
                } else {
                }
                groupName = mGroup.group(1);
                groupPoints = Integer.parseInt(mGroup.group(2));
                groupDistance = 1000 * Integer.parseInt(mGroup.group(3)) + Integer.parseInt(mGroup.group(4));

                Distance d = new Distance(groupName, groupDistance, groupPoints + 1);

                // Find equal distance in other groups and store this fact
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
                groupsNames.add(groupName);
                allGroups.lastElement().setDistance(d);
                allGroups.lastElement().setName(groupName);

                if (DEBUG) System.out.println(groupName);
                
                // Parse each line in the group
                for (int j = 1; j < groupLines.length; j++) {
                    String athleteName = "";
                    String athleteResult = "";
                    Time[] athleteSplits = new Time[groupPoints + 1];

                    if (groupLines[j].length() < nameEnd) {
                        System.err.println("Bad 'Version 1' file format. Length of line: '"+groupLines[j]+"' is not enough for @NAME.");
                        return false;
                    } else {
                        athleteName = groupLines[j].substring(nameStart - 1, nameEnd);
                    }

                    if (groupLines[j].length() < resultEnd) {
                        System.err.println("Bad 'Version 1' file format. Length of line: '"+groupLines[j]+"' is not enough for @RESULT.");
                        return false;
                    } else {
                        athleteResult = groupLines[j].substring(resultStart - 1, resultEnd);
                    }

                    if (groupLines[j].length() < splitStart) {
                        System.err.println("Bad 'Version 1' file format. Length of line: '"+groupLines[j]+"' is not enough for @SPLITS.");
                        return false;
                    } else {
                        
                        if (DEBUG) System.out.println(groupLines[j]);
                        
                        String[] theSplits = groupLines[j].substring(splitStart - 1).trim().split("\\s+");
                        for(int k = 0; k < groupPoints + 1; k++){
                            try{
                                athleteSplits[k] = new Time(theSplits[k],2);
                            } catch(java.lang.NumberFormatException e){
                                athleteSplits[k] = new Time(0,2);
                            } catch(java.lang.ArrayIndexOutOfBoundsException e){
                                athleteSplits[k] = new Time(0,2);
                            }
                        }
                    }
                    String[] athleteNames = athleteName.split("[ \\t]+");
                    Athlete a = new Athlete((athleteNames.length>0?(athleteNames[0].trim()):""),
                            (athleteNames.length>1?(athleteNames[1].trim()):""),
                            athleteSplits, allGroups.lastElement(),
                            1900,
                            athleteResult);
                }
            }
        }catch(ArrayIndexOutOfBoundsException e){
            return false;
        } catch(NumberFormatException nfe) {
            throw new NotRightFormatException(file, "OSV version 1", "bad number format: " + nfe.getMessage());
        }
        Iterator<Group> it = allGroups.iterator();
        while(it.hasNext()){
            Distance d = it.next().getDistance();
            if(d.getLengthOfDist(1) < 0){
                d.setLengthsOfDists(Tools.calculatLengthsOfLaps(d.getGroups()));
            }
        }
        
        return true;
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
    
    public String getFileName() {
        return file.getName();
    }
    
    public String getEventDescription() {
        return eventDescription;
    }
    
}
