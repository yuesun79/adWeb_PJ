package com.fudan.se.community.service;

import com.fudan.se.community.pojo.task.group.VGroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
public interface VGroupService extends IService<VGroup> {
    void submitTask_group(Integer groupId, Integer taskId, byte[] file);
}
