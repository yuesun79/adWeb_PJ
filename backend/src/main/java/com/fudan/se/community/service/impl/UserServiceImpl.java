package com.fudan.se.community.service.impl;

import com.fudan.se.community.dto.LoginDto;
import com.fudan.se.community.dto.RegisterDto;
import com.fudan.se.community.exception.BadRequestException;
import com.fudan.se.community.pojo.user.User;
import com.fudan.se.community.mapper.UserMapper;
import com.fudan.se.community.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudan.se.community.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oxygen
 * @since 2021-05-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public String register(RegisterDto ro) {
        String ans = istrue(ro);
        if(ans.equals("OK")){
            User user = new User();
            user.setUsername(ro.getUsername());
            user.setPassword(MD5Utils.code(ro.getPassword()));
            user.setGender(ro.getGender());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String time= df.format(new Date());

            Timestamp ts= Timestamp.valueOf(time);
            user.setRegisterDate(ts);
            System.out.println("--------------"+user.getRegisterDate());
            if (ro.getEmail()!=null){
                user.setEmail(ro.getEmail());
            }
            if (ro.getPhone_num()!=null){
                user.setPhoneNum(ro.getPhone_num());
            }
            userMapper.insert(user);
            return "注册成功";
        }
        return ans;
    }

    @Override
    public String login(LoginDto loginDto) {

        String pd = userMapper.selectPdByUsername(loginDto.getUsername());
        if(pd==null){
            return "用户名错误";
        }else if(pd.equals(MD5Utils.code(loginDto.getPassword()))){

            return "用户名密码正确";
        }
        return "密码错误";
    }

    @Override
    public String isExist(RegisterDto ro) {
        if(userMapper.selectByUsername(ro.getUsername())!=null){
            return "用户名已经存在";
        }
        return "ok";
    }

    @Override
    public User retrieveUserInfo(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user == null)
            throw new BadRequestException("User(UserId="+userId+") doesn't exists");
        else {
            user.setPassword("");
            return user;
        }
    }

    @Override
    public void changeUserInfo(Integer userId, String status) {
        User user = new User();
        user.setId(userId);
        user.setStatus(status);
        if (userMapper.updateById(user) == 0)
            throw new BadRequestException("User(userId="+userId+") doesn't exist");
    }


    /**
     * 注册表单后端验证
     * @param ro
     * @return
     */
    public String istrue(RegisterDto ro){
        if(ro.getUsername()==null||ro.getUsername()==""){
            return "账号不能为空";
        }
        if(userMapper.selectByUsername(ro.getUsername())!=null){
            return "该账号已经存在";
        }
        if(ro.getPassword()==null||ro.getPassword()==""){
            return "密码不能为空";
        }
        if(ro.getPassword().length()>16||ro.getPassword().length()<4){
            return "密码长度只能在4~16位";
        }
        return "OK";
    }
}
