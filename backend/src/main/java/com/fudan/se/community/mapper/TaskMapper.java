package com.fudan.se.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudan.se.community.vm.GroupTask;
import com.fudan.se.community.vm.Task;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaskMapper extends BaseMapper<Task> {
    List<Task> retrieveTask_classId(Integer classId);
    List<Task> retrieveTasks_userId_accept(Integer userId);
    List<GroupTask> retrieveTasks_userId_inGroup(Integer userId);
}
