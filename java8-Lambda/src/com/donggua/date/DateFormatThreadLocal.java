package com.donggua.date;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by donggua on 2017/8/20.
 */
public class DateFormatThreadLocal {

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){

        protected DateFormat initialValue(){
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    public  static Date convert(String source) throws  Exception{
        return df.get().parse(source);
    }

 }
