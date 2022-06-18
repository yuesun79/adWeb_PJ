package com.fudan.se.community.controller;

import com.fudan.se.community.annotation.AdminLoginToken;
import com.fudan.se.community.controller.request.task.post.AddGroupEvRequest;
import com.fudan.se.community.controller.request.task.post.CheckGTaskCompleteRequest;
import com.fudan.se.community.controller.request.task.post.CheckPTaskCompleteRequest;
import com.fudan.se.community.controller.request.task.post.CreatTaskRequest;
import com.fudan.se.community.pojo.task.ClassTask;
import com.fudan.se.community.pojo.task.Task;
import com.fudan.se.community.pojo.task.group.Occupy;
import com.fudan.se.community.pojo.task.group.Room;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Api(tags="PostTaskController")
@RestController
public class PostTaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    ClassTaskService classTaskService;
    @Autowired
    VGroupService vGroupService;
    @Autowired
    OccupyService occupyService;
    @Autowired
    RoomService roomService;
    @Autowired
    AcceptService acceptService;

    /** post **/
    // student 需要验证 只能是个人任务
    @ApiOperation(value="发布自由任务",notes = "insert task table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对/信息不全等")
    })


    @RequestMapping(value = "/createFreeTask", method = RequestMethod.PUT)

    public ResponseEntity<Object> createFreeTask(@RequestBody CreatTaskRequest creatTaskRequest) {
        System.out.println("------调用成功");
        Task task= new Task();
        Timestamp ts= Timestamp.valueOf(String.valueOf(creatTaskRequest.ddl));
        task.setDdl(ts);
        task.setDescription(creatTaskRequest.description);
        task.setEv(creatTaskRequest.ev);
        task.setName(creatTaskRequest.name);
        task.setOptional(creatTaskRequest.optional);
        task.setPublisherId(creatTaskRequest.userId);
        task.setTeamSize(creatTaskRequest.team_size);
        //插入class数据
        taskService.insertTask(task);
        System.out.println("--------插入成功");
        ClassTask classTask =new ClassTask();
        classTask.setTaskId(task.getId());
        classTask.setClassId(creatTaskRequest.classId);
        //插入class-Task数据
        classTaskService.insertClassTask(classTask);

       //扣除创建者的经验值
        int userId =creatTaskRequest.userId;
        taskService.cutPersonalEv(userId,creatTaskRequest.ev);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // COMPLETION // STUDENT // free //
    @ApiOperation(value="user审核自由任务完成",notes = "update accept table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "信息不对balabala")
    })

    @RequestMapping(value = "checkCompletion/freeTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> checkCompletion_free(@RequestBody CheckPTaskCompleteRequest checkPTaskCompleteRequest) {
        int userId =checkPTaskCompleteRequest.getUserId();  //接受任务的学生id
        int taskId =checkPTaskCompleteRequest.getTaskId();  //已完成的任务id
        acceptService.checkCompletion(userId,taskId);
        taskService.addPersonalEv(userId,taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // admin 不用审核 可以是团体任务
    @ApiOperation(value="管理员发布个人任务",notes = "insert task table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对/信息不全等")
    })

    @AdminLoginToken
    @RequestMapping(value = "admin/createPersonalTask", method = RequestMethod.POST)
    public ResponseEntity<Object> createPersonalTask(@RequestBody CreatTaskRequest creatTaskRequest) {
        System.out.println("------调用成功");
        Task task= new Task();
        Timestamp ts= Timestamp.valueOf(String.valueOf(creatTaskRequest.ddl));
        task.setDdl(ts);
        task.setDescription(creatTaskRequest.description);
        task.setEv(creatTaskRequest.ev);
        task.setName(creatTaskRequest.name);
        task.setOptional(creatTaskRequest.optional);
        task.setPublisherId(creatTaskRequest.userId);
        task.setTeamSize(creatTaskRequest.team_size);
        //管理员新建任务，即该任务已经验证了
        taskService.adminInsertTask(task);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="管理员发布团队任务",notes = "insert task table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对/信息不全等")
    })
    @AdminLoginToken
    @RequestMapping(value = "admin/createGroupTask", method = RequestMethod.POST)
    public ResponseEntity<Object> createGroupTask(@RequestBody CreatTaskRequest creatTaskRequest) {
        System.out.println("------调用成功");
        Task task= new Task();
        Timestamp ts= Timestamp.valueOf(String.valueOf(creatTaskRequest.ddl));
        task.setDdl(ts);
        task.setDescription(creatTaskRequest.description);
        task.setEv(creatTaskRequest.ev);
        task.setName(creatTaskRequest.name);
        task.setOptional(creatTaskRequest.optional);
        task.setPublisherId(creatTaskRequest.userId);
        task.setTeamSize(creatTaskRequest.team_size);
        //管理员新建任务，即该任务已经验证了
        taskService.adminInsertTask(task);
       //创建团队
        VGroup vGroup =new VGroup();
        vGroup.setTaskId(task.getId());
        vGroup.setChecked(0);
        vGroup.setName(task.getName()+" "+"group");
        vGroupService.insert(vGroup);

        //为该任务新建一个房间
        Room room =new Room();
        room.setName(creatTaskRequest.name);
        roomService.insert(room);

       //绑定房间和团队
        Occupy occupy =new Occupy(vGroup.getId(),room.getId());
        occupyService.insert(occupy);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /** review **/
    // freeTask //
    @ApiOperation(value="管理员审核自由任务",notes = "update task table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "信息不对")
    })
    @AdminLoginToken
    @RequestMapping(value = "admin/checkFreeTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> checkFreeTask(@RequestParam Integer taskId) {
        taskService.adminChecked(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // COMPLETION // ADMIN // personal //
    @ApiOperation(value="管理员审核个人任务完成",notes = "update accept table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "信息不对balabala")
    })
    @AdminLoginToken
    @RequestMapping(value = "admin/checkCompletion/personalTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> checkCompletion_personal(@RequestBody CheckPTaskCompleteRequest checkPTaskCompleteRequest) {
        int userId =checkPTaskCompleteRequest.getUserId();
        int taskId =checkPTaskCompleteRequest.getTaskId();
        acceptService.checkCompletion(userId,taskId);
        //给个人添加经验值
        taskService.addPersonalEv(userId,taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // group //
    @ApiOperation(value="管理员审核团队任务完成",notes = "update v_group table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "信息不对balabala")
    })
    @AdminLoginToken
    @RequestMapping(value = "admin/checkCompletion/groupTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> checkCompletion_group(@RequestBody CheckGTaskCompleteRequest checkGTaskCompleteRequest) {
      // int userId =checkGTaskCompleteRequest.getUserId();
       //int taskId =checkGTaskCompleteRequest.getTaskId(); 暂不需要
       int groupId =checkGTaskCompleteRequest.getGroupId();

       //审核团队任务
       vGroupService.checkCompletion(groupId);

       //给团队里所有成员添加不同经验值，需队长单独调用addaddGroupEv接口

       //删除房间与组号的组合表
       occupyService.romove(groupId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value="队长加分",notes = "update accept table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "信息不对balabala")
    })

    @RequestMapping(value = "addGroupEv", method = RequestMethod.POST)
    public ResponseEntity<Object> checkCompletion_personal(@RequestBody AddGroupEvRequest addGroupEvRequest) {
        int userId =addGroupEvRequest.getUserId();
        int ev =addGroupEvRequest.getEv();

        //给个人添加经验值
        taskService.addGroupEv(userId,ev);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
