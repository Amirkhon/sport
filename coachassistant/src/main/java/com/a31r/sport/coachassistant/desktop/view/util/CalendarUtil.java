package com.a31r.sport.coachassistant.desktop.view.util;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;

import java.util.List;

/**
 * Created by bahodurova on 1/17/2018.
 */
public class CalendarUtil {

    private CalendarUtil(){}

    public static VBox getView(List<Agenda.Appointment> appointments) {
        VCalendar calendar = new VCalendar();
        ICalendarAgenda agenda = new ICalendarAgenda(calendar);
//        agenda.setLocale(new Locale("ru"));
//        agenda.appointments().addAll(appointments);
        agenda.getCategories().clear();
        agenda.getCategories().addAll("Group 1");
        Button button = new Button("Export");
        button.setOnAction(event -> {
            calendar.toString();
            agenda.toString();
        });
        return new VBox(agenda, button);
    }

}
