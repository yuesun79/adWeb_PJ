package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fudan.se.community.controller.response.TasksResponse;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.AcceptMapper;
import com.fudan.se.community.mapper.VGroupMapper;
import com.fudan.se.community.pojo.task.Accept;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.service.UserService;
import com.fudan.se.community.pojo.vm.GroupTask;
import com.fudan.se.community.pojo.vm.Task;

import com.fudan.se.community.mapper.TaskMapper;
import com.fudan.se.community.service.ClassTaskService;
import com.fudan.se.community.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.service.VClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
public class TaskServiceImpl extends ServiceImpl<TaskMapper, com.fudan.se.community.pojo.task.Task> implements TaskService {
    @Autowired
    VClassService vClassService;
    @Autowired
    ClassTaskService classTaskService;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    UserService userService;
    @Autowired
    AcceptMapper acceptMapper;
  @Autowired
    VGroupMapper vGroupMapper;
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
    public TasksResponse retrieveAllTasks_user(Integer userId) {
        if (userService.getById(userId) == null)
            throw new BadRequestException("This user(userId = "+ userId + ") doesn't exists");
        // personal tasks: select accept
        List<Task> personalTasks = baseMapper.retrieveTasks_userId_accept(userId);
        // group tasks
        List<GroupTask> groupTasks = baseMapper.retrieveTasks_userId_inGroup(userId);
        return new TasksResponse(personalTasks, groupTasks);
    }

    @Override
    public Task findTask_id(Integer taskId) {
        //
        Task task = baseMapper.findTask_id(taskId);
        if (task == null)
            throw new BadRequestException("Task(TaskId="+ taskId +") doesn't exist");
        return task;
    }

    @Override
    public void checkOverDue(Integer taskId) {
        Calendar calendar = Calendar.getInstance();
        Timestamp cur = new Timestamp(calendar.getTimeInMillis());
        log.debug("---------> current time:"+cur.toString());
        com.fudan.se.community.pojo.task.Task task = getOne(
                new QueryWrapper<com.fudan.se.community.pojo.task.Task>().lambda()
                        .eq(com.fudan.se.community.pojo.task.Task::getId, taskId)
                        .ge(com.fudan.se.community.pojo.task.Task::getDdl, cur));
        if (task == null)
            throw new BadRequestException("Task's(TaskId="+ taskId +") ddl has reached");
    }

    @Override
    public void insertTask(com.fudan.se.community.pojo.task.Task task) {
        // 未审核状态
        //  task.setTeamSize()
        task.setValidity(0);
        int influenceRows = baseMapper.insert(task);
        if (influenceRows==0) {
            throw new BadRequestException("Task setup fails");
        }
    }

    @Override
    public void adminInsertTask(com.fudan.se.community.pojo.task.Task task) {
        // 管理者发布的任务，审核状态已通过
        task.setValidity(1);
        int influenceRows = baseMapper.insert(task);
        if (influenceRows==0) {
            throw new BadRequestException("Task setup fails");
        }
    }

    @Override
    public void adminChecked(int taskId) {
        Task task =this.findTask_id(taskId);
        if (task != null) {
            task.setValidity(1);
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("id", taskId);
            updateWrapper.set("validity", 1);   //审核通过
           baseMapper.update((com.fudan.se.community.pojo.task.Task) null,updateWrapper);
        }
    }

    @Override
    public List<Task> retrieveAllTasks_unchecked() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("validity",0);//相当于where id=1
        List<Task> list = taskMapper.selectList(wrapper);
       return list;
    }

    @Override
    public List<Task> retrieveAllTasks_unfinishedPersonal() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.le("process",1);//相当于小于等于1

        List<Accept> listAccept = acceptMapper.selectList(wrapper);
        List<Task> list = new ArrayList<Task>();
        for(int i =0 ;i<listAccept.size();i++){
            Task tem =taskMapper.findTask_id(listAccept.get(i).getTaskId());
            System.out.println(listAccept.get(i).getTaskId());
            list.add(tem);
        }
        return list;
    }

    @Override
    public List<Task> retrieveAllTasks_unfinishedGroup() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.le("checked",1);//相当于小于等于1

        List<VGroup> listAccept = vGroupMapper.selectList(wrapper);
        List<Task> list = new ArrayList<Task>();
        for(int i =0 ;i<listAccept.size();i++){
            Task tem =taskMapper.findTask_id(listAccept.get(i).getTaskId());
            System.out.println(listAccept.get(i).getTaskId());
            list.add(tem);
        }
        return list;
    }

    @Override
    public List<Task> retrieveAllTasks_unfinishedFree() {
        return null;
    }

}
