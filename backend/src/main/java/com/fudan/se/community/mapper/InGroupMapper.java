package com.fudan.se.community.mapper;

import com.fudan.se.community.pojo.task.group.InGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.pojo.user.User;
import com.fudan.se.community.vm.GroupTask;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
@Mapper
@Repository
public interface InGroupMapper extends BaseMapper<InGroup> {
    VGroup ifUserAcceptTask_group(Integer userId, Integer taskId);
    List<User> getUsersInGroup(Integer groupId);
}
