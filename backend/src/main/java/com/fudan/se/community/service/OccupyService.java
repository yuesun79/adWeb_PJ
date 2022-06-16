package com.fudan.se.community.service;

import com.fudan.se.community.pojo.task.group.Occupy;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
public interface OccupyService extends IService<Occupy> {

     void romove(int groupId) ;

    void insert(Occupy occupy);
}
