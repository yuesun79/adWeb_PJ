package com.fudan.se.community.controller.task;

import com.fudan.se.community.controller.request.task.*;
import com.fudan.se.community.controller.response.AcceptTaskResponse;
import com.fudan.se.community.service.AcceptService;
import com.fudan.se.community.service.VGroupService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    @ApiOperation(value="用户接受个人任务",notes = "insert accept table")
    @ApiResponses({
            @ApiResponse(code = 201, message = "接受任务成功"),
            @ApiResponse(code = 400, message = "已经接受过该任务")
    })
    @RequestMapping(value = "/personalTaskOn", method = RequestMethod.POST)
    public ResponseEntity<Object> acceptPersonalTask(
            @ApiParam
            @RequestBody AcceptTaskRequest acceptTaskRequest) {
        acceptService.acceptTask(acceptTaskRequest.getUserId(), acceptTaskRequest.getTaskId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO: websocket
    @ApiOperation(value="用户加入团队任务，等待人数凑齐",notes = "insert v_group, in_group table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "加入任务成功"),
            @ApiResponse(code = 201, message = "接受任务成功"),
            @ApiResponse(code = 400, message = "已经接受过该任务")
    })
    @RequestMapping(value = "/groupTaskOn", method = RequestMethod.POST)
    public @ResponseBody AcceptTaskResponse acceptGroupTask(@RequestBody AcceptTaskRequest acceptTaskRequest) {
        return null;
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
            @ApiResponse(code = 400, message = "该任务不存在")
    })
    @RequestMapping(value = "/submitPersonalTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> submitPersonalTask(@RequestBody SubmitPTaskRequest submitPTaskRequest) {
        acceptService.submitTask_personal(
                submitPTaskRequest.getUserId(),
                submitPTaskRequest.getTaskId(),
                submitPTaskRequest.getFile());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /** group **/
    @ApiOperation(value="提交团队任务",notes = "update process in group table(group_id)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "已提交"),
            @ApiResponse(code = 400, message = "该任务不存在")
    })
    @RequestMapping(value = "/submitGroupTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> submitGroupTask(@RequestBody SubmitGTaskRequest submitGTaskRequest) {
        vGroupService.submitTask_group(
                submitGTaskRequest.getGroupId(),
                submitGTaskRequest.getTaskId(),
                submitGTaskRequest.getFile());
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
