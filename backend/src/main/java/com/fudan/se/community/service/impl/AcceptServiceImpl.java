package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.AcceptMapper;
import com.fudan.se.community.pojo.task.Accept;
import com.fudan.se.community.service.InGroupService;
import com.fudan.se.community.util.FileUtil;
import com.fudan.se.community.vm.Task;
import com.fudan.se.community.service.AcceptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.vm.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
@Service
public class AcceptServiceImpl extends ServiceImpl<AcceptMapper, Accept> implements AcceptService {

    @Autowired
    TaskServiceImpl taskService;

    @Autowired
    InGroupService inGroupService;

    @Override
    public void acceptTask(Integer userId, Integer taskId) {
        // 检查任务是否存在
        Task task = taskService.findTask_id(taskId);
        if (task == null) throw new BadRequestException("Task(taskId="+taskId+") doesn't exists.");
        if (task.getTeamSize() == 1) {
            acceptTask_personal(userId, taskId);
        }
        // 团队任务 insert in_group v_group
        else {
            inGroupService.acceptTask_group(userId, taskId);
        }
    }

    public boolean isTaskPersonal(Integer taskId) {
        // 检查任务是否存在
        Task task = taskService.findTask_id(taskId);
        if (task == null) throw new BadRequestException("Task(taskId="+taskId+") doesn't exists.");
        // 个人任务 insert accept
        return (task.getTeamSize() == 1);
    }

    @Override
    public void acceptTask_personal(Integer userId, Integer taskId) {
        // 检查是否已有该任务在进行状态
        Accept accept = baseMapper.selectOne(new QueryWrapper<Accept>().lambda()
                .eq(Accept::getTaskId, taskId)
                .eq(Accept::getUserId, userId));
        if (accept != null) throw new BadRequestException("User already accept this personal Task, process:"+ getTaskProcess(accept.getProcess()));
        // 个人任务 insert accept
        baseMapper.insert(new Accept(userId, taskId));
    }

    // todo: 管理员修改ddl
    // todo: 显示/下载已上传文件
    @Override
    public void submitTask_personal(Integer userId, Integer taskId, MultipartFile file, HttpServletRequest request) {
        // check whether is overdue
        taskService.checkOverDue(taskId);
        //todo: check upload file in cloud
        String fileName = FileUtil.upload(file, request);
        log.debug("--------->filename:"+fileName);
        // update
        if(!this.update(
                new Accept(1, fileName),
                new QueryWrapper<Accept>().lambda()
                        .eq(Accept::getUserId, userId)
                        .eq(Accept::getTaskId, taskId)))
            throw new BadRequestException("User doesn't accept this Personal Task before");
    }



    public String getTaskProcess(Integer process) {
        String res = "";
        switch (process) {
            case 0:
                res = "ongoing";
                break;
            case 1:
                res = "submitted";
                break;
            case 2:
                res = "done";
                break;
            case 3:
                res = "overtime";
                break;
            default:
                break;
        }
        return res;
    }
}
