package com.phung.clothshop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

public class DateFormat {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public static String format(Date date) {
        return simpleDateFormat.format(date);
    }

    public static Date parse(String string) throws ParseException {
        return simpleDateFormat.parse(string);
    }

}
