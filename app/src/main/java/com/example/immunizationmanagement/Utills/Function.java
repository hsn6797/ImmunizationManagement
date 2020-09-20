package com.example.immunizationmanagement.Utills;

import android.util.Log;

import com.example.immunizationmanagement.Model.Gender;
import com.example.immunizationmanagement.Model.Status;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Function {

    public static long DateToTimeStamp(int day,int month, int year){

        Calendar c = Calendar.getInstance();
        c.set(year,month,day);
        Date d = c.getTime();

        Timestamp ts = new Timestamp(d.getTime());
        return ts.getTime();
    }
    public static long DateTimeToTimeStamp(int day,int month, int year,int Hour, int Min){

        Calendar c = Calendar.getInstance();
        c.set(year,month,day,Hour,Min);
//        Date d = c.getTime();
//
//        Timestamp ts = new Timestamp(d.getTime());
//        return ts.getTime();
        return c.getTimeInMillis();
    }
    public static String timestampToDTString(long date){
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        return dateFormat.format(date);

//        Timestamp ts = new Timestamp(d.getTime());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Log.d("Time: ", sdf.format(ts));
    }
    public static String timestampToString(long date){
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(date);

//        Timestamp ts = new Timestamp(d.getTime());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Log.d("Time: ", sdf.format(ts));
    }
    public static Date timestampToDate(long date){
        return new Date(date);
    }

    public static boolean isDateTimeIsNow(long date) {
        Calendar c = Calendar.getInstance();
        int Year = c.get(Calendar.YEAR);
        int Month = c.get(Calendar.MONTH);
        int Day = c.get(Calendar.DAY_OF_MONTH);
        final int Hour = c.get(Calendar.HOUR_OF_DAY);
        final int Min = c.get(Calendar.MINUTE);

        Log.d("-----------> ", String.valueOf(date));

        long currentDate = DateTimeToTimeStamp(Day,Month,Year,Hour,Min);
        Log.d("-----------> ", Function.timestampToDTString(date));
//        Log.d("-----------> ", Function.timestampToDTString(currentDate));
        Date comparingDate = timestampToDate(date);
        Log.d("-----------> ",
                comparingDate.getYear()
                +" - "+ comparingDate.getMonth()
                +" - "+ comparingDate.getDate()
                +" - "+ comparingDate.getHours()
                +" - "+ comparingDate.getMinutes()
        );
        if(
//                Year == comparingDate.getYear() &&
                Month == comparingDate.getMonth() &&
                Day == comparingDate.getDate() &&
                Hour == comparingDate.getHours() &&
                Min == comparingDate.getMinutes()){

            Log.d("----------->"," Done -> " + Function.timestampToDTString(currentDate));
            return true;
        }
       return false;
    }
    public static boolean compareDate(long date,int beforeDays){

        try{
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date dLastUpdateDate = dateFormat.parse(dateFormat.format(new Date(date)));
            Date dCurrentDate = dateFormat.parse(dateFormat.format(new Date()));


            Calendar cal = Calendar.getInstance();
            cal.setTime(dLastUpdateDate);
            cal.add(Calendar.DATE, -beforeDays);
            Date dateBefore_Days = cal.getTime();

            if (dCurrentDate.equals(dateBefore_Days))
            {
                Log.d("TAG", ""+ dLastUpdateDate);
                Log.d("TAG", ""+ dCurrentDate);
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public static Gender checkGender(String g){
        if(g.equalsIgnoreCase("M")){
            return Gender.M;
        }else{
            return Gender.F;
        }

    }
    public static Status checkStatus(String s){
        if(s.equalsIgnoreCase("P")){
            return Status.P;
        }else if(s.equalsIgnoreCase("S")){
            return Status.S;
        }
        else{
            return Status.D;
        }

    }

}
