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
    public void acceptTask(Integer userId, Integer taskId);
}
