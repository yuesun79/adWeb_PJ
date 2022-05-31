package com.fudan.se.community.service;

import com.fudan.se.community.pojo.task.group.InGroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
public interface InGroupService extends IService<InGroup> {
    public void acceptTask_group(Integer userId, Integer taskId);
    void checkUserInGroup(Integer userId, Integer groupId);
}
