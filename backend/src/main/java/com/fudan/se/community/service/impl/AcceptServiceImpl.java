package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.AcceptMapper;
import com.fudan.se.community.pojo.task.Accept;
import com.fudan.se.community.pojo.task.Task;
import com.fudan.se.community.service.AcceptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void acceptTask(Integer userId, Integer taskId) {
        // 检查任务是否存在
        Task task = taskService.findTask_id(taskId);
        if (task == null) throw new BadRequestException("Task(taskId="+taskId+") doesn't exists.");
        // 个人任务 insert accept
        if (task.getTeamSize() == 1) {
            acceptTask_personal(userId, taskId);
        }
        // 团队任务 insert in_group v_group
        else {

        }

    }

    public void acceptTask_personal(Integer userId, Integer taskId) {
        // 检查是否已有该任务在进行状态
        Accept accept = baseMapper.selectOne(new QueryWrapper<Accept>().lambda()
                .eq(Accept::getTaskId, taskId)
                .eq(Accept::getUserId, userId));
        if (accept != null) throw new BadRequestException("User already accept this personal Task, process:"+ getTaskProcess(accept.getProcess()));
        // 个人任务 insert accept
        baseMapper.insert(new Accept(userId, taskId));
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
