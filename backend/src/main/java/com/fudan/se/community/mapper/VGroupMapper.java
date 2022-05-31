package com.fudan.se.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudan.se.community.pojo.task.group.VGroup;
import com.fudan.se.community.vm.GroupTask;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VGroupMapper extends BaseMapper<VGroup> {
    GroupTask findGroup_taskId(Integer taskId);
}
