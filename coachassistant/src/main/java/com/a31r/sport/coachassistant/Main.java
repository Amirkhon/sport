package com.a31r.sport.coachassistant;

import com.a31r.sport.coachassistant.core.model.*;
import com.a31r.sport.coachassistant.core.model.service.*;
import com.a31r.sport.coachassistant.desktop.view.component.ContentViewService;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by bahodurova on 1/12/2018.
 */
@SpringBootApplication
public class Main extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent root;

    public static void main(String[] args) {
        launch(Main.class, args);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(Main.class);
        AthleteService athleteService = springContext.getBean(AthleteService.class);
        if (athleteService.findAll().isEmpty()) {
            Athlete athlete = new Athlete("Имя", "Фамилия", "Отчество");
            athleteService.save(athlete);
        }
        CoachService coachService = springContext.getBean(CoachService.class);
        if (coachService.findAll().isEmpty()) {
            Coach coach = new Coach("Имя", "Фамилия", "Отчество");
            coachService.save(coach);
        }
        UserPropertyTypeService userPropertyTypeService = springContext.getBean(UserPropertyTypeService.class);
        if (userPropertyTypeService.findAll().isEmpty()) {
            UserPropertyType userPropertyType = new UserPropertyType("Телефон");
            userPropertyTypeService.save(userPropertyType);
        }
        ExerciseService exerciseService = springContext.getBean(ExerciseService.class);
        if (exerciseService.findAll().isEmpty()) {
            Exercise exercise = new Exercise("Отжимания", "к-во повторений");
            exerciseService.save(exercise);
        }
        AthleteParameterTypeService athleteParameterTypeService = springContext.getBean(AthleteParameterTypeService.class);
        if (athleteParameterTypeService.findAll().isEmpty()) {
            AthleteParameterType athleteParameterType = new AthleteParameterType("Вес", "кг");
            athleteParameterTypeService.save(athleteParameterType);
        }
        TrainingGroupService trainingGroupService = springContext.getBean(TrainingGroupService.class);
        if (trainingGroupService.findAll().isEmpty()) {
            TrainingGroup trainingGroup = new TrainingGroup("Основная группа");
            trainingGroupService.save(trainingGroup);
        }
        root = springContext.getBean(ContentViewService.class).getView();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(800);
        primaryStage.show();
    }
}