package com.fudan.se.community.controller;

import com.fudan.se.community.annotation.PassToken;
import com.fudan.se.community.controller.request.task.*;
import com.fudan.se.community.controller.response.GTasksMapResponse;
import com.fudan.se.community.pojo.message.TaskMessage;
import com.fudan.se.community.service.AcceptService;
import com.fudan.se.community.service.InGroupService;
import com.fudan.se.community.service.VGroupService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@Api(tags = "AcceptTaskController")
@RestController
public class AcceptTaskController {
    /** accept **/
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
                    "\"user(userId=\"+userId+\") has accepted this group task(taskId\"+taskId+\")\"" +
                    ";\"Task(taskId=\"+taskId+\") is group task\"")
    })
    @RequestMapping(value = "/personalTaskOn", method = RequestMethod.POST)
    public ResponseEntity<Object> acceptPersonalTask(
            @ApiParam
            @RequestBody ListGroupTasksRequest acceptTaskRequest) {
        acceptService.acceptTask_personal(acceptTaskRequest.getUserId(), acceptTaskRequest.getTaskId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="返回团队任务可以选择的团队",notes = "insert v_group, in_group table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = GTasksMapResponse.class),
            @ApiResponse(code = 400, message = "\"Task(taskId=\"+taskId+\") is personal task\"" +
                    ";\"user(userId=\"+userId+\") has accepted this group task(taskId\"+taskId+\")\"")
    })
    @RequestMapping(value = "/groupTaskList", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<GTasksMapResponse> listGroups(@RequestBody ListGroupTasksRequest request) {
        Integer userId = request.getUserId();
        Integer taskId = request.getTaskId();
        GTasksMapResponse res = new GTasksMapResponse(inGroupService.findGroups_taskId(userId, taskId));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ApiOperation(value="用户加入团队任务，等待人数凑齐",notes = "insert v_group, in_group table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "加入任务成功"),
            @ApiResponse(code = 400, message = "\"Group(GroupId=\"+groupId+\")doesn't exist or already has enough people\"" +
                    ";\"User(userId=\"+userId+\") already in group(groupId=\"+groupId+\")\"")
    })
    @RequestMapping(value = "/groupTaskOn", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> acceptGroupTask(@RequestBody AcceptTaskRequest request) {
        Integer userId = request.getUserId();
        Integer groupId = request.getGroupId();
        inGroupService.acceptTask_group(userId, groupId);
        // 加入团队成功 发送消息
        // get roomId
        Integer roomId = vGroupService. getRoomId_groupId(groupId);
        // websocket sendMessage
        TaskMessage message = new TaskMessage(userId, "Task status updates", roomId);
        MessageWSServer.sendMessageFrom(message, roomId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="设置组相关信息 组名/组长",notes = "update name/group_leader in v_group table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "组名/组长设置成功"),
            @ApiResponse(code = 400, message = "\"User(UserId =\"+userId+\") doesn't in this group(groupId=\"+groupId+\")\"")
    })
    @RequestMapping(value = "/assignGroupInfo", method = RequestMethod.PUT)
    public ResponseEntity<Object> assignGroupInfo(@RequestBody AssignGroupInfoRequest assignGroupInfoRequest) {
        vGroupService.updateGroupInfo(assignGroupInfoRequest.getGroupId(),
                assignGroupInfoRequest.getGroupLeader(),
                assignGroupInfoRequest.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /** submit **/

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

    @RequestMapping(value = "/downloadFile/personalTask", method = RequestMethod.GET)
    public void downloadPersonalFile(Integer userId, Integer taskId, HttpServletResponse response) {
        byte[] buffer = acceptService.getFile(userId, taskId);
        try {
            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            // Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            // attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("download", "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + buffer.length);
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/downloadFile/groupTask", method = RequestMethod.GET)
    public void downloadGroupFile(Integer userId, Integer groupId, HttpServletResponse response) {
        byte[] buffer = vGroupService.getFile(userId, groupId);
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("download", "UTF-8"));
            response.addHeader("Content-Length", "" + buffer.length);
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /** group **/
    @ApiOperation(value="提交团队任务",notes = "update process in group table(group_id)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "已提交"),
            @ApiResponse(code = 400, message = "\"User doesn't accept this Personal Task before\";" +
                    "\"Task's(TaskId=\"+ taskId +\") ddl has reached\";" +
                    "\"User(UserId =\"+userId+\") doesn't in this group(groupId=\"+groupId+\")\"" +
                    "\"No Authority: User(userId=\"+userId+\") isn't the GroupLeader\"")
    })
    @RequestMapping(value = "/submitGroupTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> submitGroupTask(MultipartFile file,
                                                  HttpServletRequest request,
                                                  Integer userId,
                                                  Integer groupId) {
        vGroupService.submitTask_group(userId, groupId, file, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 组长 获取当前组的组员信息（个人贡献）
    @ApiOperation(value="组长分配经验值",notes = "update process in group table(group_id), update EV in user table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "已提交"),
            @ApiResponse(code = 400, message = "\"No Authority: User(userId=\"+userId+\") isn't the GroupLeader\"" +
                    "\"GroupTask hasn't been checked\"" +
                    "\"GroupUsers doesn't match\"")
    })
    @RequestMapping(value = "/submitGroupTask/exp", method = RequestMethod.PUT)
    public ResponseEntity<Object> assignEV(@RequestBody AssignEVRequest assignEVRequest) {
        vGroupService.assignEV4GroupUsers(assignEVRequest.getUserId(),
                assignEVRequest.getGroupId(),
                assignEVRequest.getScore());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
