package com.fudan.se.community;

import com.fudan.se.community.mapper.UserMapper;
import com.fudan.se.community.pojo.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
    }

    @Test
    public void testSelect() {
        List<User> res = userMapper.selectList(null);
    }


}
