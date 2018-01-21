package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.User;
import com.a31r.sport.coachassistant.core.model.UserGroup;
import com.a31r.sport.coachassistant.desktop.model.selector.Selector;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Created by bahodurova on 1/17/2018.
 */
public abstract class AbstractUserGroupEditor<T extends UserGroup, U extends User> extends AbstractEditor<T> {

    protected final Tab membersTab = new Tab("Участники");
    protected final TextField name = new TextField();
    protected final ListView<User> members = new ListView<>();
    protected final Button add = new Button("Добавить");
    protected final Button remove = new Button("Удалить");

    public AbstractUserGroupEditor() {
        gridPane.add(new Label("Название"), 0, 0);
        gridPane.add(name, 1, 0);
        membersTab.setContent(new VBox(members, new ToolBar(add, remove)));
        tabPane.getTabs().add(membersTab);

        add.setOnAction(event ->
                getSelector().openDialog(selected -> {

                    object.getMembers().add(selected);
//                    if (object.getId() != null) {
//                        getService().save(object);
//                    }
                    addToListView(members, selected);
//                    members.getItems().add(selected);
//                    members.refresh();
                }));

        remove.setOnAction(event -> {
            User selected = members.getSelectionModel().getSelectedItem();
            if (selected != null) {
                object.getMembers().remove(selected);
//                if (object.getId() != null) {
//                    getService().save(object);
//                }
                removeFromListView(members, selected);
//                members.getItems().remove(selected);
//                members.refresh();
            }
        });
    }

    @Override
    protected void beforeSave() {
        object.setName(name.getText());
    }

    @Override
    protected void setData() {
        name.setText(object.getName());
        members.setItems(FXCollections.observableArrayList(object.getMembers()));
    }

    @Override
    protected void setDisabled(boolean disabled) {
        name.setDisable(disabled);
        add.setDisable(disabled);
        remove.setDisable(disabled);
        members.setDisable(disabled);
    }

    abstract Selector<U> getSelector();
}