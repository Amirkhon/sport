package com.a31r.sport.coachassistant.desktop.view.util;

import java.time.format.DateTimeFormatter;

/**
 * Created by bahodurova on 1/17/2018.
 */
public class DateTimeUtil {
    private DateTimeUtil(){}

    public static DateTimeFormatter getDefaultFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

}
