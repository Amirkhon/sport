package com.a31r.sport.coachassistant.desktop.model.editor;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by bahodurova on 1/21/2018.
 */
public class PopupEditor {

    private PopupEditor() {}

    public static <T> void open(Editor<T> editor, T object, Handler<T> callback) {
        open(editor, object, callback, null, null);
    }

    public static <T> void open(Editor<T> editor, T object, Handler<T> callback, Integer width, Integer height) {
        Stage stage = new Stage();
        editor.setObject(object, new Editor.Handler<T>() {
            @Override
            public T onSave(T object) {
                stage.close();
                callback.onSave(object);
                return object;
            }

            @Override
            public void onCancel() {
                stage.close();
            }
        });
        stage.setScene(new Scene(new VBox(editor.show())));
        editor.edit();
        if (width != null) {
            stage.setMinWidth(width);
        }
        if (height != null) {
            stage.setMinHeight(height);
        }
        stage.show();
    }

    public interface Handler<T> {
        void onSave(T result);
    }

}