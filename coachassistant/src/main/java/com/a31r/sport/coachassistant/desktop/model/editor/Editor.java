package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.desktop.model.Visible;
import javafx.scene.Parent;

/**
 * Created by bahodurova on 1/11/2018.
 */
public interface Editor<T> extends Visible {
//    void setObject(T object);
    void setObject(T object, Handler<T> handler);
    void edit();
    void cancel();
    void save();
    T newObject();

    interface Handler<T> {
        T onSave(T object);
        void onCancel();
    }
}
