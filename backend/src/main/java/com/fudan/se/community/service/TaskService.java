package com.fudan.se.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudan.se.community.controller.response.TasksResponse;
import com.fudan.se.community.vm.Task;

import java.util.List;
import java.util.Map;

public interface TaskService extends IService<com.fudan.se.community.pojo.task.Task> {
    /** RetrieveInfoController **/
    List<Task> retrieveAllTasks_class(Integer classId);
    TasksResponse retrieveAllTasks_user(Integer userId);

    Task findTask_id(Integer taskId);

    void checkOverDue(Integer taskId);

    void insertTask(com.fudan.se.community.pojo.task.Task task);

}
