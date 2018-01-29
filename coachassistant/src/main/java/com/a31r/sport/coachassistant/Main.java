package com.a31r.sport.coachassistant;

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