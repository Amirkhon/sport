package com.a31r.sport.coachassistant.desktop.model.editor;

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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bahodurova on 1/12/2018.
 */
public abstract class AbstractUserEditor<T extends User> extends AbstractEditor<T> {

    @Autowired
    private UserPropertyEditor propertyEditor;

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

        groups.setDisable(true);
        setupPropertiesToolbar();
    }

    private void setupPropertiesToolbar() {
        addPropertyButton.setOnAction(event -> {
            openPropertyDialog(propertyEditor.newObject());
        });

        editPropertyButton.setOnAction(event -> {
            UserProperty selected = properties.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openPropertyDialog(selected);
            }
        });

        removePropertyButton.setOnAction(event -> {
            UserProperty selected = properties.getSelectionModel().getSelectedItem();
            if (selected != null) {
                object.removeProperty(selected);
                removeFromListView(properties, selected);
            }
        });
    }

    private void openPropertyDialog(UserProperty userProperty) {
        PopupEditor.open(propertyEditor, userProperty, result -> {
            object.addProperty(result);
            addToListView(properties, userProperty);
        });
    }

    @Override
    protected void fillWithObjectData() {
        setData(object.getFamilyName(), object.getName(), object.getPatronymic(),
                object.getBirthday(), object.getPhoto(), object.getProperties(), object.getGroups());
    }

    @Override
    protected void fillWithDefaultData() {
        setData("", "", "",
                LocalDate.of(2000, 1, 1), null, new ArrayList<>(), new HashSet<>());
    }

    @Override
    protected void beforeShow() {
        propertyTab.setContent(
                new VBox(properties, new ToolBar(addPropertyButton, removePropertyButton, editPropertyButton)));
        groupTab.setContent(groups);
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

    private void setData(String familyName, String name, String patronymic, LocalDate birthday,
                           byte[] rawPhoto, List<UserProperty> properties, Set<UserGroup> groups) {
        if (rawPhoto != null && rawPhoto.length > 0)
        {
            photo.setImage(ImageUtils.cropAndFit(rawPhoto, 245, 270));
        }
        this.familyName.setText(familyName);
        this.name.setText(name);
        this.patronymic.setText(patronymic);
        this.birthday.setValue(birthday);
        this.properties.setItems(FXCollections.observableArrayList(properties));
        this.groups.setItems(FXCollections.observableArrayList(groups));
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
    }
}