package com.fudan.se.community.controller;


import com.fudan.se.community.common.CommonResult;
import com.fudan.se.community.dto.LoginDto;
import com.fudan.se.community.dto.RegisterDto;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oxygen
 * @since 2021-05-24
 */
@RestController
@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户注册
     */
    @RequestMapping("/register")//@Validated
    public CommonResult register( @RequestBody RegisterDto registerDto){
        log.info(registerDto.toString());

        String obj = userService.register(registerDto);
        if(obj.equals("注册成功")){
            return CommonResult.success(obj);
        }else{
            throw new BadRequestException(obj);
        }

    }

    /**
     * 用户登录
     */
    @RequestMapping("/login")
    public CommonResult login(@RequestBody LoginDto loginDto){
        log.info(loginDto.toString());

        String ans = userService.login(loginDto);
        if(ans.equals("用户名密码正确")){
            return CommonResult.success(ans);
        }
        throw new BadRequestException(ans);
    }
    @RequestMapping("/username")
    public CommonResult username(@RequestBody RegisterDto registerDto){
        log.info(registerDto.toString());

        String ans = userService.isExist(registerDto);
        if(ans.equals("用户名已经存在")){
            throw new BadRequestException(ans);
        }
       return CommonResult.success(ans);
    }
}

