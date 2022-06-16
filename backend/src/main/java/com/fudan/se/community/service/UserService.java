package com.fudan.se.community.service;

import com.fudan.se.community.dto.LoginDto;
import com.fudan.se.community.dto.RegisterDto;
import com.fudan.se.community.pojo.user.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dq
 * @since 2021-05-24
 */
public interface UserService extends IService<User> {

    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);

    String isExist(RegisterDto registerDto);

    User retrieveUserInfo(Integer userId);

    void changeUserInfo(Integer userId, String status);
}
