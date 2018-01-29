package com.a31r.sport.coachassistant.desktop.view.component;

import com.a31r.sport.coachassistant.core.model.AbstractEntity;
import com.a31r.sport.coachassistant.core.service.*;
import com.a31r.sport.coachassistant.desktop.model.editor.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by bahodurova on 1/12/2018.
 */
@Service
public class ListViewService {

    @Autowired
    private AthleteService athleteService;
    @Autowired
    private CoachService coachService;
    @Autowired
    private UserPropertyTypeService userPropertyTypeService;
    @Autowired
    private AthleteParameterTypeService athleteParameterTypeService;
    @Autowired
    private TrainingGroupService trainingGroupService;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private TrainingSessionService trainingSessionService;

    @Autowired
    private AthleteEditor athleteEditor;
    @Autowired
    private CoachEditor coachEditor;
    @Autowired
    private TrainingGroupEditor trainingGroupEditor;
    @Autowired
    private ExerciseEditor exerciseEditor;
    @Autowired
    private AthleteParameterTypeEditor athleteParameterTypeEditor;
    @Autowired
    private UserPropertyTypeEditor userPropertyTypeEditor;
    @Autowired
    private TrainingSessionEditor trainingSessionEditor;

    private DataService dataService;
    private Editor editor;
    private Editor.Handler<AbstractEntity> handler = new Editor.Handler() {
        @Override
        public AbstractEntity onSave(AbstractEntity object) {
            view.setCenter(null);
            if (!listView.getItems().stream().anyMatch(abstractEntity -> abstractEntity.getId() == object.getId())) {
                listView.getItems().add(object);
            }
            listView.refresh();
            return dataService.save(object);
        }

        @Override
        public void onCancel() {
            view.setCenter(null);
        }
    };

    private final BorderPane view = new BorderPane();
    private final ListView<AbstractEntity> listView = new ListView();
    private final VBox listViewHolder = new VBox();
    private final Button addButton = new Button("Добавить");
    private final Button editButton = new Button("Изменить");
    private final Button removeButton = new Button("Удалить");

    private void setDisableButtons(boolean disable) {
        removeButton.setDisable(disable);
        editButton.setDisable(disable);
    }

    private Editor getEditor() {
        return editor;
    }

    private void onRemove(AbstractEntity selected) {
        dataService.delete(selected);
    }

    private ObservableList getItems() {
        return FXCollections.observableArrayList(dataService.findAll());
    }

    public void setListType(ListType listType) {
        switch (listType) {
            case ATHLETES:
                dataService = athleteService;
                editor = athleteEditor;
                break;
            case COACHES:
                dataService = coachService;
                editor = coachEditor;
                break;
            case TRAINING_GROUPS:
                dataService = trainingGroupService;
                editor = trainingGroupEditor;
                break;
            case EXERCISES:
                dataService = exerciseService;
                editor = exerciseEditor;
                break;
            case TRAINING_SESSIONS:
                dataService = trainingSessionService;
                editor = trainingSessionEditor;
                break;
            case USER_PROPERTY_TYPES:
                dataService = userPropertyTypeService;
                editor = userPropertyTypeEditor;
                break;
            case ATHLETE_PARAMETER_TYPES:
                dataService = athleteParameterTypeService;
                editor = athleteParameterTypeEditor;
                break;
            default:

                break;
        }
        if (dataService != null) {
            listView.setItems(getItems());
        }
    }

    public Parent getView() {
        view.setCenter(null);
        return view;
    }

    @PostConstruct
    private void init() {
        setListType(ListType.ATHLETES);

        editButton.setOnAction(event -> {
            AbstractEntity selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                getEditor().edit();
            }
        });

        removeButton.setOnAction(event -> {
            AbstractEntity selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                listView.getItems().remove(selected);
                onRemove(selected);
            }
        });

        addButton.setOnAction(event -> {
            AbstractEntity object = getEditor().newObject();
            getEditor().setObject(object, handler);
            view.setCenter(editor.show());
            editor.edit();
        });

        listViewHolder.getChildren()
                .addAll(new ToolBar(addButton, removeButton, editButton), listView);

        listView.setMinWidth(400);
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        getEditor().setObject(newValue, handler);
                        view.setCenter(getEditor().show());
                        setDisableButtons(false);
                    } else {
                        setDisableButtons(true);
                    }
                });

        view.setLeft(listViewHolder);
    }

    public enum ListType {
        ATHLETES("athletes", "Спортсмены"),
        COACHES("coaches", "Тренеры"),
        TRAINING_GROUPS("trainingGroups","Группы"),
        EXERCISES("exercises","Упражнения"),
        TRAINING_SESSIONS("trainingSessions","Тренировки"),
        USER_PROPERTY_TYPES("userPropertyTypes","Типы свойств пользователя"),
        ATHLETE_PARAMETER_TYPES("athleteParameterTypes","Типы параметров спортсмена"),;

        private final String label;
        private final String id;

        ListType(String id, String label) {
            this.id = id;
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public String getId() {
            return id;
        }

        public static ListType getByLabel(String label) {
            for (ListType type : ListType.values()) {
                if (type.label.equals(label)) {
                    return type;
                }
            }
            return null;
        }
    }
}