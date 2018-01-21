package com.a31r.sport.coachassistant.desktop.model.selector;

import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

/**
 * Created by bahodurova on 1/16/2018.
 */
public interface Selector<T> {

    void openDialog(Handler<T> handler);
    Parent getView(Handler<T> handler);
    ComboBox<T> getComboBox();

    interface Handler<V> {
        void onSelect(V selected);
    }

}
