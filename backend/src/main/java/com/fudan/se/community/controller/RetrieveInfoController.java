package com.fudan.se.community.controller;

import com.fudan.se.community.controller.response.ClassResponse;
import com.fudan.se.community.controller.response.PTasksMapResponse;
import com.fudan.se.community.controller.response.TasksResponse;
import com.fudan.se.community.pojo.user.User;
import com.fudan.se.community.service.UserService;
import com.fudan.se.community.service.VClassService;
import com.fudan.se.community.vm.Task;
import com.fudan.se.community.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags="RetrieveInfoController")
@RestController
public class RetrieveInfoController {
    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    VClassService vClassService;

    /** TASKS **/
    @ApiOperation(value="获取taskId对应的任务",notes = "select task table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = Task.class),
            @ApiResponse(code = 400, message = "Task(TaskId=xxx) doesn't exist")
    })

    @RequestMapping(value = "/retrieveTask/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> retrieveTask_id(@PathVariable Integer id) {
        Task task = taskService.findTask_id(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @ApiOperation(value="获取该课堂所有的任务",notes = "select task table join class_task join v_class")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = PTasksMapResponse.class),
            @ApiResponse(code = 400, message = "Class(classId=xxx) doesn't exist")
    })

    @RequestMapping(value = "/retrieveTasks/class", method = RequestMethod.GET)
    public ResponseEntity<PTasksMapResponse> retrieveTasks_class(@RequestParam Integer classId) {
        List<Task> list = taskService.retrieveAllTasks_class(classId);
        PTasksMapResponse res = new PTasksMapResponse(list);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ApiOperation(value="获取某用户所有的任务",notes = "select task table join accept join user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = TasksResponse.class),
            @ApiResponse(code = 400, message = "\"This user(userId = \"+ userId + \") doesn't exists\";")
    })
    @RequestMapping(value = "/retrieveTasks/user", method = RequestMethod.GET)
    public ResponseEntity<TasksResponse> retrieveTasks_user(Integer userId) {
        TasksResponse res = taskService.retrieveAllTasks_user(userId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ApiOperation(value="获取某用户信息",notes = "select user table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = User.class),
            @ApiResponse(code = 400, message = "\"User(UserId=\"+userId+\") doesn't exists\"")
    })
    @RequestMapping(value = "/retrieveUserInfo", method = RequestMethod.GET)
    public ResponseEntity<Object> retrieveUserInfo(Integer userId) {
        User user = userService.retrieveUserInfo(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(value="获取课堂信息",notes = "select vClass table")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClassResponse.class),
    })
    @RequestMapping(value = "/retrieveClasses", method = RequestMethod.GET)
    public ResponseEntity<ClassResponse> retrieveClass(Integer userId) {
        ClassResponse res = new ClassResponse(vClassService.getClasses());
        return new ResponseEntity<>(res, HttpStatus.OK);
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
    @RequestMapping(value = "/retrieveTasks/unfinishedFree", method = RequestMethod.GET)
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