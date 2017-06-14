package com.example.android.architecture.blueprints.todoapp.tasks;

import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.base.PresenterFactory;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;

public class TaskPresenterFactory implements PresenterFactory<TasksPresenter> {

    private static final String TAG = TaskPresenterFactory.class.getSimpleName();

    private final TasksRepository mTasksRepository;

    public TaskPresenterFactory(TasksRepository tasksRepository) {
        mTasksRepository = tasksRepository;
        Log.d(TAG, "TaskPresenterFactory =" +this);
    }

    @Override
    public TasksPresenter create() {
        Log.d(TAG, "TaskPresenterFactory create >>");
        return new TasksPresenter(mTasksRepository);
    }

}
