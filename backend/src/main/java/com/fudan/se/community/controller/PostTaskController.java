package com.fudan.se.community.controller;

import com.fudan.se.community.controller.request.task.post.CheckGTaskCompleteRequest;
import com.fudan.se.community.controller.request.task.post.CheckPTaskCompleteRequest;
import com.fudan.se.community.pojo.task.Task;
import com.fudan.se.community.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags="PostTaskController")
@RestController
public class PostTaskController {
    @Autowired
    TaskService taskService;
    /** post **/
    // student 需要验证 只能是个人任务
    @ApiOperation(value="发布自由任务",notes = "insert task table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对/信息不全等")
    })

    // todo：
    @RequestMapping(value = "/createFreeTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> createFreeTask(Task task) {
        taskService.insertTask(task);
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
        return null;
    }

    // admin 不用审核 可以是团体任务
    @ApiOperation(value="管理员发布个人任务",notes = "insert task table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对/信息不全等")
    })

    @RequestMapping(value = "admin/createPersonalTask", method = RequestMethod.POST)
    public ResponseEntity<Object> createPersonalTask(Task task) {
        return null;
    }

    @ApiOperation(value="管理员发布团队任务",notes = "insert task table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "userId不对/信息不全等")
    })

    @RequestMapping(value = "admin/createGroupTask", method = RequestMethod.POST)
    public ResponseEntity<Object> createGroupTask(Task task) {
        return null;
    }

    /** review **/
    // freeTask //
    @ApiOperation(value="管理员审核自由任务",notes = "update task table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "信息不对balabala")
    })

    @RequestMapping(value = "admin/checkFreeTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> checkFreeTask(@RequestParam Integer taskId) {
        return null;
    }

    // COMPLETION // ADMIN // personal //
    @ApiOperation(value="管理员审核个人任务完成",notes = "update accept table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "信息不对balabala")
    })

    @RequestMapping(value = "admin/checkCompletion/personalTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> checkCompletion_personal(@RequestBody CheckPTaskCompleteRequest checkPTaskCompleteRequest) {
        return null;
    }

    // group //
    @ApiOperation(value="管理员审核团队任务完成",notes = "update v_group table")
    @ApiResponses({
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "信息不对balabala")
    })

    @RequestMapping(value = "admin/checkCompletion/groupTask", method = RequestMethod.PUT)
    public ResponseEntity<Object> checkCompletion_group(@RequestBody CheckGTaskCompleteRequest checkGTaskCompleteRequest) {
        return null;
    }
}
