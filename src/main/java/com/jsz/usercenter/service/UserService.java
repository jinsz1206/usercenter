package com.jsz.usercenter.service;

import com.jsz.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ex_shengzhou.jin
* @description 针对表【user】的数据库操作Service
* @createDate 2024-11-14 16:23:07
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 确认
     * @return 新用户ID
     */
    long usrRegister(String userAccount, String userPassword,String checkPassword);


    /**
     *
     * @param userAccount 账号
     * @param userPassword 密码
     * @return User对象
     */
    User doLogin(String userAccount, String userPassword);







}
