package com.fudan.se.community.service.impl;

import com.fudan.se.community.pojo.user.User;
import com.fudan.se.community.mapper.UserMapper;
import com.fudan.se.community.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SY
 * @since 2022-04-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
