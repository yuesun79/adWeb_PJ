package com.fudan.se.community.mapper;

import com.fudan.se.community.pojo.user.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dq
 * @since 2021-05-24
 */

@Repository //这个注释可以不加，以及添加了MapperScan会自动扫描
public interface UserMapper extends BaseMapper<User> {

    @Select("select id from user where username = #{username}")
    String selectByUsername(String username);

    @Select("select password from user where username = #{username}")
    String selectPdByUsername(String username);
}
