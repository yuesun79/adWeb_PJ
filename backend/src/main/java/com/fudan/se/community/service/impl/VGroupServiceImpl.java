package com.fudan.se.community.service.impl;

import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.mapper.VGroupMapper;
import com.fudan.se.community.service.VGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public void acceptTask_group(Integer userId, Integer taskId) {

    }
}
