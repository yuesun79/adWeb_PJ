package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.exception.BadRequestException;
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
    @Autowired
    TaskMapper taskMapper;

    @Override
    public List<Task> retrieveAllTasks_class(Integer classId) {
        List<Task> tasks;
        // 根据classId查VClass @
        if (vClassService.getClass_classId(classId) == null) {
            throw new BadRequestException("Class(classId="+classId+") doesn't exist");
        } else {
            // 根据classId联表 @
            tasks = taskMapper.retrieveTask_classId(classId);
        }
        return tasks;
    }

    @Override
    public List<Task> retrieveAllTasks_user(Integer userId) {
        return null;
    }

    @Override
    public Task findTask_id(Integer taskId) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Task::getId, taskId);
        return baseMapper.selectOne(wrapper);
    }
}
