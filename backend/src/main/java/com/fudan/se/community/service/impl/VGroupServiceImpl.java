package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.InGroupMapper;
import com.fudan.se.community.mapper.OccupyMapper;
import com.fudan.se.community.pojo.task.Accept;
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
@Service
public class VGroupServiceImpl extends ServiceImpl<VGroupMapper, VGroup> implements VGroupService {
    @Autowired
    InGroupMapper inGroupMapper;
    @Autowired
    InGroupService inGroupService;
    @Autowired
    TaskService taskService;
    @Autowired
    OccupyMapper occupyMapper;


    @Override
    public Integer getRoomId_roomId(Integer groupId) {
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
        inGroupService.checkUserInGroup(userId, groupId);
        // check whether overdue
        VGroup vGroup = baseMapper.selectById(groupId);
        taskService.checkOverDue(vGroup.getTaskId());
        //todo: check upload file in cloud
        String fileName = FileUtil.upload(file, request);
        log.debug("--------->filename:"+fileName);
        // check whether in group
        if(!this.update(new VGroup(1, fileName),
                new QueryWrapper<VGroup>().lambda()
                        .eq(VGroup::getId, groupId)))
            throw new BadRequestException("User doesn't accept this Group Task before");
    }
}
