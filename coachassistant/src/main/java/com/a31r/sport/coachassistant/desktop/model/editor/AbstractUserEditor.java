package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.service.UserGroupService;
import com.a31r.sport.coachassistant.core.model.service.UserPropertyService;
import com.a31r.sport.coachassistant.desktop.model.selector.UserGroupSelector;
import com.a31r.sport.coachassistant.desktop.view.util.DialogUtil;
import com.a31r.sport.coachassistant.desktop.view.util.ImageUtils;
import com.a31r.sport.coachassistant.core.model.User;
import com.a31r.sport.coachassistant.core.model.UserGroup;
import com.a31r.sport.coachassistant.core.model.UserProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by bahodurova on 1/12/2018.
 */
public abstract class AbstractUserEditor<T extends User> extends AbstractEditor<T> {

    @Autowired
    private UserGroupService groupService;
    @Autowired
    private UserPropertyService propertyService;
    @Autowired
    private UserPropertyEditor propertyEditorService;
    @Autowired
    private UserGroupSelector userGroupSelector;

    private final ImageView photo = new ImageView("/images/no-photo.png");
    private byte[] image = null;
    private final TextField familyName = new TextField();
    private final TextField name = new TextField();
    private final TextField patronymic = new TextField();
    private final DatePicker birthday = new DatePicker();
    protected final ListView<UserProperty> properties = new ListView<>();
    protected final ListView<UserGroup> groups = new ListView<>();
    protected final Tab propertyTab = new Tab("Дополнительные сведения");
    protected final Tab groupTab = new Tab("Группы");
    protected final Button addPropertyButton = new Button("Добавить");
    protected final Button editPropertyButton = new Button("Изменить");
    protected final Button removePropertyButton = new Button("Удалить");

    protected final Button addGroupButton = new Button("Добавить");
    protected final Button removeGroupButton = new Button("Удалить");

    public AbstractUserEditor() {
        gridPane.add(new Label("Фотография"), 0, 0);
        gridPane.add(photo, 1, 0);
        gridPane.add(new Label("Фамилия"), 0, 1);
        gridPane.add(familyName, 1, 1);
        gridPane.add(new Label("Имя"), 0, 2);
        gridPane.add(name, 1, 2);
        gridPane.add(new Label("Отчество"), 0, 3);
        gridPane.add(patronymic, 1, 3);
        gridPane.add(new Label("Дата рождения"), 0, 4);
        gridPane.add(birthday, 1, 4);

        photo.setFitHeight(270);
        photo.setFitWidth(245);

        birthday.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            @Override
            public String toString(LocalDate object) {
                if (object == null) {
                    return "";
                }
                return dateTimeFormatter.format(object);
            }

            @Override
            public LocalDate fromString(String string) {
                if (StringUtils.isEmpty(string)) {
                    return null;
                }
                return LocalDate.parse(string, dateTimeFormatter);
            }
        });

        photo.setOnMouseClicked(event -> {
            File photoFile = DialogUtil.showFileChooserDialog("Выберите фото", DialogUtil.getLastDirectory(),
                    null, true, DialogUtil.IMAGES);
            if (photoFile != null)
            {
                try {
                    byte[] photo = Files.readAllBytes(photoFile.toPath());
                    object.setPhoto(photo);
                    this.photo.setImage(ImageUtils.cropAndFit(photo, 245, 270));
                    DialogUtil.setLastDirectory(photoFile.getParentFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        setupPropertiesToolbar();
        setupGroupsToolbar();
    }

    private void setupGroupsToolbar() {
        addGroupButton.setOnAction(event ->
                userGroupSelector.openDialog(selected -> {
                    groupService.includeMembers(selected);
//                    object.addGroup(selected);
                    selected.addMember(object);
                    groupService.save(selected);
                    addToListView(groups, selected);
                })
        );

        removeGroupButton.setOnAction(event -> {
            UserGroup selected = groups.getSelectionModel().getSelectedItem();
            if (selected != null) {
                groupService.includeMembers(selected);
//                object.removeGroup(selected);
                selected.removeMember(object);
                groupService.save(selected);
                removeFromListView(groups, selected);
            }
        });
    }

    private void setupPropertiesToolbar() {
        addPropertyButton.setOnAction(event -> {
            UserProperty newUserProperty = propertyEditorService.newObject();
            newUserProperty.setUser(object);
            openPropertyDialog(newUserProperty);
        });

        editPropertyButton.setOnAction(event -> {
            UserProperty selected = properties.getSelectionModel().getSelectedItem();
            if (selected != null) {
//                removeFromListView(properties, selected);
                openPropertyDialog(selected);
            }
        });

        removePropertyButton.setOnAction(event -> {
            UserProperty selected = properties.getSelectionModel().getSelectedItem();
            if (selected != null) {
                removeFromListView(properties, selected);
                object.removeProperty(selected);
            }
        });
    }

    private void openPropertyDialog(UserProperty userProperty) {
//        propertyEditorService.setObject(userProperty);
        PopupEditor.open(propertyEditorService, userProperty, result -> {
            object.addProperty(result);
            addToListView(properties, userProperty);
        });
//        DialogUtil.editCardDialog(propertyEditorService, save -> {
//            userProperty.setUser(object);
//            propertyService.save(userProperty);
//
//        }, cancel -> {
//            addToListView(properties, userProperty);
//        });
    }

    @Transactional
    @Override
    protected void setData() {
        if (object.getPhoto() != null && object.getPhoto().length > 0)
        {
            photo.setImage(ImageUtils.cropAndFit(object.getPhoto(), 245, 270));
        }
        this.familyName.setText(object.getFamilyName());
        this.name.setText(object.getName());
        this.patronymic.setText(object.getPatronymic());
        this.birthday.setValue(object.getBirthday());
        this.properties.setItems(FXCollections.observableArrayList(object.getProperties()));
        this.groups.setItems(FXCollections.observableArrayList(object.getGroups()));
    }

    @Override
    protected void beforeShow() {
        propertyTab.setContent(
                new VBox(properties, new ToolBar(addPropertyButton, removePropertyButton, editPropertyButton)));
        groupTab.setContent(new VBox(groups, new ToolBar(addGroupButton, removeGroupButton)));
        tabPane.getTabs().addAll(propertyTab, groupTab);
    }

    @Override
    protected void beforeSave() {
        if (image != null) {
            object.setPhoto(image);
        }
        object.setFamilyName(familyName.getText());
        object.setName(name.getText());
        object.setPatronymic(patronymic.getText());
        object.setBirthday(birthday.getValue());
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.photo.setDisable(disabled);
        this.familyName.setDisable(disabled);
        this.name.setDisable(disabled);
        this.patronymic.setDisable(disabled);
        this.birthday.setDisable(disabled);
        this.properties.setDisable(disabled);
        this.addPropertyButton.setDisable(disabled);
        this.editPropertyButton.setDisable(disabled);
        this.removePropertyButton.setDisable(disabled);
        this.groups.setDisable(disabled && object.getId() != null);
        this.addGroupButton.setDisable(disabled || object.getId() == null);
        this.removeGroupButton.setDisable(disabled || object.getId() == null);
    }
}