package com.a31r.sport.coachassistant.desktop.model.selector;

import com.a31r.sport.coachassistant.core.model.AbstractEntity;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

/**
 * Created by bahodurova on 1/16/2018.
 */
public interface Selector<T extends AbstractEntity> {

    void openDialog(Handler<T> handler);
    Parent getView(Handler<T> handler);
    ComboBox<T> getComboBox();

    interface Handler<V extends AbstractEntity> {
        void onSelect(V selected);
    }

}
