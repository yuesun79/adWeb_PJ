package com.fudan.se.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudan.se.community.controller.response.TasksResponse;
import com.fudan.se.community.pojo.vm.Task;

import java.util.List;

public interface TaskService extends IService<com.fudan.se.community.pojo.task.Task> {
    /** RetrieveInfoController **/
    List<Task> retrieveAllTasks_class(Integer classId);
    TasksResponse retrieveAllTasks_user(Integer userId);

    Task findTask_id(Integer taskId);

    void checkOverDue(Integer taskId);

    void insertTask(com.fudan.se.community.pojo.task.Task task);

    void adminInsertTask(com.fudan.se.community.pojo.task.Task task);

    void adminChecked(int taskId);

    List<Task> retrieveAllTasks_unchecked();

    List<Task> retrieveAllTasks_unfinishedPersonal();

    List<Task> retrieveAllTasks_unfinishedGroup();

    List<Task> retrieveAllTasks_unfinishedFree( int userId);

    void addPersonalEv(int userId, int taskId);

    void cutPersonalEv(int userId, Integer ev);

    void addGroupEv(int userId, int ev);

    List<Task> retrieveAllTasks_free();
}
