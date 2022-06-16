package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.OccupyMapper;
import com.fudan.se.community.mapper.RoomMapper;
import com.fudan.se.community.mapper.VGroupMapper;
import com.fudan.se.community.pojo.task.group.InGroup;
import com.fudan.se.community.mapper.InGroupMapper;
import com.fudan.se.community.pojo.task.group.Occupy;
import com.fudan.se.community.pojo.task.group.Room;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.service.InGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.service.VGroupService;
import com.fudan.se.community.pojo.vm.GroupTask;
import com.fudan.se.community.pojo.vm.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class InGroupServiceImpl extends ServiceImpl<InGroupMapper, InGroup> implements InGroupService {

    @Autowired
    TaskServiceImpl taskService;
    @Autowired
    VGroupMapper vGroupMapper;
    @Autowired
    VGroupService vGroupService;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    OccupyMapper occupyMapper;

    @Override
    public boolean isTaskPersonal(Integer taskId) {
        // 检查任务是否存在
        Task task = taskService.findTask_id(taskId);
        if (task == null) throw new BadRequestException("Task(taskId="+taskId+") doesn't exists.");
        // 个人任务 insert accept
        return (task.getTeamSize() == 1);
    }

    @Override
    public Integer findRoomId_userIdAndTaskId(Integer userId, Integer taskId) {
        VGroup vGroup = vGroupMapper.findGroup_userIdAndTaskId(userId, taskId);
        if (vGroup == null)
            throw new BadRequestException("There isn't any group user(userId="+userId+") with this task" +
                    "(taskId"+taskId+")");
        return vGroupService.getRoomId_groupId(vGroup.getId());
    }

    @Override
    public List<VGroup> findGroups_taskId(Integer userId, Integer taskId) {
        if (isTaskPersonal(taskId))
            throw new BadRequestException("Task(taskId="+taskId+") is personal task");
        // in_group 是否已经接受过该任务
        VGroup group = baseMapper.ifUserAcceptTask_group(userId, taskId);
        if (group != null) {
            throw new BadRequestException("user(userId="+userId+") has accepted this group task(taskId"+taskId+")");
        }
        // 寻找是否有该任务对应的未满team_size的group
        List<VGroup> oldGroup = vGroupMapper.findGroups_taskId(taskId);
        // 不存在 insert v_group
        Room room = new Room();
        room.setName("new room");
        if (oldGroup.size() == 0) {
            VGroup group1 = new VGroup(taskId);
            vGroupMapper.insert(group1);
            // insert room
            roomMapper.insert(room);
            occupyMapper.insert(new Occupy(group1.getId(), room.getId()));

            oldGroup = vGroupMapper.findGroups_taskId(taskId);

        }
        return oldGroup;
    }

    @Override
    public Integer acceptTask_group(Integer userId, Integer groupId) {
        // 判断人数是否已满
        Integer teamSize = vGroupService.getTask_groupId(groupId).getTeamSize();

        VGroup vGroup = vGroupMapper.selectOne(new QueryWrapper<VGroup>()
                .lambda().eq(VGroup::getId, groupId)
                .lt(VGroup::getChecked, teamSize)); // VGroup::getProcess小于
        if (vGroup == null) {
            throw new BadRequestException("Group(GroupId="+groupId+")doesn't exist or already has enough people");
        }
        // 判断是否已经在组中
        // insert in_group
        InGroup inGroup = new InGroup(userId, groupId);
        if (baseMapper.selectOne(new QueryWrapper<>(inGroup)) != null) {
            throw new BadRequestException("User(userId="+userId+") already in group(groupId="+groupId+")");
        }
        baseMapper.insert(inGroup);
        // update memberNum
        vGroup.setChecked(vGroup.getChecked()+1);
        vGroupMapper.update(vGroup, new QueryWrapper<VGroup>()
                .lambda().eq(VGroup::getId,vGroup.getId()));
        return inGroup.getId();
    }

}
