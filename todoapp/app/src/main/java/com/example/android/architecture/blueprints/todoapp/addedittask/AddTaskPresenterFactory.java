package com.example.android.architecture.blueprints.todoapp.addedittask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.architecture.blueprints.todoapp.base.PresenterFactory;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;

public class AddTaskPresenterFactory implements PresenterFactory<AddEditTaskPresenter>{
    private final String mTaskId;
    private final TasksRepository mTasksRepository;

    public AddTaskPresenterFactory(@Nullable String taskId, @NonNull TasksRepository tasksRepository) {
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
    }

    @Override
    public AddEditTaskPresenter create() {
        return new AddEditTaskPresenter(mTaskId, mTasksRepository);
    }
}
