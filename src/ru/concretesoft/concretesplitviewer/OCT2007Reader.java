/*
 * OCT2007Reader.java
 *
 * Created on 08 October 2007, 19:52
 *
 */

package ru.concretesoft.concretesplitviewer;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Valeri Mytinski
 *
 * Reader of so called "OCT2007" format files.
 * OCT2007 has no formal specification at the moment.
 * St.Petersburg people just started to use it in september or october 2007 without any
 * agreement or preliminary discussions.
 * I write it on base of examination several such files.
 *
 * We count file as OCT2007 if it consists of series "athlete-patterns".
 * Athlete-pattern is three lines like these:
 *    1   206 МАВЧУН       ГЕОРГИЙ      2000 кмс  СПбГУ                   33:58    04:16   05:17   06:30   08:27   13:50   20:01   22:55   29:37   31:12   32:14
 *                                                                                 04:16   01:01   01:13   01:57   05:23   06:11   02:54   06:42   01:35   01:02
 * -------------------------------------------------------------------------------------------------------------------------------------------------------------
 *
 */
public class OCT2007Reader extends SplitReader{
    private File file;
    private FileInputStream fIS;
    
    private String all;
    private String nameOfComp;
    private Vector<String> groupsNames;
    private Vector<Group> allGroups;
    private String encoding = "CP1251";
    private int version = 0; // 0 - unknown version
    private String eventDescription = ""; // Event description if it presents in file
    // Number of points in a group = number of times in the first line for first athlete in a group
    private int numberOfPoints = 0;
    
    /**
     * Creates a new instance of OCT2007Reader
     *
     * @param file splits file
     * @throws java.io.IOException
     * @throws ru.concretesoft.concretesplitviewer.NotRightFormatException
     */
    public OCT2007Reader(File file)  throws IOException, NotRightFormatException {
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
        
        /*
         * Check if there are two "athlete-pattern" - one just after another -
         * in the file.
         * If yes, it seems OCT2007 format.
         * If no, we will not parse file.
         */
        String firstLineOfPattern = "[ \\t]*\\d+[ \\t]+\\d+[ \\t\\S]*([ \\t]+(\\d{1,2}:)?\\d\\d:\\d\\d)+[ \\t]*";
        String secondLineOfPattern = "([ \\t]+(\\d{1,2}:)?\\d\\d:\\d\\d)+[ \\t]*";
        String thirdLineOfPattern = "---+[ \\t]*";
        Pattern hyphenPattern = Pattern.compile(thirdLineOfPattern + "\\r\\n");
        Matcher hyphenMatcher = hyphenPattern.matcher(all);
        Pattern athletePattern = Pattern.compile(firstLineOfPattern + "\\r\\n" + secondLineOfPattern + "\\r\\n" + thirdLineOfPattern + "\\r\\n");
        Matcher athleteMatcher = athletePattern.matcher(all);
        if (!hyphenMatcher.find()) {
            throw new NotRightFormatException(file, "OCT2007", "it not contains line of hyphens");
        } else {
            if (!athleteMatcher.find()) {
                throw new NotRightFormatException(file, "OCT2007", "it not contains athlete pattern");
            } else {
                if (!athleteMatcher.find(athleteMatcher.end())) {
                    throw new NotRightFormatException(file, "OCT2007", "it not contains two sequential athlete pattern");
                }
            }
        }
            
        /*
         * OK. It seems OCT2007 format.
         * Try to parse it.
         */
            Pattern groupPattern = Pattern.compile("^\\s+([^-\\s:]+)\\s*(\\r\\n)+", Pattern.MULTILINE);
            Matcher groupMatcher = groupPattern.matcher(all);
            
            groupsNames = new Vector<String>();
            allGroups = new Vector<Group>();
            
        /*
         * Find group pattern, find next group pattern (or end of text)
         * Then find all athlete patterns between them.
         */
            int groupPatternIndex = 0;
            while (groupMatcher.find(groupPatternIndex)) {
                // New group begins
                allGroups.add(new Group());
                // Store it's name
                String groupName = groupMatcher.group(1);
                groupsNames.add(groupName);
                allGroups.lastElement().setName(groupName);
                
                // Parse athletes in group
                int curIndex = groupMatcher.start();
                int endIndex = 0;
                if (groupMatcher.find(groupMatcher.end())) {
                    endIndex = groupMatcher.start();
                } else {
                    endIndex = all.length() - 1;
                }
                
                numberOfPoints = 0;
                boolean athleteFinded = true;
                while ((curIndex < endIndex) && (athleteFinded)) {
                    athleteFinded = false;
                    if (athleteMatcher.find(curIndex)) {
                        curIndex = athleteMatcher.start();
                        if (curIndex < endIndex) {
                            // Parse athlete
                            parseAthlete(athleteMatcher.group(), allGroups.lastElement());
                            athleteFinded = true;
                            curIndex = athleteMatcher.end();
                        }
                    }
                }
                
                //Remove group if it doesn't have athletes
                if(allGroups.lastElement().getAthletes().size() == 0){
                    allGroups.remove(allGroups.lastElement());
                    groupsNames.remove(groupsNames.lastElement());
                }else{
                    // Store group's distance
                    int groupDistance = 5000;
                    Distance d = new Distance(groupName, groupDistance, numberOfPoints);
                    allGroups.lastElement().setDistance(d);
                    d.setLengthsOfDists(Tools.calculatLengthsOfLaps(d.getGroups()));
                }
                
                groupPatternIndex = endIndex;
            }
        }
        
