package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.AbstractEntity;
import com.a31r.sport.coachassistant.desktop.model.Visible;

/**
 * Created by bahodurova on 1/11/2018.
 */
public interface Editor<T extends AbstractEntity> extends Visible {
    void setObject(T object, Handler<T> handler);
    void edit();
    void cancel();
    void save();
    T newObject();

    interface Handler<T extends AbstractEntity> {
        T onSave(T object);
        void onCancel();
    }
}
