package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.service.UserService;
import com.fudan.se.community.vm.GroupTask;
import com.fudan.se.community.vm.Task;
import com.fudan.se.community.mapper.TaskMapper;
import com.fudan.se.community.service.ClassTaskService;
import com.fudan.se.community.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.service.VClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    UserService userService;

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
    public Map<String, Object> retrieveAllTasks_user(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        if (userService.getById(userId) == null)
            throw new BadRequestException("This user(userId = "+ userId + ") doesn't exists");
        // personal tasks: select accept
        List<Task> personalTasks = baseMapper.retrieveTasks_userId_accept(userId);
        map.put("personal", personalTasks);
        // group tasks
        List<GroupTask> groupTasks = baseMapper.retrieveTasks_userId_inGroup(userId);
        map.put("group", groupTasks);
        return map;
    }

    @Override
    public Task findTask_id(Integer taskId) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Task::getId, taskId);
        Task task = baseMapper.selectOne(wrapper);
        if (task == null)
            throw new BadRequestException("Task(TaskId="+ taskId +") doesn't exist");
        return task;
    }
}
