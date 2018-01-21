package com.a31r.sport.coachassistant.desktop.view.util;

import com.a31r.sport.coachassistant.desktop.model.editor.Editor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * Created by bahodurova on 1/12/2018.
 */
public class DialogUtil {

    public static void editCardDialog(Editor editor, EventHandler<ActionEvent> saveAction,
                                      EventHandler<ActionEvent> cancelAction) {
        Stage stage = new Stage();
        stage.setScene(new Scene(editCard(editor, saveAction, cancelAction, stage)));
        stage.show();
    }

    private static Pane editCard(Editor editor, EventHandler<ActionEvent> saveAction,
                                 EventHandler<ActionEvent> cancelAction, Stage stage) {
        BorderPane root = new BorderPane();
        ToolBar toolBar = new ToolBar();
        Button saveButton = new Button("Сохранить");
        Button cancelButton = new Button("Отмена");
        cancelButton.setOnAction(event -> {
            editor.cancel();
            cancelAction.handle(event);
            if (stage != null) {
                stage.close();
            }
        });
        saveButton.setOnAction(event -> {
            editor.save();
            saveAction.handle(event);
            if (stage != null) {
                stage.close();
            }
        });
        toolBar.getItems().addAll(saveButton, cancelButton);
        root.setCenter(editor.show());
        root.setBottom(toolBar);
        editor.edit();
        return root;
    }

    public static Pane editCard(Editor editor, EventHandler<ActionEvent> saveAction,
                                EventHandler<ActionEvent> cancelAction) {
        return editCard(editor, saveAction, cancelAction,null);
    }

    public static final FileChooser.ExtensionFilter IMAGES =
            new FileChooser.ExtensionFilter("Файлы изображений", "*.png", "*.jpeg", "*.jpg");

    private static File lastDirectory;

    public static File getLastDirectory() {
        return lastDirectory;
    }

    public static void setLastDirectory(File lastDirectory) {
        DialogUtil.lastDirectory = lastDirectory;
    }

    public static File showFileChooserDialog(String title, File initialDirectory, String initialFileName, boolean open,
                                             FileChooser.ExtensionFilter extensionFilter)
    {
        FileChooser fileChooser = new FileChooser();
        if (initialDirectory != null)
        {
            fileChooser.setInitialDirectory(initialDirectory);
        }
        fileChooser.setTitle(title);
        if (initialFileName != null)
        {
            fileChooser.setInitialFileName(initialFileName);
        }
        if (extensionFilter != null)
        {
            fileChooser.getExtensionFilters().add(extensionFilter);
        }
        if (open)
        {
            return fileChooser.showOpenDialog(new Stage());
        }
        else
        {
            return fileChooser.showSaveDialog(new Stage());
        }
    }

    public static File showDirectoryChooserDialog(String title, File initialDirectory)
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);

        if (initialDirectory != null)
        {
            directoryChooser.setInitialDirectory(initialDirectory);
        }

        return directoryChooser.showDialog(new Stage());
    }

    public static Optional showErrorMessage(String headerText, String message)
    {
        return showMessage(Alert.AlertType.ERROR, headerText, "Ошибка", message);
    }

    public static Optional showInfoMessage(String headerText, String message)
    {
        return showMessage(Alert.AlertType.INFORMATION, headerText, "Информация", message);
    }

    public static boolean showConfirmMessage(String headerText, String message)
    {
        return showMessage(Alert.AlertType.CONFIRMATION, headerText, "Подтвердить", message).get() == ButtonType.OK;
    }

    public static Optional showMessage(Alert.AlertType type, String headerText, String title, String message)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        return alert.showAndWait();
    }
}