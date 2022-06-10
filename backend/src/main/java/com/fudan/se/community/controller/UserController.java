package com.fudan.se.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudan.se.community.annotation.PassToken;
import com.fudan.se.community.annotation.UserLoginToken;
import com.fudan.se.community.common.CommonResult;
import com.fudan.se.community.dto.LoginDto;
import com.fudan.se.community.dto.RegisterDto;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.mapper.UserMapper;
import com.fudan.se.community.pojo.user.User;
import com.fudan.se.community.service.UserService;
import com.fudan.se.community.util.MD5Utils;
import com.fudan.se.community.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
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
    @PassToken
    public CommonResult login(@RequestBody LoginDto loginDto) throws JSONException {
        log.info(loginDto.toString());

        String ans = userService.login(loginDto);
        if(ans.equals("用户名密码正确")){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(loginDto.getUsername() != null, "username", loginDto.getUsername());
            User user = userService.getOne(queryWrapper);
            String s = MD5Utils.code(loginDto.getPassword());
            String token = TokenUtil.sign(new User(loginDto.getUsername(),s));
            HashMap<String,Object> hs =new HashMap<>();
            hs.put("token",token);
            hs.put("userid",user.getId());
            return new CommonResult(String.valueOf(HttpStatus.OK.value()),"成功",hs);
        }
        throw new BadRequestException(ans);
    }

    @RequestMapping  ("/info")
    @UserLoginToken(required = true)
    public String getInfo(){
        return "Token success";
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

