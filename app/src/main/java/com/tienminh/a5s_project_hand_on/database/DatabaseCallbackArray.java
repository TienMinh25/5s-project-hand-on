package com.tienminh.a5s_project_hand_on.database;

import java.util.ArrayList;

public interface DatabaseCallbackArray<T> {
    void onTaskComplete(T result);
}
