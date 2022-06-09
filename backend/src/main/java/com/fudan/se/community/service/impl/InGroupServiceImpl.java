package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.OccupyMapper;
import com.fudan.se.community.mapper.RoomMapper;
import com.fudan.se.community.mapper.VGroupMapper;
import com.fudan.se.community.pojo.task.group.InGroup;
import com.fudan.se.community.mapper.InGroupMapper;
import com.fudan.se.community.pojo.task.group.Occupy;
import com.fudan.se.community.pojo.task.group.Room;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.pojo.user.User;
import com.fudan.se.community.service.InGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.service.RoomService;
import com.fudan.se.community.vm.GroupTask;
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
@Service
public class InGroupServiceImpl extends ServiceImpl<InGroupMapper, InGroup> implements InGroupService {

    @Autowired
    VGroupMapper vGroupMapper;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    OccupyMapper occupyMapper;

    @Override
    public List<VGroup> findGroups_taskId(Integer userId, Integer taskId) {
        // in_group 是否已经接受过该任务
        VGroup group = baseMapper.ifUserAcceptTask_group(userId, taskId);
        if (group != null) {
            throw new BadRequestException("user(userId="+userId+") has accepted this group task(taskId"+taskId+")");
        }
        // 寻找是否有该任务对应的未满team_size的group
        List<VGroup> oldGroup = vGroupMapper.findGroup_taskId(taskId);
        // 不存在 insert v_group
        Room room = new Room();
        if (oldGroup.size() == 0) {
            VGroup group1 = new VGroup(taskId);
            vGroupMapper.insert(group1);
            // insert room
            roomMapper.insert(room);
            occupyMapper.insert(new Occupy(group1.getId(), room.getId()));

            oldGroup = vGroupMapper.findGroup_taskId(taskId);

        }
        return oldGroup;
    }

    @Override
    public Integer acceptTask_group(Integer userId, Integer groupId) {
        VGroup vGroup = vGroupMapper.selectById(groupId);
        if (groupId == null)
            throw new BadRequestException("Group(GroupId="+groupId+")doesn't exist");
        // insert in_group
        InGroup inGroup = new InGroup(userId, groupId);
        baseMapper.insert(inGroup);
        // update memberNum
        vGroupMapper.update(new VGroup(groupId), new QueryWrapper<VGroup>()
                .lambda().eq(VGroup::getProcess,vGroup.getProcess()+1));
        return inGroup.getId();
    }

    @Override
    public void checkUserInGroup(Integer userId, Integer groupId) {
        if (baseMapper.selectOne(new QueryWrapper<InGroup>().
                lambda()
                .eq(InGroup::getGroupId, groupId)
                .eq(InGroup::getUserId, userId)) == null)
            throw new BadRequestException("User(UserId ="+userId+") doesn't in this group(groupId="+groupId+")");
    }
}
