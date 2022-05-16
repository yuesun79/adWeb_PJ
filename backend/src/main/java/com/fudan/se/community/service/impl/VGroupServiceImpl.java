package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.InGroupMapper;
import com.fudan.se.community.pojo.task.Accept;
import com.fudan.se.community.pojo.task.group.InGroup;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.mapper.VGroupMapper;
import com.fudan.se.community.service.VGroupService;
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
public class VGroupServiceImpl extends ServiceImpl<VGroupMapper, VGroup> implements VGroupService {
    @Autowired
    InGroupMapper inGroupMapper;
    public void acceptTask_group(Integer userId, Integer taskId) {

    }

    @Override
    public void submitTask_group(Integer groupId, Integer taskId, byte[] file) {
        if(!this.update(new VGroup(groupId, taskId),
                new QueryWrapper<VGroup>().lambda()
                        .eq(VGroup::getProcess, 1)
                        .eq(VGroup::getFile, file)))
            throw new BadRequestException("User doesn't accept this Group Task before");
    }
}
