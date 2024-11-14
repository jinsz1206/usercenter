package com.jsz.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsz.usercenter.model.domain.User;
import com.jsz.usercenter.service.UserService;
import com.jsz.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author ex_shengzhou.jin
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-11-14 16:23:07
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




