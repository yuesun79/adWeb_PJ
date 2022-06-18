package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.AcceptMapper;
import com.fudan.se.community.pojo.task.Accept;
import com.fudan.se.community.service.InGroupService;
import com.fudan.se.community.util.FileUtil;
import com.fudan.se.community.service.AcceptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.pojo.vm.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
@Slf4j
@Service
public class AcceptServiceImpl extends ServiceImpl<AcceptMapper, Accept> implements AcceptService {

    @Autowired
    TaskServiceImpl taskService;

    @Autowired
    InGroupService inGroupService;


    @Override
    public Integer acceptTask_personal(Integer userId, Integer taskId) {
        if (!inGroupService.isTaskPersonal(taskId))
            throw new BadRequestException("Task(taskId="+taskId+") is group task");
        // 检查是否已有该任务在进行状态
        Accept accept = baseMapper.selectOne(new QueryWrapper<Accept>().lambda()
                .eq(Accept::getTaskId, taskId)
                .eq(Accept::getUserId, userId));
        if (accept != null)
            throw new BadRequestException("User already accept this personal Task, process:"+ getTaskProcess(accept.getChecked()));
        // 个人任务 insert accept
        Accept accept1 = new Accept();
        accept1.setUserId(userId);
        accept1.setTaskId(taskId);
        baseMapper.insert(accept1);

        return accept1.getId();
    }

    // todo: 管理员修改ddl
    // todo: 显示/下载已上传文件
    @Override
    public Integer submitTask_personal(Integer userId, Integer taskId, MultipartFile file, HttpServletRequest request) {
        // check whether is overdue
        taskService.checkOverDue(taskId);
        //todo: check upload file in cloud
        String absoluteFileName = FileUtil.upload(file, request);
        String fileName = file.getOriginalFilename();
        log.info("--------->absoluteFilename:"+absoluteFileName);
        log.info("--------->filename:"+fileName);

        Accept accept = new Accept();
        accept.setChecked(1);
        accept.setFile(fileName);
        accept.setPath(absoluteFileName);

        // update
        if(!this.update(
                accept,
                new QueryWrapper<Accept>().lambda()
                        .eq(Accept::getUserId, userId)
                        .eq(Accept::getTaskId, taskId)))
            throw new BadRequestException("User doesn't accept this Personal Task before");
        return accept.getId();
    }

    @Override
    public byte[] getFile(Integer userId, Integer taskId) {
        Accept accept = baseMapper.selectOne(new QueryWrapper<Accept>().lambda()
                .eq(Accept::getUserId, userId)
                .eq(Accept::getTaskId, taskId));
        if (accept == null)
            throw new BadRequestException("User doesn't accept this Personal Task before");
        return FileUtil.download(accept.getPath());
    }

    //检查是否已完成
    @Override
    public void checkCompletion(int userId, int taskId) {
        if(!this.update(
                new Accept(2),
                new QueryWrapper<Accept>().lambda()
                        .eq(Accept::getUserId, userId)
                        .eq(Accept::getTaskId, taskId)))
            throw new BadRequestException("User doesn't complete this Personal Task before");
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
