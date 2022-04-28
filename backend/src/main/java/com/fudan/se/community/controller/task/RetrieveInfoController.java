package com.fudan.se.community.controller.task;

import com.fudan.se.community.controller.response.AcceptTaskResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api("RetrieveInfoController")
@RestController
public class RetrieveInfoController {

    /** TASKS **/
    @ApiOperation(value="获取该课堂所有的任务",notes = "select task table join class_task join v_class")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "classId不对")
    })

    @RequestMapping(value = "/retrieveTasks/class", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> retrieveTasks_class(@RequestParam long classId) {
        return null;
    }

    @ApiOperation(value="获取某用户所有的任务",notes = "select task table join accept join user")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对")
    })
    @RequestMapping(value = "/retrieveTasks/user", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> retrieveTasks_user(@RequestParam long userId) {
        return null;
    }

    // ADMIN // CHECK TASK //
    @ApiOperation(value="管理员获取未审核的所有的任务",notes = "select task")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对")
    })
    @RequestMapping(value = "admin/retrieveTasks/uncheck", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> retrieveTasks_unchecked() {
        return null;
    }

    // ADMIN // CHECK COMPLETION //
    @ApiOperation(value="管理员获取未审核完成度的个人任务",notes = "select task + accept + user")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对")
    })
    // todo 构造response
    // 想象了一下前端 task/userAccept
    @RequestMapping(value = "admin/retrieveTasks/unfinishedPersonal", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> retrieveTasks_unfinishedPersonal() {
        return null;
    }

    @ApiOperation(value="管理员获取未审核完成度的团队任务",notes = "select task + group + accept + user")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对")
    })
    // todo 构造response
    // 想象了一下前端 task/group/userAccept
    @RequestMapping(value = "admin/retrieveTasks/unfinishedGroup", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> retrieveTasks_unfinishedGroup() {
        return null;
    }

    @ApiOperation(value="user获取未审核完成度的自由任务",notes = "select task + accept + user")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对")
    })
    // todo 构造response
    // 想象了一下前端 task/userAccept
    @RequestMapping(value = "retrieveTasks/unfinishedFree", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> retrieveTasks_unfinishedFree(@RequestParam long userId) {
        return null;
    }



    /** USER **/
    @ApiOperation(value="获取当前组的组员信息（个人贡献）",notes = "select user table join in_group")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对")
    })
    @RequestMapping(value = "/retrieveGroupUsers", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> retrieveGroupUsers(@RequestParam long groupId) {
        return null;
    }

    /** GROUP **/
}
