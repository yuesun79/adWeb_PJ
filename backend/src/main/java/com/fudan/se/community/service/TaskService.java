package com.fudan.se.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudan.se.community.vm.Task;

import java.util.List;
import java.util.Map;

public interface TaskService extends IService<Task> {
    /** RetrieveInfoController **/
    List<Task> retrieveAllTasks_class(Integer classId);
    Map<String, Object> retrieveAllTasks_user(Integer userId);

    Task findTask_id(Integer taskId);

}
