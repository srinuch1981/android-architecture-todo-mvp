package com.example.android.architecture.blueprints.todoapp.statistics;

import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.base.PresenterFactory;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;

public class StatisticsPresenterFactory implements PresenterFactory<StatisticsPresenter> {

    private final TasksRepository mTasksRepository;

    public StatisticsPresenterFactory(@NonNull TasksRepository tasksRepository) {
        mTasksRepository = tasksRepository;
    }

    @Override
    public StatisticsPresenter create() {
        return new StatisticsPresenter(mTasksRepository);
    }
}
