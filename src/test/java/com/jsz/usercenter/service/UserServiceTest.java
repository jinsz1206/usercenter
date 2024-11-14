package com.jsz.usercenter.service;


import com.jsz.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Title: UserServiceTest
 * @Author jsz
 * @Package com.jsz.usercenter.service
 */

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUserAccount("1209990432");
        user.setUserPassword("jsz20031216");

        user.setUserGender("男");
        user.setUserName("jsz");
        user.setUserProfile("神人");
        user.setUserRole("admin");

        
        

        boolean reasult = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(reasult);

    }

}