package com.fudan.se.community.service.impl;

import com.fudan.se.community.pojo.task.group.InGroup;
import com.fudan.se.community.mapper.InGroupMapper;
import com.fudan.se.community.service.InGroupService;
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
public class InGroupServiceImpl extends ServiceImpl<InGroupMapper, InGroup> implements InGroupService {

    @Override
    public void acceptTask_group(Integer userId, Integer taskId) {
        // 寻找是否有该任务对应的未满teamsize的group

        // 存在 insert in_group

        // 不存在 insert v_group in_group
    }
}
