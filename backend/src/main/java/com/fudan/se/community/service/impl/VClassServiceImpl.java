package com.fudan.se.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.pojo.task.VClass;
import com.fudan.se.community.mapper.VClassMapper;
import com.fudan.se.community.service.VClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
@Service
public class VClassServiceImpl extends ServiceImpl<VClassMapper, VClass> implements VClassService {
    @Override
    public VClass getClass_classId(Integer classId) {
        // 根据classId查VClass @
        QueryWrapper<VClass> vClassQueryWrapper = new QueryWrapper<>();
        vClassQueryWrapper.lambda()
                .eq(VClass::getId, classId);
        return getOne(vClassQueryWrapper);
    }

    @Override
    public List<VClass> getClasses() {
        // 查所有VClass @
        QueryWrapper<VClass> vClassQueryWrapper = new QueryWrapper<>();
        vClassQueryWrapper.lambda();
        return baseMapper.selectList(vClassQueryWrapper);
    }
}
