package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.InGroupMapper;
import com.fudan.se.community.mapper.OccupyMapper;
import com.fudan.se.community.pojo.task.group.Occupy;
import com.fudan.se.community.pojo.task.group.Room;
import com.fudan.se.community.mapper.RoomMapper;
import com.fudan.se.community.pojo.user.User;
import com.fudan.se.community.service.RoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.service.VGroupService;
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
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {
    @Autowired
    OccupyMapper occupyMapper;
    @Autowired
    InGroupMapper inGroupMapper;
    @Autowired
    VGroupService vGroupService;

    @Override
    public Integer getGroupId_roomId(Integer roomId) {
        Room room = baseMapper.selectById(roomId);
        if (room == null) {
            throw new BadRequestException("Room(RoomId="+roomId+") doesn't exist");
        }
        Occupy occupy = occupyMapper.selectOne(new QueryWrapper<Occupy>().lambda().eq(Occupy::getRoomId, roomId));

        return occupy.getGroupId();
    }

    @Override
    public List<User> findUsersInRoom(Integer roomId) {
        Integer groupId = getGroupId_roomId(roomId);
        return inGroupMapper.getUsersInGroup(groupId);
    }

    @Override
    public Integer getTeamSize_roomId(Integer roomId) {
        Integer groupId = getGroupId_roomId(roomId);
        return vGroupService.getTask_groupId(groupId).getTeamSize();
    }

}
