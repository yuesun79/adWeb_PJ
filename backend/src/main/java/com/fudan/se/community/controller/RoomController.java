package com.fudan.se.community.controller;

import com.fudan.se.community.annotation.PassToken;
import com.fudan.se.community.controller.request.task.ListGroupTasksRequest;
import com.fudan.se.community.service.InGroupService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags = "RoomController")
@Controller
public class RoomController {
    @Autowired
    InGroupService inGroupService;
    @ApiOperation(value="获取用户所在的房间",notes = "insert accept table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "")
    })
    @RequestMapping(value = "/getRooms", method = RequestMethod.GET)
    public ResponseEntity<Object> getRooms(
            @ApiParam Integer userId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="获取用户某个团队任务对应的roomId",notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "roomId"),
            @ApiResponse(code = 400, message = "\"There isn't any group user(userId=\"+userId+\") with this task(taskId\"+taskId+\")\"")
    })
    @RequestMapping(value = "/task/getRoomId", method = RequestMethod.GET)
    public ResponseEntity<Object> getRoomId_taskId(
            @ApiParam Integer userId, Integer taskId) {
            Integer roomId = inGroupService.findRoomId_userIdAndTaskId(userId, taskId);
        return new ResponseEntity<>(roomId, HttpStatus.OK);
    }

    @ApiOperation(value="获取用户某个团队任务对应的groupId",notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "groupId"),
            @ApiResponse(code = 400, message = "\"There isn't any group user(userId=\"+userId+\") with this task(taskId\"+taskId+\")\"")
    })
    @RequestMapping(value = "/task/getGroupId", method = RequestMethod.GET)
    public ResponseEntity<Object> getGroupId_taskId(
            @ApiParam Integer userId, Integer taskId) {
        Integer groupId = inGroupService.findGroupId_userIdAndTaskId(userId, taskId);
        return new ResponseEntity<>(groupId, HttpStatus.OK);
    }
}
