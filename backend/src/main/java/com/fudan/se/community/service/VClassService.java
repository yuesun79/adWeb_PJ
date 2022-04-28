package com.fudan.se.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudan.se.community.pojo.task.VClass;


public interface VClassService extends IService<VClass>  {
    // 根据classId查VClass @
    VClass getClass_classId(Integer classId);
}
