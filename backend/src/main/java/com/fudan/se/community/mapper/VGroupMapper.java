package com.fudan.se.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.pojo.vm.GroupTask;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VGroupMapper extends BaseMapper<VGroup> {
    List<VGroup> findGroups_taskId(Integer taskId);
    VGroup findGroup_userIdAndTaskId(Integer userId, Integer taskId);
}
