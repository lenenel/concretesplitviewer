/*
 * Time.java
 *
 * Created on 28 ������ 2006 �., 23:04
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package ru.concretesoft.concretesplitviewer;

/**
 *
 * @author ��������� ������
 *
 * ����� ��� ������ �� ��������.
 */
public class Time implements Comparable{
    private String timeS;
    private int time,fields;
    /** Creates a new instance of Time 
     * time - ����� � ������� ������ ���� ����, � ����������� �� ���������� �����
     * fields - ���������� �����
     */
    public Time(int time, int fields) {
        
        setFields(fields);
        setTime(time);
        
        
    }
    
     /** Creates a new instance of Time 
     * @param  s  ����� � ������� ��:��:�� ���� ��:��, � ����������� �� ���������� �����
     * @param  fields  ���������� �����
     */
    public Time(String s,int f){
//        this.timeS = s;
        s = s.replaceAll("\\s+","");
        setFields(f);
//        timeS="";
        String[] tmpS = s.split(":");
        int length  = tmpS.length;
//        if(tmpS.length > fields) length = fields;
        
        for(int i=length-1; i>0;i--){
            
            if(!tmpS[i].equals("")){
                
                    int tInt = (new Integer(tmpS[i])).intValue();
                    tInt = Math.abs(tInt);
                    while(tInt>=60){
                        int tmpSi1 = (new Integer(tmpS[i-1])).intValue();
                        if(tmpSi1>=0)
                            tmpS[i-1]=(tmpSi1+1)+"";
                        else
                            tmpS[i-1]=(tmpSi1-1)+"";
                        tInt-=60;
                    }
                    tmpS[i]=tInt+"";
                    while(tmpS[i].length()<2)tmpS[i]="0"+tmpS[i];
                
            }
            else tmpS[i]="00";
            
//            temp+=tmpS[i];
        }
        int temp=0;
        
        for(int i=0;i<length;i++){
            temp+=(Math.abs(new Integer(tmpS[i])))*Math.pow(60,length-i-1);
        }
        if((new Integer(tmpS[0])).intValue()<0)
            temp = -temp;
        else;
        setTimeInSeconds(temp);
        
//        for(int i=0;i<fields;i++){
//            String temp
//            timeS+=tmpS[i];
//        }
//        time=0;
//        for(int i=0;i<fields;i++){
//            time+=(new Integer(tmpS[i])).intValue()*(int)(Math.pow(100,fields-i-1));
//        }
    }
    public boolean equals(Time t){
        return (time == t.getTimeInSeconds());
    }
    /**
     * ��������� �����
     * time - ����� � ������� ������, ���� ���� �������������� � ����������� �� �������������� ���������� �����
     */
    public void setTime(int time){
        java.util.Vector<Integer> tmp = new java.util.Vector<Integer>();
        int i=0;
        int timetmp=time;
        while(timetmp>0){
            i++;
            
            int tmpInt = timetmp % 100;
            timetmp/=100;
            tmp.add(tmpInt);
            
        }
        if(i<fields)
            for(int j=0;j<fields-i;j++){
                tmp.add(tmp.size(),0);
            }
        if(i>fields)
            for(int j=i-1;j>=fields;j--){
                tmp.set(j-1,tmp.get(j)*60+tmp.get(j-1));
                tmp.remove(j);
            }
        for(int j=0;j<fields-1;j++){
            while(tmp.get(j)>60){
                tmp.set(j, tmp.get(j)-60);
                tmp.set(j+1, tmp.get(j+1)+1);
            }
        }
        timeS="";
        this.time=0;
        for(int j=fields-1;j>=0;j--){
            String temp = tmp.get(j)+"";
            if(temp.length()<2)temp="0"+temp;
            this.time += tmp.get(j)*(int)Math.pow(60,j);
            timeS+=temp+":";
        }
        byte[] bs = timeS.getBytes();
        timeS=new String(bs,0,bs.length-1);
    }
    /**
     * ��������� ����� � ��������
     * t - ����� � ��������
     */
    public void setTimeInSeconds(int t){
        this.time = t;
        int ed = 1;
        if(t < 0){
            t = -t;
            ed = -1;
        }
        else;
        String temp = "";
        if(fields==2){
             String tmpS = (t / 60) +"";
             temp+=tmpS +":";
             
             tmpS = (t  % 60)+"";
             if(tmpS.length()==1) tmpS = "0"+tmpS;
             else;
             temp+=tmpS;
             if(ed<0){
                temp = "-"+temp;
             }
             else;
        }else if(fields==3){
            String tmpS = (t / 3600) +"";
            temp+=tmpS +":";

            tmpS = ((t / 60) % 60)+"";
            if(tmpS.length()==1) tmpS = "0"+tmpS;
            temp+=tmpS +":";

             tmpS = (t  % 60)+"";
             if(tmpS.length()==1) tmpS = "0"+tmpS;
             temp+=tmpS;
             if(ed<0){
                temp = "-"+temp;
             }
             else;
        }
        else;
        this.timeS = temp;
    }
    /**
     * ��������� ���������� �����
     * f - ���������� �����
     */
    private void setFields(int f){
        if(f>=3)f = 3;
        else f = 2;
        this.fields = f;
    }
     /**
     * ��������� ����� �� t
     */
    public void addTime(Time t){
        setTimeInSeconds(time+t.getTimeInSeconds());
       
        
    }
     /**
     * ����� ���������� ������ ���������� ����� � ������� ��:��:��, ���� ��:��
     */
    public String getTimeString(){
        return timeS;
    }
    /**
     * ����� ���������� ����� � ��������
     */
    public int getTimeInSeconds(){
       
        return time;
    }
    
    public int compareTo(Object o) {
        try{
            Time t = (Time)o;
            if(t.getTimeInSeconds()>time) return -1;
            else 
                if(t.getTimeInSeconds()<time) return 1;
                    else return 0;
        }
        catch(ClassCastException e){
            
        }
        return 0;
    }
   
        
    
    
}
