package com.fudan.se.community.service.impl;

import com.fudan.se.community.pojo.task.Task;
import com.fudan.se.community.mapper.TaskMapper;
import com.fudan.se.community.service.ClassTaskService;
import com.fudan.se.community.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.service.VClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    @Autowired
    VClassService vClassService;
    @Autowired
    ClassTaskService classTaskService;

    @Override
    public List<Task> retrieveAllTasks_class(Integer classId) {
        List<Task> tasks;
        // 根据classId查VClass @
        if (vClassService.getClass_classId(classId) == null) {
            return null;
        } else {
            // 根据classId查class_task @
//            if (classTaskService.getTaskId_classId(classId))
        }
        return null;
    }

    @Override
    public List<Task> retrieveAllTasks_user(Integer userId) {
        return null;
    }
}
