package com.fudan.se.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudan.se.community.pojo.task.VClass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VClassMapper extends BaseMapper<VClass> {
}
