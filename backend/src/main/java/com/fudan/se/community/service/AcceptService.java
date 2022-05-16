package com.fudan.se.community.service;

import com.fudan.se.community.pojo.task.Accept;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
public interface AcceptService extends IService<Accept> {
    void acceptTask(Integer userId, Integer taskId);
    boolean isTaskPersonal(Integer taskId);
    void acceptTask_personal(Integer userId, Integer taskId);
    void acceptTask_group(Integer userId,  Integer taskId);

    void submitTask_personal(Integer userId, Integer taskId, byte[] file);
}
