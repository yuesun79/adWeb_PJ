package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.InGroupMapper;
import com.fudan.se.community.mapper.OccupyMapper;
import com.fudan.se.community.mapper.UserMapper;
import com.fudan.se.community.pojo.task.Accept;
import com.fudan.se.community.pojo.task.Task;
import com.fudan.se.community.pojo.task.group.InGroup;
import com.fudan.se.community.pojo.task.group.Occupy;
import com.fudan.se.community.pojo.task.group.Room;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.mapper.VGroupMapper;
import com.fudan.se.community.service.InGroupService;
import com.fudan.se.community.service.TaskService;
import com.fudan.se.community.service.VGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.util.FileUtil;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
public class VGroupServiceImpl extends ServiceImpl<VGroupMapper, VGroup> implements VGroupService {
    @Autowired
    InGroupMapper inGroupMapper;
    @Autowired
    TaskService taskService;
    @Autowired
    OccupyMapper occupyMapper;


    @Override
    public void checkCompletion(int groupId) {
        if(!this.update(
                new VGroup(2),
                new QueryWrapper<VGroup>().lambda()
                        .eq(VGroup::getId, groupId)))
            throw new BadRequestException("group doesn't complete this Task dont exist");
        System.out.println("------------删除成功");
    }

    @Override
    public Integer getRoomId_groupId(Integer groupId) {
        VGroup vGroup = baseMapper.selectById(groupId);
        if (vGroup == null) {
            throw new BadRequestException("Group(GroupId="+groupId+") doesn't exist");
        }
        Occupy occupy = occupyMapper.selectOne(new QueryWrapper<Occupy>().lambda().eq(Occupy::getGroupId, groupId));

        return occupy.getRoomId();
    }

    @Override
    public void submitTask_group(Integer userId, Integer groupId, MultipartFile file, HttpServletRequest request) {
        // check whether in group
        checkUserInGroup(userId, groupId);
        // check whether overdue
        VGroup vGroup = baseMapper.selectById(groupId);
        taskService.checkOverDue(vGroup.getTaskId());
        // check whether groupLeader
        if (!userId.equals(vGroup.getGroupLeader()))
            throw new BadRequestException("No Authority: User(userId="+userId+") isn't the GroupLeader");
        //todo: check upload file in cloud
        String fileName = FileUtil.upload(file, request);
        log.debug("--------->filename:"+fileName);
        // check whether in group
        if(!this.update(new VGroup(1, fileName),
                new QueryWrapper<VGroup>().lambda()
                        .eq(VGroup::getId, groupId)))
            throw new BadRequestException("User doesn't accept this Group Task before");
    }

    @Override
    public Task getTask_groupId(Integer groupId) {
        VGroup vGroup = baseMapper.selectById(groupId);
        log.info("Task--:"+taskService.getById(vGroup.getTaskId()).toString());
        return taskService.getById(vGroup.getTaskId());
    }

    @Override
    public void checkUserInGroup(Integer userId, Integer groupId) {
        if (inGroupMapper.selectOne(new QueryWrapper<InGroup>().
                lambda()
                .eq(InGroup::getGroupId, groupId)
                .eq(InGroup::getUserId, userId)) == null)
            throw new BadRequestException("User(UserId ="+userId+") doesn't in this group(groupId="+groupId+")");
    }

    @Override
    public void updateGroupInfo(Integer groupId, Integer groupLeader, String name) {
        // if groupLeader in this group
        checkUserInGroup(groupLeader, groupId);

        VGroup group = new VGroup();
        group.setId(groupId);
        group.setGroupLeader(groupLeader);
        group.setName(name);
        update(group, new QueryWrapper<VGroup>().lambda().eq(VGroup::getId, groupId));
    }

    @Override
    public void insert(VGroup vGroup) {
        int influenceRows = baseMapper.insert(vGroup);
        if (influenceRows==0) {
            throw new BadRequestException("Vgroup setup fails");
        }
    }
}
