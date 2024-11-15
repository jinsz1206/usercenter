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

    @Test
    public void userRegiister() {

        //check 不一样
        String account = "qwe123345";
        String password = "jsz20031216";
        String check = "213213213";
        long result = userService.usrRegister(account, password, check);
        Assertions.assertEquals(-1, result);

        //特殊字符
        account = "12@dw321eq ";
        check = "jsz20031216";
        result  = userService.usrRegister(account, password, check);
        Assertions.assertEquals(-1, result);

        //账号位数
        account = "1234";
        result  = userService.usrRegister(account, password, check);
        Assertions.assertEquals(-1, result);

        //密码位数
        account = "12345678ab";
        password = "1234578901231231231231";
        check = "1234578901231231231231";
        result  = userService.usrRegister(account, password, check);
        Assertions.assertEquals(-1, result);

        //重复插入
        result  = userService.usrRegister(account, password, check);
        Assertions.assertEquals(-1, result);


    }

    @Test
    public void userInsert() {
        String account = "1209990432";
        String password = "jsz20031216";
        String check = "jsz20031216";
        Long result  = userService.usrRegister(account, password, check);
        Assertions.assertTrue(result>1);
    }


}