package com.jsz.usercenter.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsz.usercenter.model.domain.User;
import com.jsz.usercenter.service.UserService;
import com.jsz.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
* @author ex_shengzhou.jin
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-11-14 16:23:07
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    private static final String SALT = "jsz";

    @Resource
    private UserMapper userMapper;

    @Override
    public long usrRegister(String userAccount, String userPassword, String checkPassword) {
        //1. 校验


        //内容为空
        if(StrUtil.isAllBlank(userAccount, userPassword, checkPassword)){
            return -1;
        }
        //用户名长度
//        if(StrUtil.length(userAccount) < 4){
//            return -1;
//        }

        //密码长度
        if(StrUtil.length(userPassword) > 15 || StrUtil.length(userPassword) < 6){
            return -1;
        }


        //账户名字不能包含特殊字符,6-12位数
        if(!Validator.isGeneral(userAccount,6,12)){
            return -1;
        }

        //密码2次输入
        if (!StrUtil.equals(userPassword, checkPassword)) {
            return -1;
        }


        //账户不能重复,对数据库的调用放校验最后
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if(count > 0){
            return -1;
        }

        //密码加密
        byte[] bytes = DigestUtil.md5(SALT + userPassword);
        String encryptPassWord = DigestUtil.md5Hex(bytes);
        //插入
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassWord);
        boolean saveResult = this.save(user);
        if(!saveResult){
            return -1;
        }
        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword) {
        //1. 校验


        //内容为空
        if(StrUtil.isAllBlank(userAccount, userPassword)){
            return null;
        }

        //密码长度
        if(StrUtil.length(userPassword) > 15 || StrUtil.length(userPassword) < 6){
            return null;
        }


        //账户名字不能包含特殊字符,6-12位数
        if(!Validator.isGeneral(userAccount,6,12)){
            return null;
        }



        //密码加密
        byte[] bytes = DigestUtil.md5(SALT + userPassword);
        String encryptPassWord = DigestUtil.md5Hex(bytes);





        return null;
    }
}




