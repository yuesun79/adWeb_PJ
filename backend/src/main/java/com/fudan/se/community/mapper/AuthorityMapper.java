package com.fudan.se.community.mapper;

import com.fudan.se.community.pojo.user.Authority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
public interface AuthorityMapper extends BaseMapper<Authority> {

}
