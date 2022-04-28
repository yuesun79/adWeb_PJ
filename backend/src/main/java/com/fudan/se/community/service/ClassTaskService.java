package com.fudan.se.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.mapper.ClassTaskMapper;
import com.fudan.se.community.pojo.task.ClassTask;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
@Service
public class ClassTaskService extends ServiceImpl<ClassTaskMapper, ClassTask> {
    public Integer getTaskId_classId(Integer classId) {
        // 根据classId查class_task @
        QueryWrapper<ClassTask> classTaskQueryWrapper = new QueryWrapper<>();
        classTaskQueryWrapper.lambda()
                .eq(ClassTask::getClassId, classId);
        return getOne(classTaskQueryWrapper).getTaskId();
    }
}
