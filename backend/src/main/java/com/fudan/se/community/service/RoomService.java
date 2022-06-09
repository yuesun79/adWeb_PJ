package com.fudan.se.community.service;

import com.fudan.se.community.pojo.task.group.Room;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudan.se.community.pojo.user.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
public interface RoomService extends IService<Room> {
    Integer getGroupId_roomId(Integer roomId);
    List<Integer> findUsersInRoom(Integer roomId);

}
