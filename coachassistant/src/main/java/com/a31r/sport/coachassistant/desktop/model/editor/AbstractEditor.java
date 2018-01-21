package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.AbstractEntity;
import com.a31r.sport.coachassistant.core.model.service.DataService;
import com.a31r.sport.coachassistant.desktop.model.Visible;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

/**
 * Created by bahodurova on 1/11/2018.
 */
public abstract class AbstractEditor<T extends AbstractEntity> implements Editor<T>, Visible {

    protected final BorderPane view = new BorderPane();
    protected final TabPane tabPane = new TabPane();
    protected final Tab mainTab = new Tab("Главная");
    protected final GridPane gridPane = new GridPane();
    protected final Button saveButton = new Button("Сохранить");
    protected final Button cancelButton = new Button("Отмена");
    protected final ToolBar toolBar = new ToolBar(saveButton, cancelButton);
    protected final BooleanProperty edit = new SimpleBooleanProperty(this, "edit", true);
    protected T object;
    protected Handler<T> handler;

    public AbstractEditor() {
        mainTab.setContent(gridPane);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        tabPane.getTabs().add(mainTab);
        edit.addListener((observable, oldValue, newValue) -> {
            setDisabled(!newValue);
            cancelButton.setDisable(!newValue);
            saveButton.setDisable(!newValue);
        });
        cancelButton.setOnAction(event -> cancel());
        saveButton.setOnAction(event -> save());
        view.setBottom(toolBar);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

//    @Override
//    public void setObject(T object) {
//        setObject(object, null);
//    }

    @Override
    public void setObject(T object, Handler<T> handler) {
        this.object = object;
        this.handler = handler;
        if (object.getId() != null) {
            getService().includeMembers(this.object);
            edit.set(false);
        }
        setData();
    }

    @Override
    public Parent show() {
        beforeShow();
        edit.set(false);
        return view();
    }

    @Override
    public void edit() {
        edit.set(true);
    }

    @Override
    public void save() {
        beforeSave();
        edit.set(false);
        if (handler != null) {
            object = handler.onSave(object);
        } else {
            object = getService().save(object);
        }
    }

    @Override
    public void cancel() {
        if (object.getId() != null) {
            setData();
        }
        edit.set(false);
        if (handler != null) {
            handler.onCancel();
        }
    }

    protected void addToListView(ListView listView, Object object) {
        listView.getItems().add(object);
        listView.refresh();
    }

    protected void removeFromListView(ListView listView, Object object) {
        listView.getItems().remove(object);
        listView.refresh();
    }

    protected void beforeShow() {}
    protected Parent view() {
        if (tabPane.getTabs().size() == 1) {
            view.setCenter(gridPane);
        } else {
            view.setCenter(tabPane);
        }
        return view;
    }

    protected abstract DataService<T> getService();
    protected abstract void beforeSave();
    protected abstract void setData();
    protected abstract void setDisabled(boolean disabled);
}