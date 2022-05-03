package com.fudan.se.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudan.se.community.pojo.task.Task;

import java.util.List;

public interface TaskService extends IService<Task> {
    /** RetrieveInfoController **/
    List<Task> retrieveAllTasks_class(Integer classId);
    List<Task> retrieveAllTasks_user(Integer userId);

    Task findTask_id(Integer taskId);

}
