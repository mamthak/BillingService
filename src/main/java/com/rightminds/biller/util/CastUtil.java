package com.rightminds.biller.util;

import liquibase.exception.DateParseException;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CastUtil {

    public static String getString(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static Integer getInteger(Object value) {
        String stringValue = getString(value);
        if (StringUtils.isEmpty(stringValue)) {
            return null;
        }
        return Integer.valueOf(stringValue);
    }

    public static Long getLong(Object value) {
        String stringValue = getString(value);
        if (StringUtils.isEmpty(stringValue)) {
            return null;
        }
        return Long.valueOf(stringValue);
    }

    public static BigDecimal getBigDecimal(Object value) {
        String stringValue = getString(value);
        if (StringUtils.isEmpty(stringValue)) {
            return null;
        }
        return new BigDecimal(stringValue);
    }

    public static boolean getBoolean(Object value) {
        String stringValue = getString(value);
        return !StringUtils.isEmpty(stringValue) && Boolean.parseBoolean(stringValue);
    }

    public static Date getDate(Object dateTimeString) {
        String stringValue = getString(dateTimeString);
        String pattern = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").getPattern();
        return parseDate(stringValue, pattern);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }

    public static Date parseDate(String dateString, String pattern) {
        if (isBlank(dateString)) {
            return null;
        }
        try {
            return DateUtils.parseDate(dateString, pattern);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
