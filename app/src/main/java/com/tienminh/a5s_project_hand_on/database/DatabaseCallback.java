package com.tienminh.a5s_project_hand_on.database;

public interface DatabaseCallback<T> {
    void onTaskComplete(T result);
}
