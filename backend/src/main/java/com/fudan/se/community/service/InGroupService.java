package com.fudan.se.community.service;

import com.fudan.se.community.pojo.task.group.InGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.pojo.user.User;
import com.fudan.se.community.vm.GroupTask;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
public interface InGroupService extends IService<InGroup> {
    List<VGroup> findGroups_taskId(Integer userId, Integer taskId);
    Integer acceptTask_group(Integer userId, Integer groupId);
    boolean isTaskPersonal(Integer taskId);
    Integer findRoomId_userIdAndTaskId(Integer userId, Integer taskId);
}
