package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.User;
import com.a31r.sport.coachassistant.core.model.UserGroup;
import com.a31r.sport.coachassistant.desktop.model.selector.Selector;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Set;

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
                    object.addMember(selected);
                    addToListView(members, selected);
                }));

        remove.setOnAction(event -> {
            User selected = members.getSelectionModel().getSelectedItem();
            if (selected != null) {
                object.removeMember(selected);
                removeFromListView(members, selected);
            }
        });
    }

    @Override
    protected void beforeSave() {
        object.setName(name.getText());
    }

    @Override
    protected void fillWithObjectData() {
        setData(object.getName(), object.getMembers());
    }

    @Override
    protected void fillWithDefaultData() {
        setData("", new HashSet<>());
    }

    private void setData(String groupName, Set<User> users) {
        name.setText(groupName);
        members.setItems(FXCollections.observableArrayList(users));
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