package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.VGroupMapper;
import com.fudan.se.community.pojo.task.group.InGroup;
import com.fudan.se.community.mapper.InGroupMapper;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.service.InGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.vm.GroupTask;
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
public class InGroupServiceImpl extends ServiceImpl<InGroupMapper, InGroup> implements InGroupService {

    @Autowired
    VGroupMapper vGroupMapper;

    @Override
    public void acceptTask_group(Integer userId, Integer taskId) {
        // in_group 是否已经接受过该任务
        VGroup group = baseMapper.ifUserAcceptTask_group(userId, taskId);
        if (group != null) {
            throw new BadRequestException("user(userId="+userId+") has accepted this group task(taskId"+taskId+")");
        }
        // 寻找是否有该任务对应的未满team_size的group
        GroupTask oldGroup = vGroupMapper.findGroup_taskId(taskId);
        // 不存在 insert v_group
        if (oldGroup == null) {

            vGroupMapper.insert(new VGroup(taskId));
            oldGroup = vGroupMapper.findGroup_taskId(taskId);
        }
        // insert in_group
        baseMapper.insert(new InGroup(userId, oldGroup.getId()));
        // update memberNum
        vGroupMapper.update(new VGroup(oldGroup.getGroupId()), new QueryWrapper<VGroup>()
                .lambda().eq(VGroup::getProcess,oldGroup.getGroupProcess()+1));
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
