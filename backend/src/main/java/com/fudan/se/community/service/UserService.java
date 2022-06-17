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

    String isExist(String userName);

    User retrieveUserInfo(Integer userId);

    void changeUserInfo(Integer userId, String status);

    void cutEv(int userId, Integer ev);

    void addEv(int userId, int ev);
}
