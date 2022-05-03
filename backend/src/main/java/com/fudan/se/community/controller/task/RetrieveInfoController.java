package com.fudan.se.community.controller.task;

import com.fudan.se.community.controller.response.AcceptTaskResponse;
import com.fudan.se.community.pojo.task.Task;
import com.fudan.se.community.service.TaskService;
import com.fudan.se.community.service.impl.TaskServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags="RetrieveInfoController")
@RestController
public class RetrieveInfoController {
    @Autowired
    TaskService taskService;

    /** TASKS **/
    @ApiOperation(value="获取该课堂所有的任务",notes = "select task table join class_task join v_class")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "Class(classId=xxx) doesn't exist")
    })

    @RequestMapping(value = "/retrieveTasks/class", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> retrieveTasks_class(@RequestParam Integer classId) {
        Map<String, Object> map = new HashMap<>();
        List<Task> list = taskService.retrieveAllTasks_class(classId);
        map.put("result", list);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ApiOperation(value="获取某用户所有的任务",notes = "select task table join accept join user")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对")
    })
    @RequestMapping(value = "/retrieveTasks/user", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> retrieveTasks_user(@RequestParam Integer userId) {
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
    public ResponseEntity<Map<String, Object>> retrieveTasks_unfinishedFree(@RequestParam Integer userId) {
        return null;
    }



    /** USER **/
    @ApiOperation(value="获取当前组的组员信息（个人贡献）",notes = "select user table join in_group")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对")
    })
    @RequestMapping(value = "/retrieveGroupUsers", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> retrieveGroupUsers(@RequestParam Integer groupId) {
        return null;
    }

    /** GROUP **/
}
