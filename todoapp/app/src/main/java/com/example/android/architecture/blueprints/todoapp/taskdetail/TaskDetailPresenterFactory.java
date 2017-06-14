package com.example.android.architecture.blueprints.todoapp.taskdetail;


import com.example.android.architecture.blueprints.todoapp.base.PresenterFactory;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;

public class TaskDetailPresenterFactory implements PresenterFactory<TaskDetailPresenter> {

    private final String mTaskId;
    private final TasksRepository mTasksRepository;

    public TaskDetailPresenterFactory(String taskId, TasksRepository tasksRepository) {
        mTaskId = taskId;
        mTasksRepository = tasksRepository;
    }

    @Override
    public TaskDetailPresenter create() {
        return new TaskDetailPresenter(mTaskId, mTasksRepository);
    }
}