        /**
         * Parse string for athlete information.
         * Create new athlete and add it to group.
         *
         * @param s String - three lines - to parse
         * @param g Group for new athlete
         */
        private void parseAthlete(String s, Group g) {
            String[] lines = s.split("\\r\\n");
            
            // 1-st line is like
            //   1   206 МАВЧУН       ГЕОРГИЙ      2000 кмс  СПбГУ                   33:58    04:16   05:17   06:30   08:27   13:50   20:01   22:55   29:37   31:12   32:14
            // 2-nd line is like
            //                                                                                04:16   01:01   01:13   01:57   05:23   06:11   02:54   06:42   01:35   01:02
            // 3-d line is like
            // -------------------------------------------------------------------------------------------------------------------------------------------------------------
            
            // Catch last and first names from 1-st line - 3-d and 4-th words.
            String[] words = lines[0].trim().split("\\s++");
            String lastName = words[2];
            String firstName = words[3];
            String athleteResult = "24:00:00";
            // Then find first word that matches time pattern - it is the result time
            int firstTimeIndex = 3;
            while (firstTimeIndex < words.length) {
                if (words[firstTimeIndex].matches("(\\d{1,2}:)?\\d\\d:\\d\\d")) {
                    athleteResult = words[firstTimeIndex];
                    break;
                }
                firstTimeIndex++;
            }
            // Set or check number of points
            if (numberOfPoints == 0) {
                // This is first athlete in the group. Set number of points
                numberOfPoints = words.length - firstTimeIndex;
            } else {
                // Check number of points for all others athlete
                if ((words.length - firstTimeIndex) != numberOfPoints) {
                    // Possible reasons: DSQ, ...
                    return;
                }
            }
        /*
         * This crazy format does not contain finish split time.
         * We must calculate it.
         * We are finding last word - time on pre-finish point.
         * Then subtract it from finish time, result is finish split time.
         */
            String preFinishTime = words[words.length -1];
            String finishSplit = secondsToString(stringToSeconds(athleteResult) - stringToSeconds(preFinishTime));
            
            // Now catch split times from third line
            String[] splits = lines[1].trim().split("\\s++");
            // Create array of all splits
            Time[] athleteSplits = new Time[splits.length + 1];
            // Fill array with splits...
            for (int i = 0; i < splits.length; i++) {
                String[] fields = splits[i].split(":");
                athleteSplits[i] = new Time(splits[i], fields.length);
            }
            // ...and with finish split
            String[] fields = finishSplit.split(":");
            athleteSplits[athleteSplits.length - 1] = new Time(finishSplit, fields.length);
            
            // U-u-ph
            Athlete a = new Athlete(lastName, firstName,
                    athleteSplits, allGroups.lastElement(),
                    1900,
                    athleteResult);
        }
        
        private int stringToSeconds(String s) {
            String[] hhmmss = s.trim().split(":");
            int seconds = 0;
            for (int i = 0; i < hhmmss.length; i++) {
                seconds = 60 * seconds + Integer.parseInt(hhmmss[i]);
            }
            return seconds;
        }
        
        private String secondsToString(int seconds) {
            int h,m,s;
            s = seconds;
            h = s / 3600;
            s = s - 3600*h;
            m = s / 60;
            s = s - 60*m;
            
            return (h>0?String.format("%02d:",h):"")+String.format("%02d:",m)+String.format("%02d",s);
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
