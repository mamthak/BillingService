package com.rightminds.biller.util;

import org.springframework.util.StringUtils;

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


}
