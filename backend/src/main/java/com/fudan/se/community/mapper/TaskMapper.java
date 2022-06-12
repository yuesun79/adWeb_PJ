package com.fudan.se.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudan.se.community.pojo.vm.GroupTask;
import com.fudan.se.community.pojo.vm.Task;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaskMapper extends BaseMapper<com.fudan.se.community.pojo.task.Task> {
    List<Task> retrieveTask_classId(Integer classId);
    List<Task> retrieveTasks_userId_accept(Integer userId);
    List<GroupTask> retrieveTasks_userId_inGroup(Integer userId);
    Task findTask_id(Integer taskId);
}
