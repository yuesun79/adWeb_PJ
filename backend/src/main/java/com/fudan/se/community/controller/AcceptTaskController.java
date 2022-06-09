package com.fudan.se.community.controller;

import com.fudan.se.community.controller.MessageWSServer;
import com.fudan.se.community.controller.request.task.*;
import com.fudan.se.community.controller.response.GTasksMapResponse;
import com.fudan.se.community.controller.response.TaskMessage;
import com.fudan.se.community.service.AcceptService;
import com.fudan.se.community.service.InGroupService;
import com.fudan.se.community.service.RoomService;
import com.fudan.se.community.service.VGroupService;
import com.fudan.se.community.vm.GroupTask;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "AcceptTaskController")
@RestController
public class AcceptTaskController {
    /** accept **/
    // TODO: 2022/4/26  
    // 获取该课堂所有的任务 v_class(id) class_task task
    // 筛选条件：个人/团体

    @Autowired
    AcceptService acceptService;

    @Autowired
    VGroupService vGroupService;

    @Autowired
    InGroupService inGroupService;
    
    @ApiOperation(value="用户接受个人任务",notes = "insert accept table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "接受任务成功"),
            @ApiResponse(code = 400, message = "\"Task(taskId=\"+taskId+\") doesn't exists.\";" +
                    "\"user(userId=\"+userId+\") has accepted this group task(taskId\"+taskId+\")\"")
    })
    @RequestMapping(value = "/personalTaskOn", method = RequestMethod.POST)
    public ResponseEntity<Object> acceptPersonalTask(
            @ApiParam
            @RequestBody ListGroupTasksRequest acceptTaskRequest) {
        acceptService.acceptTask(acceptTaskRequest.getUserId(), acceptTaskRequest.getTaskId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="返回团队任务可以选择的团队",notes = "insert v_group, in_group table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = GTasksMapResponse.class),
            @ApiResponse(code = 400, message = "已经接受过该任务")
    })
    @RequestMapping(value = "/groupTaskList", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<GTasksMapResponse> listGroups(@RequestBody ListGroupTasksRequest request) {
        Integer userId = request.getUserId();
        Integer taskId = request.getTaskId();
        GTasksMapResponse res = new GTasksMapResponse(inGroupService.findGroups_taskId(userId, taskId));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // TODO: websocket
    @ApiOperation(value="用户加入团队任务，等待人数凑齐",notes = "insert v_group, in_group table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "加入任务成功"),
            @ApiResponse(code = 400, message = "已经接受过该任务")
    })
    @RequestMapping(value = "/groupTaskOn", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> acceptGroupTask(@RequestBody AcceptTaskRequest request) {
        Integer userId = request.getUserId();
        Integer groupId = request.getGroupId();
        inGroupService.acceptTask_group(userId, groupId);
        // get roomId
        Integer roomId = vGroupService.getRoomId_roomId(groupId);
        // websocket sendMessage
        TaskMessage message = new TaskMessage(userId.toString(), "Task status updates", roomId);
        MessageWSServer.sendMessageFrom(message, roomId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="设置组相关信息 组名/组长",notes = "update name/group_leader in v_group table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "组名/组长设置成功"),
            @ApiResponse(code = 400, message = "该组不存在")
    })
    @RequestMapping(value = "/assignGroupInfo", method = RequestMethod.PUT)
    public ResponseEntity<Object> assignGroupInfo(@RequestBody AssignGroupInfoRequest assignGroupInfoRequest) {
        return null;
    }

    /** submit **/
    // TODO: 2022/4/26  
    // 获取某用户所有的任务 user(id) accept task
    // 筛选条件：个人/团体/course_name

    /** personal **/
    @ApiOperation(value="提交个人任务",notes = "update process in accept table(task_id)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "已提交"),
            @ApiResponse(code = 400, message = "\"User doesn't accept this Personal Task before\";" +
                    "\"Task's(TaskId=\"+ taskId +\") ddl has reached\"")
    })
    @RequestMapping(value = "/submitPersonalTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> submitPersonalTask(MultipartFile file,
                                                     HttpServletRequest request,
                                                     Integer userId,
                                                     Integer taskId) {
        acceptService.submitTask_personal(userId, taskId, file, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /** group **/
    @ApiOperation(value="提交团队任务",notes = "update process in group table(group_id)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "已提交"),
            @ApiResponse(code = 400, message = "\"User doesn't accept this Personal Task before\";" +
                    "\"Task's(TaskId=\"+ taskId +\") ddl has reached\";" +
                    "\"User(UserId =\"+userId+\") doesn't in this group(groupId=\"+groupId+\")\"")
    })
    @RequestMapping(value = "/submitGroupTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> submitGroupTask(MultipartFile file,
                                                  HttpServletRequest request,
                                                  Integer userId,
                                                  Integer groupId) {
        vGroupService.submitTask_group(userId, groupId, file, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO: 2022/4/26
    // 组长 获取当前组的组员信息（个人贡献）
    @ApiOperation(value="组长分配经验值",notes = "update process in group table(group_id), update EV in user table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "已提交"),
            @ApiResponse(code = 400, message = "组员信息有误")
    })
    @RequestMapping(value = "/submitGroupTask/exp", method = RequestMethod.PUT)
    public ResponseEntity<Object> assignEV(@RequestBody AssignEVRequest assignEVRequest) {
        return null;
    }

}
