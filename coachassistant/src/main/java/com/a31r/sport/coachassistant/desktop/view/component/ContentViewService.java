package com.a31r.sport.coachassistant.desktop.view.component;

//import com.a31r.sport.coachassistant.desktop.view.util.CalendarUtil;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
//import jfxtras.scene.control.agenda.Agenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;

import static com.a31r.sport.coachassistant.desktop.view.component.ListViewService.ListType.*;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class ContentViewService implements ViewService {

    @Autowired
    private ListViewService listView;

    private final BorderPane view = new BorderPane();
    private final MenuBar menuBar = new MenuBar();

    public ContentViewService() {
        Menu lists = new Menu("Списки");
        MenuItem athleteList = new MenuItem(ATHLETES.getLabel());
        MenuItem coachList = new MenuItem(COACHES.getLabel());
        MenuItem trainingGroupList = new MenuItem(TRAINING_GROUPS.getLabel());
        MenuItem userPropertyTypeList = new MenuItem(USER_PROPERTY_TYPES.getLabel());
        MenuItem athleteParameterTypeList = new MenuItem(ATHLETE_PARAMETER_TYPES.getLabel());
        MenuItem exerciseList = new MenuItem(EXERCISES.getLabel());
        MenuItem trainingSessionList = new MenuItem(TRAINING_SESSIONS.getLabel());
        lists.getItems().addAll(athleteList, coachList, trainingGroupList, userPropertyTypeList,
                athleteParameterTypeList, exerciseList, trainingSessionList);

        for (MenuItem item : lists.getItems()) {
            item.setOnAction(event -> showListView(getByLabel(item.getText())));
        }
        /*Menu menu = new Menu("Меню");
        MenuItem calendar = new MenuItem("Календарь");
        calendar.setOnAction(event -> {
            // get calendar
            List<Agenda.Appointment> appointments = new ArrayList<>();
            appointments.add(new Agenda.AppointmentImplLocal()
                    .withStartLocalDateTime(LocalDate.now().atTime(4, 00))
                    .withEndLocalDateTime(LocalDate.now().atTime(15, 30))
                    .withDescription("It's time"));
            view.setCenter(CalendarUtil.getView(appointments));
        });
        menu.getItems().addAll(calendar);*/
        menuBar.getMenus().addAll(/*menu,*/ lists);
        view.setTop(menuBar);
    }

    private void showListView(ListViewService.ListType listType) {
        if (listType == null) {
            return;
        }
        view.setCenter(listView.getView());
        listView.setListType(listType);
    }

    @Override
    public Parent getView() {
        return view;
    }
}