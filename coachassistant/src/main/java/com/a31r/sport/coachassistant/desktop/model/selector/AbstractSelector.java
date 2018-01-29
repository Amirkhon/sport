package com.a31r.sport.coachassistant.desktop.model.selector;

import com.a31r.sport.coachassistant.core.model.AbstractEntity;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
public abstract class AbstractSelector<T extends AbstractEntity> implements Selector<T> {

    private final ComboBox<T> comboBox = new ComboBox<>();
    private final Button select = new Button("Ок");
    private final Button cancel = new Button("Отмена");
    private final VBox view;
    private final Scene scene;

    public AbstractSelector() {
        view = new VBox(comboBox, new ToolBar(select, cancel));
        scene = new Scene(view);
    }

    @Override
    public void openDialog(Handler<T> handler) {
        Stage stage = new Stage();
        stage.setScene(scene);
        setupComboBox();
        select.setOnAction(event -> {
            handler.onSelect(comboBox.getValue());
            stage.close();
        });
        cancel.setOnAction(event -> stage.close());
        stage.show();
    }

    private void setupComboBox() {
        comboBox.setItems(FXCollections.observableArrayList(getItems()));
        comboBox.getSelectionModel().selectFirst();
    }

    @Override
    public Parent getView(Handler<T> handler) {
        setupComboBox();
        select.setOnAction(event -> handler.onSelect(comboBox.getValue()));
        return view;
    }

    @Override
    public ComboBox<T> getComboBox() {
        setupComboBox();
        return comboBox;
    }

    abstract List<T> getItems();
}