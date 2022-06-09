package com.fudan.se.community.controller;

import com.fudan.se.community.controller.request.task.ListGroupTasksRequest;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags = "RoomController")
@Controller
public class RoomController {
    @ApiOperation(value="获取用户所在的房间",notes = "insert accept table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "")
    })
    @RequestMapping(value = "/getRoomInfo", method = RequestMethod.GET)
    public ResponseEntity<Object> acceptPersonalTask(
            @ApiParam Integer userId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
