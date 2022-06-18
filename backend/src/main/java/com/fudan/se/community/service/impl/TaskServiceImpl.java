package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fudan.se.community.controller.response.TasksResponse;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.AcceptMapper;
import com.fudan.se.community.mapper.UserMapper;
import com.fudan.se.community.mapper.VGroupMapper;
import com.fudan.se.community.pojo.task.Accept;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.pojo.user.User;
import com.fudan.se.community.pojo.vm.unfinishGTask;
import com.fudan.se.community.pojo.vm.unfinishTask;
import com.fudan.se.community.service.*;
import com.fudan.se.community.pojo.vm.GroupTask;
import com.fudan.se.community.pojo.vm.Task;

import com.fudan.se.community.mapper.TaskMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    UserMapper userMapper;
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
//        task.setTeamSize()


        task.setValidity(0);
        int influenceRows = baseMapper.insert(task);
        if (influenceRows==0) {
            throw new BadRequestException("Task setup fails");
        }
    }

    @Override
    public void adminInsertTask(com.fudan.se.community.pojo.task.Task task) {
        // 管理者发布的任务，审核状态已通过
        task.setIsFree(0); //不是自由发布的任务。
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
          int t= baseMapper.update((com.fudan.se.community.pojo.task.Task) null,updateWrapper);
            if (t<=0)
                throw new BadRequestException("Task's(TaskId="+ taskId +") has some wrong");

        }
    }

    @Override
    public List<com.fudan.se.community.pojo.vm.Task> retrieveAllTasks_unchecked() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("validity",0);//相当于where id=1
        List<com.fudan.se.community.pojo.task.Task> list = taskMapper.selectList(wrapper);
        List<User> liastUser =new ArrayList<>();
        for (int i=0;i<list.size();i++){
            QueryWrapper wrapper1 = new QueryWrapper();
            wrapper1.eq("id",list.get(i).getPublisherId());//相当于where id=1
            List<User> list1 = userMapper.selectList(wrapper1);
            liastUser.add(list1.get(0));
        }
        List<com.fudan.se.community.pojo.vm.Task> listVm=new ArrayList<>();
        for (int i=0;i<list.size();i++) {

            com.fudan.se.community.pojo.vm.Task tem =new com.fudan.se.community.pojo.vm.Task(liastUser.get(i),list.get(i));
            listVm.add(tem);
        }

       return listVm;
    }

    @Override
    public List<unfinishTask> retrieveAllTasks_unfinishedPersonal() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.le("checked",1);//相当于小于等于1

        List<Accept> listAccept = acceptMapper.selectList(wrapper);
        List<unfinishTask> res= new ArrayList<unfinishTask>();
        for(int i =0 ;i<listAccept.size();i++){
            com.fudan.se.community.pojo.task.Task temTask =taskMapper.selectById(listAccept.get(i).getTaskId());

            User pubUser =userMapper.selectById(temTask.getPublisherId());   //发布任务者
            User upUser =new User();
            if (!listAccept.get(i).getFile().equals("")){
                upUser=userMapper.selectById(listAccept.get(i).getUserId());  //上传文件者
            }
            unfinishTask tem1 =new unfinishTask(temTask,listAccept.get(i),pubUser,upUser);
            res.add(tem1);
        }
        return res;
    }

    @Override
    public List<unfinishGTask> retrieveAllTasks_unfinishedGroup() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.le("checked",1);//相当于小于等于1

        List<VGroup> listAccept = vGroupMapper.selectList(wrapper);
        List<Task> list = new ArrayList<Task>();
        List<unfinishGTask> res= new ArrayList<unfinishGTask>();
        for(int i =0 ;i<listAccept.size();i++){
            com.fudan.se.community.pojo.task.Task temTask =taskMapper.selectById(listAccept.get(i).getTaskId());
            User pubUser =userMapper.selectById(temTask.getPublisherId());
            User upUser =new User();
            if (!listAccept.get(i).getFile().equals("")){
                upUser=userMapper.selectById(listAccept.get(i).getGroupLeader());  //上传文件者
            }
            unfinishGTask tem1 =new unfinishGTask(temTask,listAccept.get(i),pubUser,upUser);
            res.add(tem1);
        }
        return res;
    }

    @Override
    public List<unfinishTask> retrieveAllTasks_unfinishedFree(int userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("is_free",1);//等于1的自由任务
        List<com.fudan.se.community.pojo.task.Task> listFreeTask = taskMapper.selectList(wrapper); //所有自由任务列表
        List<Accept> list = new ArrayList<Accept>();
        for(int i =0 ;i<listFreeTask.size();i++){
            QueryWrapper wrapper1 = new QueryWrapper();
            wrapper1.eq("task_id",listFreeTask.get(i).getId());
            wrapper1.eq("user_id",userId);
            wrapper1.le("checked",1);//小于等于1,即未完成的
            List<Accept> listFreeUncompletionTask = acceptMapper.selectList(wrapper1);
           for (int j =0;j<listFreeUncompletionTask.size();j++){
               list.add(listFreeUncompletionTask.get(j));  //所有未完成的已经accept的自由任务列表
           }
        }
        List<Task> listTask = new ArrayList<Task>();
        List<unfinishTask> res= new ArrayList<unfinishTask>();
        for(int i =0 ;i<list.size();i++){  //根据accept表寻找符合条件的task
            com.fudan.se.community.pojo.task.Task temTask =taskMapper.selectById(list.get(i).getTaskId());
            User pubUser =userMapper.selectById(temTask.getPublisherId());   //发布任务者
            User upUser =new User();
            if (!list.get(i).getFile().equals("")){
                upUser=userMapper.selectById(list.get(i).getUserId());  //上传文件者
            }
            unfinishTask tem1 =new unfinishTask(temTask,list.get(i),pubUser,upUser);
            res.add(tem1);
        }
        return res;
    }

   //给个人任务的个人增加经验值
    @Override
    public void addPersonalEv(int userId, int taskId) {
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.eq("task_id",taskId);
        wrapper1.eq("user_id",userId);
        List<Accept> listcompletionTask = acceptMapper.selectList(wrapper1);
        if (listcompletionTask.size()==0){
            throw new BadRequestException("Task not accept or complete");
        }else {
            Task task =taskMapper.findTask_id(taskId);
            int taskEv =task.getEv();
            userService.addEv(userId,taskEv);
        }
    }
    //给发布个人任务的个人减少经验值
    @Override
    public void cutPersonalEv(int userId, Integer ev) {
      userService.cutEv(userId,ev);
    }
    //
    @Override
    public void addGroupEv(int userId, int ev) {
        userService.addEv(userId,ev);
    }

    @Override
    public List<Task> retrieveAllTasks_free() {
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.eq("is_Free",1);
        List<Task> listFreeTask = taskMapper.selectList(wrapper1);
        return listFreeTask;
    }

}
